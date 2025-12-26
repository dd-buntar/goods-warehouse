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
    <h1>Add New Manufacturer</h1>

    <form action="${pageContext.request.contextPath}/manufacturer" accepted-charset='utf-8' method="post">
        <div class="form-group">
                <label for="manufacturerName">Manufacturer Name:</label>
                <input type="text" id="manufacturerName" name="manufacturerName"
                       required placeholder="Enter manufacturer name">
            </div>

            <div class="form-group">
                <label for="contactPhone">Contact Phone:</label>
                <input type="tel" id="contactPhone" name="contactPhone"
                       required pattern="[+]?[\d\s-]+"
                       placeholder="+1 234 567 8900">
            </div>

            <div class="form-group">
                <label for="country">Country:</label>
                <input type="text" id="country" name="country"
                       required placeholder="Enter country">
            </div>

            <button type="submit" class="submit-btn">Add Manufacturer</button>
    </form>
</body>
</html>
