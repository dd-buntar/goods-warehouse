<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Goods Warehouse System</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; background-color: #f5f5f5; }
        .container { max-width: 800px; margin: 0 auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h1 { color: #333; text-align: center; }
        .menu { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-top: 30px; }
        .card { background: #f8f9fa; padding: 20px; border-radius: 8px; text-align: center; border-left: 5px solid #007bff; }
        .card h3 { margin-top: 0; color: #007bff; }
        .card a { display: inline-block; margin: 5px; padding: 8px 15px; background: #007bff; color: white; text-decoration: none; border-radius: 4px; }
        .card a:hover { background: #0056b3; }
        .links { margin-top: 10px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🏭 Goods Warehouse Management System</h1>
        <div class="menu">
            <div class="card">
                <h3>📦 Manufacturers</h3>
                <p>Manage product manufacturers</p>
                <div class="links">
                    <a href="manufacturers.jsp">View All</a>
                    <a href="add_manufacturer.jsp">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>📦 Products</h3>
                <p>Manage products catalog</p>
                <div class="links">
                    <a href="products.jsp">View All</a>
                    <a href="add_product.jsp">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>📍 Storage Locations</h3>
                <p>Manage warehouse locations</p>
                <div class="links">
                    <a href="storage_locations.jsp">View All</a>
                    <a href="add_storage_location.jsp">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>🚚 Shipments</h3>
                <p>Manage product shipments</p>
                <div class="links">
                    <a href="shipments.jsp">View All</a>
                    <a href="add_shipment.jsp">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>🏬 Storehouse</h3>
                <p>Manage warehouse inventory</p>
                <div class="links">
                    <a href="storehouse.jsp">View All</a>
                    <a href="add_storehouse.jsp">Add New</a>
                </div>
            </div>

            <div class="card">
                <h3>📊 Reports</h3>
                <p>View system reports</p>
                <div class="links">
                    <a href="reports.jsp">Inventory Report</a>
                    <a href="expiry_report.jsp">Expiry Report</a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>