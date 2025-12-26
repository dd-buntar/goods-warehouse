<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Shipment</h1>

    <form action="${pageContext.request.contextPath}/shipment" accepted-charset='utf-8' method="post">
        <div class="form-group">
            <label for="productId">Product:</label>
            <select id="productId" name="productId" required>
                <option value="">Select product</option>
                <c:forEach var="product" items="${products}">
                    <option value="${product.productId}">
                        ${product.productName} (ID: ${product.productId})
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="purchasePrice">Purchase Price:</label>
            <input type="number" id="purchasePrice" name="purchasePrice"
                   required min="0" step="0.01"
                   placeholder="Enter purchase price">
        </div>

        <div class="form-group">
            <label for="salePrice">Sale Price:</label>
            <input type="number" id="salePrice" name="salePrice"
                   required min="0" step="0.01"
                   placeholder="Enter sale price">
        </div>

        <div class="form-group">
            <label for="productionDate">Production Date:</label>
            <input type="datetime-local" id="productionDate" name="productionDate"
                   required>
        </div>

        <div class="form-group">
            <label for="expiryDate">Expiry Date:</label>
            <input type="datetime-local" id="expiryDate" name="expiryDate"
                   required>
        </div>

        <div class="form-group">
            <label for="arrivalDate">Arrival Date:</label>
            <input type="datetime-local" id="arrivalDate" name="arrivalDate"
                   required>
        </div>

        <button type="submit" class="submit-btn">Add Shipment</button>
    </form>

    <script>
        // Устанавливаем минимальную дату для полей с датами
        document.addEventListener('DOMContentLoaded', function() {
            const now = new Date();
            const nowString = now.toISOString().slice(0, 16);

            // Для productionDate можно установить вчерашнюю дату как минимальную
            const yesterday = new Date();
            yesterday.setDate(yesterday.getDate() - 1);
            const yesterdayString = yesterday.toISOString().slice(0, 16);

            document.getElementById('productionDate').min = yesterdayString;
            document.getElementById('expiryDate').min = nowString;
            document.getElementById('arrivalDate').min = nowString;

            // Устанавливаем значения по умолчанию на текущую дату-время
            document.getElementById('arrivalDate').value = nowString;
        });
    </script>
</body>
</html>