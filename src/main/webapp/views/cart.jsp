<%@ include file="../includes/header.jsp" %>
<h2>Your Cart</h2>
<c:if test="${empty cartItems}"><div class="alert alert-info">Cart is empty. <a href="${pageContext.request.contextPath}/home">Shop now</a></div></c:if>
<c:if test="${not empty cartItems}">
    <table class="table table-bordered">
        <thead class="table-light"><tr><th>Product</th><th>Price</th><th>Qty</th><th>Total</th><th>Action</th></tr></thead>
        <tbody>
        <c:set var="grandTotal" value="0"/>
        <c:forEach var="item" items="${cartItems}">
            <c:set var="itemTotal" value="${item.quantity * item.product.price}"/>
            <c:set var="grandTotal" value="${grandTotal + itemTotal}"/>
            <tr>
                <td><img src="${pageContext.request.contextPath}/${item.product.imageUrl}" style="height:50px;width:50px;object-fit:cover;" class="me-2">${item.product.productName}</td>
                <td>₹${item.product.price}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/update-cart" method="post" class="d-inline">
                        <input type="hidden" name="productId" value="${item.productId}">
                        <input type="number" name="quantity" value="${item.quantity}" min="1" style="width:60px;" class="form-control d-inline">
                        <button type="submit" class="btn btn-sm btn-primary"><i class="fas fa-sync-alt"></i></button>
                    </form>
                </td>
                <td>₹${itemTotal}</td>
                <td><a href="${pageContext.request.contextPath}/remove-from-cart?productId=${item.productId}" class="btn btn-sm btn-danger" onclick="return confirm('Remove?')"><i class="fas fa-trash"></i></a></td>
            </tr>
        </c:forEach>
        <tr class="table-secondary"><td colspan="3" class="text-end"><strong>Grand Total</strong></td><td><strong>₹${grandTotal}</strong></td><td></td></tr>
        </tbody>
    </table>
    <div class="d-flex justify-content-between"><a href="${pageContext.request.contextPath}/home" class="btn btn-secondary">Continue Shopping</a><a href="${pageContext.request.contextPath}/checkout" class="btn btn-success">Proceed to Checkout</a></div>
</c:if>
<%@ include file="../includes/footer.jsp" %>