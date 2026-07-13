<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ include file="../includes/header.jsp" %>
<c:if test="${not empty searchQuery}">
    <h3 class="mb-4">Search Results for: <span class="text-primary">"${searchQuery}"</span></h3>
</c:if>
<c:if test="${empty productList}">
    <div class="alert alert-info">No products found.</div>
</c:if>
<div class="row">
    <c:forEach var="product" items="${productList}">
        <div class="col-md-4 col-lg-3 mb-4">
            <div class="card product-card">
                <img src="${pageContext.request.contextPath}/${product.imageUrl}" class="card-img-top product-img" alt="${product.productName}" onerror="this.src='https://via.placeholder.com/300x250/cccccc/ffffff?text=No+Image'">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title">${product.productName}</h5>
                    <p class="card-text fw-bold text-primary">₹${product.price}</p>
                    <div class="mt-auto d-flex justify-content-between">
                        <a href="${pageContext.request.contextPath}/product-detail?id=${product.productId}" class="btn btn-sm btn-outline-primary">View</a>
                        <c:if test="${not empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/add-to-cart?productId=${product.productId}" class="btn btn-sm btn-success"><i class="fas fa-cart-plus"></i> Add</a>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
<%@ include file="../includes/footer.jsp" %>