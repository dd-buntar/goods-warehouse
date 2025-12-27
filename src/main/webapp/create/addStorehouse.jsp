<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Storehouse Record</h1>

    <form action="${pageContext.request.contextPath}/storehouse" accepted-charset='utf-8' method="post">
        <div class="form-group">
            <label for="shipmentId">Shipment:</label>
            <select id="shipmentId" name="shipmentId" required>
                <option value="">Select shipment</option>
                <c:forEach var="shipment" items="${shipments}">
                    <option value="${shipment.shipmentId}">
                        #${shipment.shipmentId} - Product ${shipment.productId}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity"
                   required min="1"
                   placeholder="Enter quantity">
        </div>

        <div class="form-group">
            <label for="locationId">Storage Location:</label>
            <select id="locationId" name="locationId" required>
                <option value="">Select location</option>
                <c:forEach var="location" items="${locations}">
                    <option value="${location.locationId}">
                        Location #${location.locationId}
                        (Rack: ${location.rackNum}, Shelf: ${location.shelfNum})
                    </option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="submit-btn">Add to Storehouse</button>
    </form>
</body>
</html>