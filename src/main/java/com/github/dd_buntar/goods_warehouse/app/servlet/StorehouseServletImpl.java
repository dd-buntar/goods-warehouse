package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorehouseService;
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
@WebServlet("api/storehouse/*")
public class StorehouseServletImpl extends HttpServlet {
    private DomainStorehouseService storehouseService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/storehouse - получить все
                List<Storehouse> storehouseItems = storehouseService.findAll();
                StringBuilder sb = new StringBuilder();
                for (Storehouse item : storehouseItems) {
                    sb.append(item.toString()).append("\n");
                }
                resp.getWriter().write(sb.toString());

            } else if (pathInfo.startsWith("/")) {  // GET /api/storehouse/{id} - получить по ID
                Long id = Long.parseLong(pathInfo.substring(1));
                Storehouse storehouseItem = storehouseService.findById(id);

                if (storehouseItem != null) {
                    resp.getWriter().write(storehouseItem.toString());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Storehouse item not found");
                }
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

        try {
            final Long shipmentId = Long.valueOf(req.getParameter("shipment_id"));
            final Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            final Long locationId = Long.valueOf(req.getParameter("location_id"));

            storehouseService.create(Storehouse.builder()
                    .shipmentId(shipmentId)
                    .quantity(quantity)
                    .locationId(locationId)
                    .build()
            );

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Storehouse item created successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for shipment_id, quantity or location_id");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating storehouse item: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("stock_id"));
            final Long shipmentId = Long.valueOf(req.getParameter("shipment_id"));
            final Integer quantity = Integer.valueOf(req.getParameter("quantity"));
            final Long locationId = Long.valueOf(req.getParameter("location_id"));

            storehouseService.update(Storehouse.builder()
                    .stockId(id)
                    .shipmentId(shipmentId)
                    .quantity(quantity)
                    .locationId(locationId)
                    .build()
            );
            resp.getWriter().write("Storehouse item updated successfully");

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