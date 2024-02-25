<%-- 
    Document   : index
    Created on : Feb 23, 2024, 10:26:29 PM
    Author     : huypd
--%>

<%@page import="model.User"%>
<%@page import="dao.DAO"%>
<%@page import="model.Order"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    User auth = (User) session.getAttribute("auth");
    DAO dao = new DAO();
    if (auth != null) {
        List<Order> listOrder = dao.userOrders(auth.getId());
        session.setAttribute("listO", listOrder);
    }
    else
        response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Order Page</title>
        <%@include file="includes/header.jsp" %>
    </head>
    <body>
        <%@include file="/includes/navbar.jsp"%>
        <div class="container">
            <div class="card-header my-3">All Orders</div>
            <table class="table table-light">
                <thead>
                    <tr>
                        <th scope="col">Date</th>
                        <th scope="col">Name</th>
                        <th scope="col">Category</th>
                        <th scope="col">Quantity</th>
                        <th scope="col">Price</th>
                        <th scope="col">Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${sessionScope.listO}" var="o">
                    <tr>
                        <td>${o.date}</td>
                        <td>${o.name}</td>
                        <td>${o.category}</td>
                        <td>${o.quantity}</td>
                        <td>${o.price}</td>
                        <td><a class="btn btn-sm btn-danger" href="cancel-order?id=${o.orderId}">Cancel Order</a></td>
                    </tr>
                    </c:forEach>

                </tbody>
            </table>
        </div>
        <%@include file="/includes/footer.jsp"%>
    </body>
</html>