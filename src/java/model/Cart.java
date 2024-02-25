/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huypd
 */
public class Cart extends Product{
    private int quantity;

    public Cart() {
    }
    public Cart(int id, int quantity) {
        super(id);
        this.quantity = quantity;
    }
    
    public Cart(int id, String name, String category, double price, String image, int quantity) {
        super(id, name, category, price, image);
        this.quantity = quantity;
    }
    
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return super.toString() + ", quantity: " + quantity;
    }
    
}
