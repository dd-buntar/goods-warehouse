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
    <h1>Storehouse</h1>


    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Shipment</th>
                <th>Quantity</th>
                <th>Location</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="storehouse" items="${storehouse}">
                <tr>
                    <td>${storehouse.stockId}</td>
                    <td>${storehouse.shipmentId}</td>
                    <td>${storehouse.quantity}</td>
                    <td>${storehouse.locationId}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}">View</a>
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}/delete" onclick="return confirm('Are you sure you want to delete this storehouse?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty storehouse}">
        <p>No storehouses found.</p>
    </c:if>
</body>
</html>
