<%@ include file="../includes/header.jsp" %>
<h2>My Orders</h2>
<c:if test="${param.placed eq 'true'}"><div class="alert alert-success">Order placed successfully!</div></c:if>
<c:if test="${empty orders}"><div class="alert alert-info">No orders yet. <a href="${pageContext.request.contextPath}/home">Start shopping</a></div></c:if>
<c:if test="${not empty orders}">
    <div class="list-group">
        <c:forEach var="order" items="${orders}">
            <div class="list-group-item">
                <div class="row align-items-center">
                    <div class="col-md-2"><strong>Order #${order.orderId}</strong></div>
                    <div class="col-md-3">${order.orderDate}</div>
                    <div class="col-md-2">₹${order.totalAmount}</div>
                    <div class="col-md-2"><span class="badge ${order.status == 'PENDING' ? 'bg-warning' : order.status == 'SHIPPED' ? 'bg-info' : order.status == 'DELIVERED' ? 'bg-success' : 'bg-danger'}">${order.status}</span></div>
                    <div class="col-md-3"><button class="btn btn-sm btn-outline-primary" data-bs-toggle="collapse" data-bs-target="#orderItems${order.orderId}">Show Items</button></div>
                </div>
                <div class="collapse mt-2" id="orderItems${order.orderId}">
                    <div class="card card-body">
                        <table class="table table-sm table-bordered mb-0">
                            <thead><tr><th>Product</th><th>Qty</th><th>Price</th></tr></thead>
                            <tbody>
                                <c:forEach var="item" items="${orderItemsMap[order.orderId]}">
                                    <tr><td>Product ID: ${item.productId}</td><td>${item.quantity}</td><td>₹${item.price}</td></tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</c:if>
<%@ include file="../includes/footer.jsp" %>