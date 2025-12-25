package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.entities.Shipment;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//@WebServlet("api/shipments/*")
public class ShipmentServletImpl extends HttpServlet {
    private DomainShipmentService shipmentService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/shipments - получить все
                try {
                    List<Shipment> shipments = shipmentService.findAll();
                    req.setAttribute("shipment", shipments);
                    req.getRequestDispatcher("/views/shipments.jsp").forward(req, resp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (pathInfo.startsWith("/")) {  // GET /api/shipments/{id} - получить по ID
                Long id = Long.parseLong(pathInfo.substring(1));
                Shipment shipment = shipmentService.findById(id);

                if (shipment != null) {
                    resp.getWriter().write(shipment.toString());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Shipment not found");
                }
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid shipment ID format");
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
            String productionDateStr = req.getParameter("production_date");
            String expiryDateStr = req.getParameter("expiry_date");
            String arrivalDateStr = req.getParameter("arrival_date");

            final Long productId = Long.valueOf(req.getParameter("product_id"));
            final Integer purchasePrice = Integer.valueOf(req.getParameter("purchase_price"));
            final Integer salePrice = Integer.valueOf(req.getParameter("sale_price"));

            // Парсинг дат
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            final LocalDateTime productionDate = LocalDateTime.parse(productionDateStr, formatter);
            final LocalDateTime expiryDate = LocalDateTime.parse(expiryDateStr, formatter);
            final LocalDateTime arrivalDate = LocalDateTime.parse(arrivalDateStr, formatter);

            shipmentService.create(Shipment.builder()
                    .productId(productId)
                    .purchasePrice(purchasePrice)
                    .salePrice(salePrice)
                    .productionDate(productionDate)
                    .expiryDate(expiryDate)
                    .arrivalDate(arrivalDate)
                    .build()
            );

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Shipment created successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for numeric fields");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating shipment: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("shipment_id"));
            final Long productId = Long.valueOf(req.getParameter("product_id"));
            final Integer purchasePrice = Integer.valueOf(req.getParameter("purchase_price"));
            final Integer salePrice = Integer.valueOf(req.getParameter("sale_price"));
            final String productionDateStr = req.getParameter("production_date");
            final String expiryDateStr = req.getParameter("expiry_date");
            final String arrivalDateStr = req.getParameter("arrival_date");

            // Парсинг дат
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime productionDate = LocalDateTime.parse(productionDateStr, formatter);
            LocalDateTime expiryDate = LocalDateTime.parse(expiryDateStr, formatter);
            LocalDateTime arrivalDate = LocalDateTime.parse(arrivalDateStr, formatter);

            shipmentService.update(Shipment.builder()
                    .shipmentId(id)
                    .productId(productId)
                    .purchasePrice(purchasePrice)
                    .salePrice(salePrice)
                    .productionDate(productionDate)
                    .expiryDate(expiryDate)
                    .arrivalDate(arrivalDate)
                    .build()
            );
            resp.getWriter().write("Shipment updated successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for numeric fields");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating shipment: " + e.getMessage());
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
                resp.getWriter().write("Shipment ID required");
                return;
            }

            Long id = Long.valueOf(pathInfo.substring(1));
            shipmentService.deleteById(id);
            resp.getWriter().write("Shipment deleted successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid shipment ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting shipment: " + e.getMessage());
        }
    }
}