<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Storage Location</h1>

    <form action="${pageContext.request.contextPath}/storageLocation" accepted-charset='utf-8' method="post">
        <div class="form-group">
            <label for="rackNum">Rack Number:</label>
            <input type="number" id="rackNum" name="rackNum"
                   required min="1"
                   placeholder="Enter rack number">
        </div>

        <div class="form-group">
            <label for="shelfNum">Shelf Number:</label>
            <input type="number" id="shelfNum" name="shelfNum"
                   required min="1"
                   placeholder="Enter shelf number">
        </div>

        <button type="submit" class="submit-btn">Add Storage Location</button>
    </form>
</body>
</html>