<%@ include file="../includes/header.jsp" %>
<div class="row justify-content-center mt-5">
    <div class="col-md-6 col-lg-4">
        <div class="card shadow">
            <div class="card-header bg-primary text-white"><h4><i class="fas fa-sign-in-alt me-2"></i>Login</h4></div>
            <div class="card-body">
                <c:if test="${param.registered eq 'true'}"><div class="alert alert-success">Registration successful! Please login.</div></c:if>
                <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                <form action="${pageContext.request.contextPath}/login" method="post">
                    <div class="mb-3"><label for="username">Username</label><input type="text" name="username" id="username" class="form-control" required></div>
                    <div class="mb-3"><label for="password">Password</label><input type="password" name="password" id="password" class="form-control" required></div>
                    <button type="submit" class="btn btn-primary w-100">Login</button>
                </form>
                <p class="mt-3 text-center">Don't have an account? <a href="${pageContext.request.contextPath}/views/register.jsp">Register</a></p>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>