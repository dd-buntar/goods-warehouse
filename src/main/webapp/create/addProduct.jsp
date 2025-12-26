<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Add New Product</h1>

    <form action="${pageContext.request.contextPath}/product" accepted-charset='utf-8' method="post">
        <div class="form-group">
            <label for="productName">Product Name:</label>
            <input type="text" id="productName" name="productName"
                   required placeholder="Enter product name">
        </div>

        <div class="form-group">
            <label for="manufacturerId">Manufacturer:</label>
            <select id="manufacturerId" name="manufacturerId" required>
                <option value="">Select manufacturer</option>
                <c:forEach var="manufacturer" items="${manufacturers}">
                    <option value="${manufacturer.manufacturerId}">
                        ${manufacturer.manufacturerName}
                    </option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="weight">Weight (grams):</label>
            <input type="number" id="weight" name="weight"
                   required min="1" placeholder="Enter weight in grams">
        </div>

        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description"
                      rows="4" placeholder="Enter product description"></textarea>
        </div>

        <button type="submit" class="submit-btn">Add Product</button>
    </form>
</body>
</html>