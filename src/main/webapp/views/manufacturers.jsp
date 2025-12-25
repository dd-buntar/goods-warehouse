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


    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Phone</th>
                <th>Country</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="manufacturer" items="${manufacturer}">
                <tr>
                    <td>${manufacturer.manufacturerId}</td>
                    <td>${manufacturer.manufacturerName}</td>
                    <td>${manufacturer.contactPhone}</td>
                    <td>${manufacturer.country}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/manufacturer/${manufacturer.manufacturerId}">View</a>
                        <a href="${pageContext.request.contextPath}/manufacturer/${manufacturer.manufacturerId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/manufacturer/${manufacturer.manufacturerId}/delete" onclick="return confirm('Are you sure you want to delete this manufacturer?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty manufacturer}">
        <p>No manufacturers found.</p>
    </c:if>
</body>
</html>
