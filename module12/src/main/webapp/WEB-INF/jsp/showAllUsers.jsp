<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Список всех пользователей БД</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/scripts/script.js"></script>
</head>
<body class="bg-dark">
<div class="container">
    <div class="row">
        <h1 class="text-white">Список всех пользователей БД</h1>
        <div class="col-12">
            <table class="table table-bordered table-dark table-striped">
                <thead>
                <tr>
                    <th scope="col">Id</th>
                    <th scope="col">Name</th>
                    <th scope="col">Age</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${users}" var="users">
                    <tr>
                        <th scope="row">${users.id}</th>
                        <td>${users.name}</td>
                        <td>${users.age}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>