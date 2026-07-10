<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="../includes/header.jsp" %>
<c:if test="${empty product}">
    <div class="alert alert-warning">Product not found.</div>
</c:if>
<c:if test="${not empty product}">
    <div class="row mt-4">
        <div class="col-md-6"><img src="${pageContext.request.contextPath}/${product.imageUrl}" class="img-fluid rounded" alt="${product.productName}" onerror="this.src='https://via.placeholder.com/500/cccccc/ffffff?text=No+Image'"></div>
        <div class="col-md-6">
            <h2>${product.productName}</h2>
            <p>${product.description}</p>
            <h4 class="text-primary">₹${product.price}</h4>
            <p><strong>Stock:</strong> ${product.stock}</p>
            <c:if test="${not empty sessionScope.user}">
                <a href="${pageContext.request.contextPath}/add-to-cart?productId=${product.productId}" class="btn btn-success btn-lg"><i class="fas fa-cart-plus"></i> Add to Cart</a>
            </c:if>
            <a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Back</a>
        </div>
    </div>
</c:if>
<%@ include file="../includes/footer.jsp" %>