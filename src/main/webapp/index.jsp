<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods Warehouse System</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="container">
        <h1>🏭 Goods Warehouse Management System</h1>
        <div class="menu">
            <div class="card">
                <h3>📦 Manufacturers</h3>
                <p>Manage product manufacturers</p>
                <div class="links">
                    <a href="${pageContext.request.contextPath}/manufacturer">View All</a>
                    <a href="${pageContext.request.contextPath}/manufacturer/create">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>📦 Products</h3>
                <p>Manage products catalog</p>
                <div class="links">
                    <a href="${pageContext.request.contextPath}/product">View All</a>
                    <a href="${pageContext.request.contextPath}/product/create">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>📍 Storage Locations</h3>
                <p>Manage warehouse locations</p>
                <div class="links">
                    <a href="${pageContext.request.contextPath}/storageLocation">View All</a>
                    <a href="${pageContext.request.contextPath}/storageLocation/create">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>🚚 Shipments</h3>
                <p>Manage product shipments</p>
                <div class="links">
                    <a href="${pageContext.request.contextPath}/shipment">View All</a>
                    <a href="${pageContext.request.contextPath}/shipment/create">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>🏬 Storehouse</h3>
                <p>Manage warehouse inventory</p>
                <div class="links">
                    <a href="${pageContext.request.contextPath}/storehouse">View All</a>
                    <a href="${pageContext.request.contextPath}/storehouse/create">Add New</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>