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
                <th>Shipment</th>
                <th>Quantity</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="storehouse" items="${storehouse}">
                <tr>
                    <td>${storehouse.shipmentId}</td>
                    <td>${storehouse.quantity}</td>
                    <td>${storehouse.locationId}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}">View</a>
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}/edit">Edit</a>
                        <button onclick="delFrom('${storehouse.stockId}', 'storehouse')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty storehouse}">
        <p>No storehouses found.</p>
    </c:if>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
