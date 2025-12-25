<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Product</h1>


    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Manufacturer</th>
                <th>Weight</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="product" items="${product}">
                <tr>
                    <td>${product.productId}</td>
                    <td>${product.productName}</td>
                    <td>${product.manufacturerId}</td>
                    <td>${product.weight}</td>
                    <td>${product.description}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/product/${product.productId}">View</a>
                        <a href="${pageContext.request.contextPath}/product/${product.productId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/product/${product.productId}/delete" onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty product}">
        <p>No products found.</p>
    </c:if>
</body>
</html>
