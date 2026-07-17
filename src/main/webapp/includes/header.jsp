<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="ecommerce.dao.CartDAO" %>
<%@ page import="ecommerce.model.User" %>
<%
    int currentCartCount = 0;
    User sessionUserObj = (User) session.getAttribute("user");
    if (sessionUserObj != null) {
        try {
            currentCartCount = new CartDAO().getCartItemCount(sessionUserObj.getUserId());
        } catch(Exception e) { e.printStackTrace(); }
    }
    pageContext.setAttribute("cartCount", currentCartCount);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyShoppy</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        .product-img { width: 100%; height: 250px; object-fit: cover; background: #f8f9fa; }
        .product-card { height: 100%; transition: transform 0.2s; }
        .product-card:hover { transform: translateY(-5px); box-shadow: 0 8px 16px rgba(0,0,0,0.1); }
        .cart-badge { font-size: 0.7rem; top: -10px; right: 5px; }
        .navbar-brand { font-weight: 700; color: #0d6efd; }
        .footer { background: #f8f9fa; padding: 20px 0; margin-top: 40px; }
        .admin-badge { background: #ffc107; color: #000; padding: 2px 8px; border-radius: 12px; font-size: 0.7rem; margin-left: 5px; }
    </style>
</head>
<body class="d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg bg-body-tertiary shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/home"><i class="fas fa-shopping-bag me-2"></i>MyShoppy</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navMenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navMenu">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/home">Home</a></li>
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/order-history">My Orders</a></li>
                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/products">Admin <span class="admin-badge">panel</span></a></li>
                    </c:if>
                </c:if>
            </ul>
            <form class="d-flex me-3" action="${pageContext.request.contextPath}/home" method="get">
                <input class="form-control me-2" type="search" placeholder="Search products..." aria-label="Search" name="search" value="${searchQuery}">
                <button class="btn btn-outline-success" type="submit"><i class="fas fa-search"></i></button>
            </form>
            <div class="d-flex align-items-center">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span class="me-2">Welcome, <strong>${sessionScope.user.fullName}</strong></span>
                        <a href="${pageContext.request.contextPath}/cart" class="btn btn-outline-primary position-relative me-2">
                            <i class="fas fa-shopping-cart fa-lg"></i>
                            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger cart-badge">
                                <c:out value="${cartCount}" default="0"/>
                            </span>
                        </a>
                        <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger">Logout</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/views/login.jsp" class="btn btn-primary me-2">Login</a>
                        <a href="${pageContext.request.contextPath}/views/register.jsp" class="btn btn-outline-secondary">Register</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>
<div class="container mt-3 flex-grow-1">