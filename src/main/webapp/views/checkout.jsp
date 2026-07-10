<%@ include file="../includes/header.jsp" %>
<h2>Checkout</h2>
<c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
<c:if test="${empty cartItems}"><div class="alert alert-warning">Cart empty. <a href="${pageContext.request.contextPath}/home">Shop</a></div></c:if>
<c:if test="${not empty cartItems}">
    <div class="row">
        <div class="col-md-8">
            <div class="card"><div class="card-header"><h5>Order Summary</h5></div><div class="card-body">
                <table class="table table-sm">
                    <c:set var="total" value="0"/>
                    <c:forEach var="item" items="${cartItems}">
                        <c:set var="subtotal" value="${item.quantity * item.product.price}"/>
                        <c:set var="total" value="${total + subtotal}"/>
                        <tr><td>${item.product.productName}</td><td>${item.quantity}</td><td>₹${item.product.price}</td><td>₹${subtotal}</td></tr>
                    </c:forEach>
                    <tr class="table-secondary"><td colspan="3" class="text-end"><strong>Total</strong></td><td><strong>₹${total}</strong></td></tr>
                </table>
            </div></div>
        </div>
        <div class="col-md-4">
            <div class="card"><div class="card-header"><h5>Shipping & Payment</h5></div><div class="card-body">
                <form action="${pageContext.request.contextPath}/checkout" method="post">
                    <div class="mb-3"><label>Shipping Address</label><textarea name="shippingAddress" class="form-control" rows="3" required></textarea></div>
                    <div class="mb-3"><label>Payment Method</label>
                        <select name="paymentMethod" class="form-select" required>
                            <option value="Credit Card">Credit Card</option><option value="Debit Card">Debit Card</option>
                            <option value="PayPal">PayPal</option><option value="Cash on Delivery">Cash on Delivery</option>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-success w-100">Place Order</button>
                </form>
            </div></div>
        </div>
    </div>
</c:if>
<%@ include file="../includes/footer.jsp" %>