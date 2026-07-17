<%@ include file="../includes/header.jsp" %>
<div class="row justify-content-center mt-5">
    <div class="col-md-6 col-lg-4">
        <div class="card shadow">
            <div class="card-header bg-success text-white"><h4><i class="fas fa-user-plus me-2"></i>Register</h4></div>
            <div class="card-body">
                <c:if test="${not empty error}"><div class="alert alert-danger">${error}</div></c:if>
                <form action="${pageContext.request.contextPath}/register" method="post">
                    <div class="mb-3"><label>Full Name</label><input type="text" name="fullName" class="form-control" required></div>
                    <div class="mb-3"><label>Email</label><input type="email" name="email" class="form-control" required></div>
                    <div class="mb-3"><label>Username</label><input type="text" name="username" class="form-control" required></div>
                    <div class="mb-3">
                        <label>Password</label>
                        <div class="input-group">
                            <input type="password" name="password" id="password" class="form-control" required>
                            <button class="btn btn-outline-secondary" type="button" onclick="togglePassword('password', this)"><i class="fas fa-eye"></i></button>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-success w-100">Register</button>
                </form>
                <p class="mt-3 text-center">Already have an account? <a href="${pageContext.request.contextPath}/views/login.jsp">Login</a></p>
                <script>
                    function togglePassword(inputId, btn) {
                        var input = document.getElementById(inputId);
                        var icon = btn.querySelector('i');
                        if (input.type === 'password') {
                            input.type = 'text';
                            icon.classList.remove('fa-eye');
                            icon.classList.add('fa-eye-slash');
                        } else {
                            input.type = 'password';
                            icon.classList.remove('fa-eye-slash');
                            icon.classList.add('fa-eye');
                        }
                    }
                </script>
            </div>
        </div>
    </div>
</div>
<%@ include file="../includes/footer.jsp" %>