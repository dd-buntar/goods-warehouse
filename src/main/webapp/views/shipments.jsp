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
    <h1>Shipment</h1>


    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Product</th>
                <th>Purchase_Price</th>
                <th>Sale_Price</th>
                <th>Production_Date</th>
                <th>Expiry_Date</th>
                <th>Arrival_Date</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="shipment" items="${shipment}">
                <tr>
                    <td>${shipment.shipmentId}</td>
                    <td>${shipment.productId}</td>
                    <td>${shipment.purchasePrice}</td>
                    <td>${shipment.salePrice}</td>
                    <td>${shipment.productionDate}</td>
                    <td>${shipment.expiryDate}</td>
                    <td>${shipment.arrivalDate}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/shipment/${shipment.shipmentId}">View</a>
                        <a href="${pageContext.request.contextPath}/shipment/${shipment.shipmentId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/shipment/${shipment.shipmentId}/delete" onclick="return confirm('Are you sure you want to delete this shipment?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty shipment}">
        <p>No shipments found.</p>
    </c:if>
</body>
</html>
