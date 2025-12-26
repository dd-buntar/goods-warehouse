<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Manufacturer</h1>
        <form action="${pageContext.request.contextPath}/manufacturer" accepted-charset='utf-8' method="post">
            Manufacturer ID: ${curManufacturer.manufacturerId}
            <div class="form-group">
                <label for="manufacturerName">Manufacturer Name:</label>
                <input type="text" id="manufacturerName" name="manufacturerName"
                    value="${curManufacturer.manufacturerName}" required>
            </div>

            <div class="form-group">
                <label for="contactPhone">Contact Phone:</label>
                <input type="tel" id="contactPhone" name="contactPhone"
                    value="${curManufacturer.contactPhone}"
                    required pattern="[+]?[\d\s-]+">
            </div>

            <div class="form-group">
                <label for="country">Country:</label>
                <input type="text" id="country" name="country"
                       value="${curManufacturer.country}" required>
            </div>

            <button type="submit" class="submit-btn">Update Manufacturer</button>
        </form>
</body>
</html>
