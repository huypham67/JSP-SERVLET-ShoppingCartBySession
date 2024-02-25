/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Cart;

/**
 *
 * @author huypd
 */
@WebServlet(name = "AddToCartSrv", urlPatterns = {"/add-to-cart"})
public class AddToCartSrv extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String idS = request.getParameter("id");
        int id = Integer.parseInt(idS);
        DAO dao = new DAO();
        HttpSession session = request.getSession();
        ArrayList<Cart> curList = (ArrayList<Cart>) session.getAttribute("curList");
        if (curList == null) {
            curList = new ArrayList<>();
            curList.add(new Cart(id, 1));
            List<Cart> newList = dao.getCartProduct(curList);
            session.setAttribute("curList", newList);
            response.sendRedirect("index.jsp");
        } else {
            boolean existed = false;
            for (Cart o : curList) {
                if (o.getId() == id) {
                    existed = true;
                    request.setAttribute("tag", id);
                    request.setAttribute("mes", "Item Already in Cart");
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                    break;
                }
            }
            if (!existed) {
                curList.add(new Cart(id, 1));
                List<Cart> newList = dao.getCartProduct(curList);
                session.setAttribute("curList", newList);
                response.sendRedirect("index.jsp");
            }
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
