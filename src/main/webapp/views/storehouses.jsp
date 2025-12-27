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
    <h1>Storehouse</h1>

    <!-- Pagination Controls -->
    <c:if test="${totalPages > 1}">
        <div class="pagination-container">
            <!-- Page Size Selector -->
            <div class="page-size-selector">
                <label for="pageSize">Items per page:</label>
                <select id="pageSize" onchange="changePageSize(this.value)">
                    <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                    <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                    <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                </select>
            </div>

            <!-- Pagination Info -->
            <div class="pagination-info">
                <c:set var="startItem" value="${(currentPage - 1) * pageSize + 1}" />
                <c:set var="endItem" value="${startItem + storehouse.size() - 1}" />
                Showing ${startItem} to ${endItem} of ${totalCount} entries
            </div>

            <!-- Pagination Controls -->
            <div class="pagination-controls">
                <c:if test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&size=${pageSize}" class="page-link">Previous</a>
                </c:if>

                <c:forEach var="i" begin="1" end="${totalPages}">
                    <c:if test="${i >= currentPage - 2 && i <= currentPage + 2}">
                        <c:choose>
                            <c:when test="${i == currentPage}">
                                <span class="page-link current">${i}</span>
                            </c:when>
                            <c:otherwise>
                                <a href="?page=${i}&size=${pageSize}" class="page-link">${i}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                </c:forEach>

                <c:if test="${currentPage < totalPages}">
                    <a href="?page=${currentPage + 1}&size=${pageSize}" class="page-link">Next</a>
                </c:if>
            </div>
        </div>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>Shipment</th>
                <th>Quantity</th>
                <th>Location</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="storehouse" items="${storehouse}">
                <tr>
                    <td>${storehouse.shipmentId}</td>
                    <td>${storehouse.quantity}</td>
                    <td>${storehouse.locationId}</td>
                    <td class="actions">
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}">View</a>
                        <a href="${pageContext.request.contextPath}/storehouse/${storehouse.stockId}/edit">Edit</a>
                        <button onclick="delFrom('${storehouse.stockId}', 'storehouse')" >Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <c:if test="${empty storehouse}">
        <p>No storehouses found.</p>
    </c:if>


    <script src="${pageContext.request.contextPath}/js/scripts.js"></script>
    <script>
        function changePageSize(size) {
            const url = new URL(window.location);
            url.searchParams.set('size', size);
            url.searchParams.set('page', '1'); // Reset to first page when changing size
            window.location.href = url.toString();
        }
    </script>
</body>
</html>
