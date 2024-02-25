<%-- 
    Document   : index
    Created on : Feb 23, 2024, 10:26:29 PM
    Author     : huypd
--%>

<%@page import="model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Login Page</title>
        <%@include file="/includes/header.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="card w-50 mx-auto my-5">
                <div class="card-header text-center">User Login</div>
                <div class="card-body">
                    <form action="log-in" method="post">
                        <p class="text-danger">${mes}</p>
                        <div class="form-group">
                            <label>Email address</label> 
                            <input type="email" name="login-email" class="form-control" placeholder="Enter email">
                        </div>
                        <div class="form-group">
                            <label>Password</label> 
                            <input type="password" name="login-password" class="form-control" placeholder="Password">
                        </div>
                        <div class="text-center">
                            <button type="submit" class="btn btn-primary">Login</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <%@include file="/includes/footer.jsp" %>
    </body>
</html>