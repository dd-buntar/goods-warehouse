package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import com.github.dd_buntar.goods_warehouse.domain.entities.Storehouse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//@WebServlet("api/storehouse/*")
public class StorehouseServletImpl extends HttpServlet {
    private DomainStorehouseService storehouseService;
    private DomainShipmentService shipmentService;
    private DomainStorageLocationService storageLocationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/storehouse - получить все
                try {
                    List<Storehouse> storehouses = storehouseService.findAll();
                    req.setAttribute("storehouse", storehouses);
                    req.getRequestDispatcher("/views/storehouses.jsp").forward(req, resp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (pathInfo.endsWith("/edit")) {
                Long id = Long.parseLong(pathInfo.substring(1, pathInfo.length() - "/edit".length()));
                Storehouse curStorehouse = storehouseService.findById(id);
                req.setAttribute("curStorehouse", curStorehouse);

                List<Shipment> shipments = shipmentService.findAll();
                req.setAttribute("shipments", shipments);

                List<StorageLocation> locations = storageLocationService.findAll();
                req.setAttribute("locations", locations);

                req.getRequestDispatcher("/edit/editStorehouse.jsp").forward(req, resp);

            } else if (pathInfo.endsWith("/create")) {
                List<Shipment> shipments = shipmentService.findAll();
                req.setAttribute("shipments", shipments);

                List<StorageLocation> locations = storageLocationService.findAll();
                req.setAttribute("locations", locations);

                req.getRequestDispatcher("/create/addStorehouse.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid storehouse item ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("stockId");
        System.out.println(id);
        if (id != null && !id.isEmpty()) {
            doPut(req, resp);
        } else {
            try {
                final Long shipmentId = Long.valueOf(req.getParameter("shipmentId"));
                final Integer quantity = Integer.valueOf(req.getParameter("quantity"));
                final Long locationId = Long.valueOf(req.getParameter("locationId"));

                storehouseService.create(Storehouse.builder()
                        .shipmentId(shipmentId)
                        .quantity(quantity)
                        .locationId(locationId)
                        .build()
                );

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.sendRedirect(req.getContextPath() + "/storehouse");

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid number format for shipment_id, quantity or location_id");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error creating storehouse item: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("stockId"));
            final Long shipmentId = Long.valueOf(req.getParameter("shipmentId"));
            final Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            final Long locationId = Long.valueOf(req.getParameter("locationId"));

            storehouseService.update(Storehouse.builder()
                    .stockId(id)
                    .shipmentId(shipmentId)
                    .quantity(quantity)
                    .locationId(locationId)
                    .build()
            );
            resp.sendRedirect(req.getContextPath() + "/storehouse");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for stock_id, shipment_id, quantity or location_id");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating storehouse item: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Storehouse item ID required");
                return;
            }

            Long id = Long.valueOf(pathInfo.substring(1));
            storehouseService.deleteById(id);
            resp.getWriter().write("Storehouse item deleted successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid storehouse item ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting storehouse item: " + e.getMessage());
        }
    }
}