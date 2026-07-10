<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../includes/header.jsp" %>

<h2>All Orders</h2>
<table class="table table-bordered table-hover">
    <thead class="table-dark">
        <tr><th>Order ID</th><th>User ID</th><th>Date</th><th>Total</th><th>Status</th><th>Action</th></tr>
    </thead>
    <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.userId}</td>
                <td>${order.orderDate}</td>
                <td>₹${order.totalAmount}</td>
                <td>
                    <span class="badge ${order.status == 'PENDING' ? 'bg-warning' : order.status == 'SHIPPED' ? 'bg-info' : order.status == 'DELIVERED' ? 'bg-success' : 'bg-danger'}">${order.status}</span>
                </td>
                <td>
                    <form action="${pageContext.request.contextPath}/admin/update-order-status" method="post">
                        <input type="hidden" name="orderId" value="${order.orderId}">
                        <select name="status" class="form-select form-select-sm d-inline w-auto">
                            <option value="PENDING" ${order.status == 'PENDING' ? 'selected' : ''}>Pending</option>
                            <option value="SHIPPED" ${order.status == 'SHIPPED' ? 'selected' : ''}>Shipped</option>
                            <option value="DELIVERED" ${order.status == 'DELIVERED' ? 'selected' : ''}>Delivered</option>
                            <option value="CANCELLED" ${order.status == 'CANCELLED' ? 'selected' : ''}>Cancelled</option>
                        </select>
                        <button type="submit" class="btn btn-sm btn-primary">Update</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="../../includes/footer.jsp" %>