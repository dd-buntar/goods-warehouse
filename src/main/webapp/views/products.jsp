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
                <th>Product_name</th>
                <th>Manufacturer_name</th>
                <th>Weight</th>
                <th>Description</th>
                <th>Quantity</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="productWithQuantity" items="${productWithQuantity}">
                <tr>
                    <td>${productWithQuantity.product.productName}</td>
                    <td>${productWithQuantity.manufacturerName}</td>
                    <td>${productWithQuantity.product.weight}</td>
                    <td>${productWithQuantity.product.description}</td>
                    <td>${productWithQuantity.quantity}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/product/${productWithQuantity.product.productId}">View</a>
                        <a href="${pageContext.request.contextPath}/product/${productWithQuantity.product.productId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/product/${productWithQuantity.product.productId}/delete" onclick="return confirm('Are you sure you want to delete this product?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty productWithQuantity}">
        <p>No products found.</p>
    </c:if>
</body>
</html>
