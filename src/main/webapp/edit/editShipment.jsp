<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .datetime-input {
            width: 200px;
        }
    </style>
</head>
<body>
    <h1>Edit Shipment</h1>
    <form action="${pageContext.request.contextPath}/shipment" method="post">
        <input type="hidden" name="shipmentId" value="${curShipment.shipmentId}">

        <div class="form-group">
            <label for="productId">Product:</label>
            <select id="productId" name="productId" required>
                <c:forEach var="product" items="${products}">
                    <option value="${product.productId}" ${product.productId == curShipment.productId ? 'selected' : ''}>
                        ${product.productName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="purchasePrice">Purchase Price:</label>
            <input type="number" id="purchasePrice" name="purchasePrice"
                   value="${curShipment.purchasePrice}" required min="0">
        </div>

        <div class="form-group">
            <label for="salePrice">Sale Price:</label>
            <input type="number" id="salePrice" name="salePrice"
                   value="${curShipment.salePrice}" required min="0">
        </div>

        <div class="form-group">
            <label for="productionDate">Production Date:</label>
            <input type="datetime-local" id="productionDate" name="productionDate"
                   value="${curShipment.productionDate}"
                   class="datetime-input" required>
        </div>

        <div class="form-group">
            <label for="expiryDate">Expiry Date:</label>
            <input type="datetime-local" id="expiryDate" name="expiryDate"
                   value="${curShipment.expiryDate}"
                   class="datetime-input" required>
        </div>

        <div class="form-group">
            <label for="arrivalDate">Arrival Date:</label>
            <input type="datetime-local" id="arrivalDate" name="arrivalDate"
                   value="${curShipment.arrivalDate}"
                   class="datetime-input" required>
        </div>

        <button type="submit" class="submit-btn">Update Shipment</button>
    </form>
</body>
</html>