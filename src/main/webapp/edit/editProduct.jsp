<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <h1>Edit Product</h1>
        <form action="${pageContext.request.contextPath}/product" accepted-charset='utf-8' method="post">
            Product ID: ${curProduct.productId}
                <input type="hidden" name="productId" value="${curProduct.productId}">
                <div class="form-group">
                    <label for="productName">Product Name:</label>
                    <input type="text" id="productName" name="productName"
                           value="${curProduct.productName}" required>
                </div>

                <div class="form-group">
                    <label for="manufacturerId">Manufacturer:</label>
                    <select id="manufacturerId" name="manufacturerId" required>
                        <c:forEach var="manufacturer" items="${manufacturers}">
                            <option value="${manufacturer.manufacturerId}" ${manufacturer.manufacturerId == curProduct.manufacturerId ? 'selected' : ''}>
                                ${manufacturer.manufacturerName}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="weight">Weight (grams):</label>
                    <input type="number" id="weight" name="weight"
                           value="${curProduct.weight}" required min="1">
                </div>

                <div class="form-group">
                    <label for="description">Description:</label>
                    <input type="text" id="description" name="description"
                        value="${curProduct.description}">
                    </textarea>
                </div>

                <button type="submit" class="submit-btn">Update Product</button>
        </form>
</body>
</html>
