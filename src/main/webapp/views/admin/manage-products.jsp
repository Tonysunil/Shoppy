<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %> 
<%@ include file="../../includes/header.jsp" %>

<h2>Manage Products</h2>
<a href="${pageContext.request.contextPath}/admin/add-product" class="btn btn-primary mb-3"><i class="fas fa-plus"></i> Add Product</a>

<table class="table table-bordered table-hover align-middle">
    <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Image</th>
            <th>Name</th>
            <th>Price (₹)</th>
            <th>Stock</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.productId}</td>
                <td>
                    <img src="${pageContext.request.contextPath}/${p.imageUrl}" 
                         alt="${p.productName}" 
                         style="width: 60px; height: 60px; object-fit: cover; border-radius: 4px; background: #f8f9fa;"
                         onerror="this.src='https://via.placeholder.com/60/cccccc/ffffff?text=No+Image'">
                </td>
                <td>${p.productName}</td>
                <td>₹${p.price}</td>
                <td>${p.stock}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/update-product" method="post" enctype="multipart/form-data" class="d-inline">
                        <input type="hidden" name="productId" value="${p.productId}">
                        <input type="hidden" name="categoryId" value="${p.categoryId}">
                        <input type="hidden" name="description" value="${p.description}">
                        <input type="hidden" name="imageUrl" value="${p.imageUrl}">
                        <input type="text" name="productName" value="${p.productName}" required style="width:100px;">
                        <input type="number" step="0.01" name="price" value="${p.price}" required style="width:80px;">
                        <input type="number" name="stock" value="${p.stock}" required style="width:70px;">
                        <input type="file" name="imageFile" accept="image/*" style="width:120px; display:inline-block;">
                        <button type="submit" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i> Update</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/admin/delete-product" method="post" class="d-inline" onsubmit="return confirm('Delete this product?')">
                        <input type="hidden" name="productId" value="${p.productId}">
                        <button type="submit" class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="../../includes/footer.jsp" %>