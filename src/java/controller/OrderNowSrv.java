/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller;

import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;
import model.Order;
import model.User;

/**
 *
 * @author huypd
 */
@WebServlet(name="OrderNowSrv", urlPatterns={"/order-now"})
public class OrderNowSrv extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //B1: Lấy id, quantity từ request
        String pidS = request.getParameter("id");
        String quantityS = request.getParameter("quantity");
        //B2: Check đăng nhập hay chưa
        HttpSession session = request.getSession();
        User auth = (User) session.getAttribute("auth");
        if (auth == null)
            //chưa thì đăng nhập thôi
            response.sendRedirect("login.jsp");
        else {
            //rồi thì set 5 thuộc tính
            //5 thuộc tính của order: orderId, pid, uid, quantity, date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            int pid = Integer.parseInt(pidS); //productId
            int quantity = Integer.parseInt(quantityS);
            Order order = new Order(pid, auth.getId(), quantity, formatter.format(new Date()));
//            order.setId(pid); //productId
//            order.setUid(auth.getId()); //userId
//            order.setQuantity(quantity); //quantity
//            order.setDate(formatter.format(new Date())); //date
            DAO dao = new DAO();
            dao.insertOrder(order); //thực hiện order
            //xóa sp trong CART
            List<Cart> curList = (List<Cart>) session.getAttribute("curList");
            if (curList != null) {
                for (Cart o : curList) {
                    if (o.getId() == pid) {
                        curList.remove(curList.indexOf(o)); //đã xóa xong (nếu có)
                        break;
                    }
                }
            }
            session.setAttribute("curList", curList);
            response.sendRedirect("orders.jsp");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
