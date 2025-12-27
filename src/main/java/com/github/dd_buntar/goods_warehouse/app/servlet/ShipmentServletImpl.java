package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainShipmentService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
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
    private DomainProductService productService;

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

            } else if (pathInfo.endsWith("/edit")) {
                Long id = Long.parseLong(pathInfo.substring(1, pathInfo.length() - "/edit".length()));
                Shipment curShipment = shipmentService.findById(id);
                req.setAttribute("curShipment", curShipment);

                List<Product> products = productService.findAll();
                req.setAttribute("products", products);

                req.getRequestDispatcher("/edit/editShipment.jsp").forward(req, resp);

            } else if (pathInfo.endsWith("/create")) {
                List<Product> products = productService.findAll();
                req.setAttribute("products", products);
                req.getRequestDispatcher("/create/addShipment.jsp").forward(req, resp);
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
        req.setCharacterEncoding("UTF-8");

        String id = req.getParameter("productId");
        System.out.println(id);
        if (id != null && !id.isEmpty()) {
            doPut(req, resp);
        } else {

            try {
                String productionDateStr = req.getParameter("productionDate");
                String expiryDateStr = req.getParameter("expiryDate");
                String arrivalDateStr = req.getParameter("arrivalDate");

                final Long productId = Long.valueOf(req.getParameter("productId"));
                final Integer purchasePrice = Integer.valueOf(req.getParameter("purchasePrice"));
                final Integer salePrice = Integer.valueOf(req.getParameter("salePrice"));

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
                resp.sendRedirect(req.getContextPath() + "/shipment");

            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid number format for numeric fields");
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error creating shipment: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("shipmentId"));
            final Long productId = Long.valueOf(req.getParameter("productId"));
            final Integer purchasePrice = Integer.valueOf(req.getParameter("purchasePrice"));
            final Integer salePrice = Integer.valueOf(req.getParameter("salePrice"));
            final String productionDateStr = req.getParameter("productionDate");
            final String expiryDateStr = req.getParameter("expiryDate");
            final String arrivalDateStr = req.getParameter("arrivalDate");

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
            resp.sendRedirect(req.getContextPath() + "/shipment");

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