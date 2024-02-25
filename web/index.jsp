<%-- 
    Document   : index
    Created on : Feb 23, 2024, 10:26:29 PM
    Author     : huypd
--%>

<%@page import="java.util.List"%>
<%@page import="model.Product"%>
<%@page import="dao.DAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    DAO dao = new DAO();
    List<Product> listP = dao.getAllProduct();
    request.setAttribute("listP", listP);
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Welcome to shopping cart!!!</title>
        <%@include file="includes/header.jsp" %>
    </head>
    <body>
        <%@include file="/includes/navbar.jsp"%>

        <div class="container">
            <div class="card-header my-3">All Products</div>
            <div class="row">
                <c:forEach items="${listP}" var="o">
                    <div class="col-md-3 my-3">
                        <div class="card w-100">
                            <img class="card-img-top" src="product-image/${o.image}" alt="Card image cap">
                            <div class="card-body">
                                <h5 class="card-title">${o.name}</h5>
                                <h6 class="price">Price: $${o.price}</h6>
                                <h6 class="category">Category: ${o.category}</h6>
                                <c:if test="${tag == o.id}">
                                    <h6 style='color: crimson; text-align: center'>${mes}</h6>
                                </c:if>
                                <div class="mt-3 d-flex justify-content-between">
                                    <a class="btn btn-dark" href="add-to-cart?id=${o.id}">Add to Cart</a> 
                                    <a class="btn btn-primary" href="order-now?quantity=1&id=${o.id}">Buy Now</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>

            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>
