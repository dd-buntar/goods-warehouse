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
    <h1>Storage location</h1>


    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Rack</th>
                <th>Shelf</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="storageLocation" items="${storageLocation}">
                <tr>
                    <td>${storageLocation.locationId}</td>
                    <td>${storageLocation.rackNum}</td>
                    <td>${storageLocation.shelfNum}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/storageLocation/${storageLocation.locationId}">View</a>
                        <a href="${pageContext.request.contextPath}/storageLocation/${storageLocation.locationId}/edit">Edit</a>
                        <a href="${pageContext.request.contextPath}/storageLocation/${storageLocation.locationId}/delete" onclick="return confirm('Are you sure you want to delete this location?')">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty storageLocation}">
        <p>No locations found.</p>
    </c:if>
</body>
</html>
