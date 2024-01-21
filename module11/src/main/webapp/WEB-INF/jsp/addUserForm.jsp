<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Добавление нового пользователя</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="/css/bootstrap.css">
    <script src="/scripts/script.js"></script>
</head>
<body class="bg-dark">
<div class="container">
    <form action="../users/add" method="post">
        <div class="form-group">
            <label for="name" class="text-white">Name</label>
            <input type="text" class="form-control" id="name" name="name">
        </div>
        <div class="form-group">
            <label for="age" class="text-white">Age</label>
            <input type="number" class="form-control" id="age" name="age">
        </div>
        <button type="submit" class="btn btn-primary">Add</button>
    </form>
</div>
</body>
</html>