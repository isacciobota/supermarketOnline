package com.example.demo.services;

import com.example.demo.exceptions.AccountExists;
import com.example.demo.exceptions.UsernameAlreadyExistsException;
import com.example.demo.model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

public class UserService {

    private static ObjectRepository<User> userRepository;

    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(FileSystemService.getPathToFile("Users.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static Nitrite getDatabase(){
        return database;
    }

    public static void checkUsernameAndPassword(String username,String password) throws AccountExists {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()) &&
                    Objects.equals(encodePassword(username,password), user.getPassword()))
                throw new AccountExists(username);
        }
    }

    public static void addUser(String username, String password, String role, String name, String address, String email) throws UsernameAlreadyExistsException {
        checkUserDoesNotAlreadyExist(username);
        userRepository.insert(new User(username, encodePassword(username, password), role, name, address, email));
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static List<User> getAllUsers(){
        return userRepository.find().toList();
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static String getUserRole(String username, String password){
        String rol = new String();
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())&&Objects.equals(encodePassword(username,password), user.getPassword()))
                rol = user.getRole();
        }
        return rol;
    }
}
