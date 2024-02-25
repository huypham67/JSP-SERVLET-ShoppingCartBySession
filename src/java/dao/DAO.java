/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import model.Product;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Cart;
import model.Order;
import model.User;

/**
 *
 * @author huypd
 */
public class DAO extends DBContext {

    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        String sql = "select * from products";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public User login(String email, String pass) {
        String sql = "select * from users \n"
                + "where email = ? and password = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Cart> getCartProduct(List<Cart> cartList) {
        List<Cart> list = new ArrayList<>();
        for (Cart o : cartList) {
            String sql = "select * from products where id = ?";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, o.getId());
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Cart(rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getDouble(4) * o.getQuantity(),
                            rs.getString(5), o.getQuantity()));
                }
            } catch (Exception e) {
            }
        }
        return list;
    }

    public void insertOrder(Order o) {
        String sql = "INSERT INTO [dbo].[orders]\n"
                + "           ([p_id]\n"
                + "           ,[u_id]\n"
                + "           ,[o_quantity]\n"
                + "           ,[o_date])\n"
                + "     VALUES (?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, o.getId());
            ps.setInt(2, o.getUid());
            ps.setInt(3, o.getQuantity());
            ps.setString(4, o.getDate());
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Order> userOrders(int uid) {
        List<Order> list = new ArrayList<>();
        String sql = "select * from orders where u_id = ? order by o_id desc";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order o = new Order();
                //set đủ 5 cột
                o.setOrderId(rs.getInt("o_id")); //orderId
                o.setId(rs.getInt("p_id")); //p_id
                o.setUid(uid); //u_id
                o.setQuantity(rs.getInt("o_quantity"));
                o.setDate(rs.getString("o_date"));
                //set thêm name, category, price để in ra
                DAO dao = new DAO();
                Product p = dao.getProductById(o.getId());
                o.setName(p.getName());
                o.setCategory(p.getCategory());
                o.setPrice(p.getPrice() * rs.getInt(4));
                list.add(o);
            }
        } catch (Exception e) {
        }
        return list;
    }

    private Product getProductById(int id) {
        String sql = "select * from products where id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDouble(4),
                        rs.getString(5));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void cancelOrder(String oid) {
        String sql = "delete from orders where o_id = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, oid);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public double getTotalCartPrice(List<Cart> curList) {
        double s = 0;
        for (Cart o : curList) {
            try {
                String sql = "select price from products where id = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, o.getId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    s += rs.getDouble(1)*o.getQuantity();
                }
            } catch (Exception e) {
            }
        }
        return s;
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        List<Order> listOrder = dao.userOrders(1);
        for (Order o : listOrder) {
            System.out.println(o);
        }
        List<Cart> cartList = new ArrayList<>();
        cartList.add(new Cart(1, 1));
        cartList.add(new Cart(2, 1));
        cartList.add(new Cart(3, 2));
        List<Cart> list = dao.getCartProduct(cartList);
        for (Cart o : list) {
            System.out.println(o);
        }
    }

}
