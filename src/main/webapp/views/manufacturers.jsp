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
    <h1>Manufacturer</h1>

    <a href="${pageContext.request.contextPath}/manufacturer/create">Add New</a>
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>Phone</th>
                <th>Country</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="manufacturer" items="${manufacturer}">
                <tr>
                    <td>${manufacturer.manufacturerName}</td>
                    <td>${manufacturer.contactPhone}</td>
                    <td>${manufacturer.country}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/manufacturer/${manufacturer.manufacturerId}">View</a>
                        <a href="${pageContext.request.contextPath}/manufacturer/${manufacturer.manufacturerId}/edit">Edit</a>
                        <button onclick="delFrom('${manufacturer.manufacturerId}', 'manufacturer')" >Delete</button>

                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty manufacturer}">
        <p>No manufacturers found.</p>
    </c:if>
    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
</body>
</html>
