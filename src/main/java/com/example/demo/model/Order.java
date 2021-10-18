package com.example.demo.model;
import com.example.demo.exceptions.ItemIsNotInTheCart;
import com.example.demo.exceptions.NotEnoughQuantity;
import com.example.demo.exceptions.ProductDoesNotExist;
import com.example.demo.services.ProductService;
import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private ArrayList<Product> lista=new ArrayList<Product>();
    private String user;
    private int pretTotal;
    private int contor=0;

    public Order() {

    }

    public Order(String user) {
        this.user = user;
    }

    public boolean exista(Product produs){
        boolean temp2=false;
        for (int i=0;i<contor;i++)
            if(produs.getName().equals(lista.get(i).getName()))
                temp2=true;
        return temp2;
    }

    public int pretTotal(){
        int s=0;
        for (int i = 0; i < contor; i++){
            s = s +  ProductService.getPrice(lista.get(i).getName(), lista.get(i).getQuantity());
        }
        return s;
    }

    public void addProduct(Product product) throws ProductDoesNotExist, NotEnoughQuantity {
        boolean temp1=false;
        Integer temp3=0;
        for (Product k : ProductService.productRepository.find())
            if (Objects.equals(product.getName(), k.getName()))
                temp1=true;
        if(!temp1)
            throw new ProductDoesNotExist(product.getName());
        for (Product k : ProductService.productRepository.find())
            if (Objects.equals(product.getName(), k.getName()))
                temp3=k.getQuantity();

        if(exista(product)) {
            for (int i = 0; i < contor; i++)
                if (product.getName().equals(lista.get(i).getName())){
                    temp3 = temp3 - lista.get(i).getQuantity();
                    if(product.getQuantity() >= temp3)
                        throw new NotEnoughQuantity(product.getName());
                    else
                    if(lista.get(i).getQuantity() + product.getQuantity()<=temp3)
                        lista.get(i).setQuantity(lista.get(i).getQuantity() + product.getQuantity());
                }
        }else
        if (temp1) {
                if (product.getQuantity() <= temp3) {
                    lista.add(product);
                    contor++;
                }
                else
                    throw new NotEnoughQuantity(product.getName());
        }
    }

    public void removeProduct(Product product) throws ItemIsNotInTheCart {
        boolean ok=false;
        for (int i=0;i<contor;i++) {
            if (product.getName().equals(lista.get(i).getName())) {
                lista.remove(i);
                ok = true;
                contor--;
            }
        }
        if(!ok)
            throw new ItemIsNotInTheCart(product.getName());
    }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public ArrayList<Product> getOrder() { return lista; }

    public int getContor() { return contor; }

}
