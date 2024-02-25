<%-- 
    Document   : index
    Created on : Feb 23, 2024, 10:26:29 PM
    Author     : huypd
--%>

<%@page import="java.util.List"%>
<%@page import="model.Cart"%>
<%@page import="dao.DAO"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List<Cart> curList = (List<Cart>) session.getAttribute("curList");
    if (curList != null) {
        DAO dao = new DAO();
        double total = dao.getTotalCartPrice(curList);
        session.setAttribute("total", total);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cart Page</title>
        <%@include file="includes/header.jsp" %>
    </head>
    <body>
        <%@include file="/includes/navbar.jsp"%>

        <div class="container my-3">
            <div class="d-flex py-3">
                <h3>Total Price: $ 
                    <c:if test="${total>0}">
                        <fmt:formatNumber pattern="#.##" value="${total}"></fmt:formatNumber>
                    </c:if>
                    <c:if test="${total==0}">
                        0
                    </c:if>
                </h3> 
                <a class="mx-3 btn btn-primary" href="cart-check-out">Check Out</a>
                <h5 style='color: crimson; text-align: center'>${mes}</h5>
            </div>
            <table class="table table-light">
                <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Category</th>
                        <th scope="col">Price</th>
                        <th scope="col">Buy Now</th>
                        <th scope="col">Cancel</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${sessionScope.curList}" var="o">
                        <tr>
                            <td>${o.name}</td>
                            <td>${o.category}</td>
                            <td><fmt:formatNumber pattern="#.##" value="${o.price}">
                            </fmt:formatNumber></td>
                            <td>
                                <form action="order-now" method="post" class="form-inline">
                                    <input type="hidden" name="id" value="${o.id}" class="form-input">
                                    <div class="form-group d-flex justify-content-between">
                                        <a class="btn bnt-sm btn-incre" href="quantity-inc-dec?action=inc&id=${o.id}"><i class="fas fa-plus-square"></i></a> 
                                        <input type="text" name="quantity" class="form-control"  value="${o.quantity}" readonly> 
                                        <a class="btn btn-sm btn-decre" href="quantity-inc-dec?action=dec&id=${o.id}"><i class="fas fa-minus-square"></i></a>
                                    </div>
                                    <button type="submit" class="btn btn-primary btn-sm">Buy</button>
                                </form>
                            </td>
                            <td><a href="remove-from-cart?id=${o.id}" class="btn btn-sm btn-danger">Remove</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <%@include file="/includes/footer.jsp"%>
    </body>
</html>