<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Location</h1>
        <form action="${pageContext.request.contextPath}/storageLocation" accepted-charset='utf-8' method="post">
            <input type="hidden" name="locationId" value="${curLocation.locationId}">
            <div class="form-group">
                <label for="rackNum">Rack Number:</label>
                <input type="number" id="rackNum" name="rackNum"
                       value="${curLocation.rackNum}" required min="1">
            </div>

            <div class="form-group">
                <label for="shelfNum">Shelf Number:</label>
                <input type="number" id="shelfNum" name="shelfNum"
                       value="${curLocation.shelfNum}" required min="1">
            </div>

            <button type="submit" class="submit-btn">Update Location</button>
        </form>
</body>
</html>
