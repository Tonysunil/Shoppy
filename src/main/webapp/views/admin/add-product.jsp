<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../includes/header.jsp" %>

<h3>Add New Product</h3>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<form action="${pageContext.request.contextPath}/admin/add-product" method="post" enctype="multipart/form-data">
    <div class="mb-3"><label>Product Name</label><input type="text" name="productName" class="form-control" required></div>
    <div class="mb-3"><label>Description</label><textarea name="description" class="form-control"></textarea></div>
    <div class="mb-3"><label>Price (₹)</label><input type="number" step="0.01" name="price" class="form-control" required></div>
    <div class="mb-3"><label>Stock</label><input type="number" name="stock" class="form-control" required></div>
    <div class="mb-3"><label>Category ID</label><input type="number" name="categoryId" class="form-control" required></div>
    <div class="mb-3"><label>Upload Image</label><input type="file" name="imageFile" class="form-control" accept="image/*" required></div>
    <button type="submit" class="btn btn-success">Add Product</button>
    <a href="products" class="btn btn-secondary">Cancel</a>
</form>

<%@ include file="../../includes/footer.jsp" %>