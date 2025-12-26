package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//@WebServlet("api/manufacturers/*")
public class ManufacturerServletImpl extends HttpServlet {
    private DomainManufacturerService manufacturerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/books - получить всех
                try {
                    List<Manufacturer> manufacturers = manufacturerService.findAll();
                    req.setAttribute("manufacturer", manufacturers);
                    req.getRequestDispatcher("/views/manufacturers.jsp").forward(req, resp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            } else if (pathInfo.endsWith("/edit")) {
                Long id = Long.parseLong(pathInfo.substring(1, pathInfo.length() - "/edit".length()));
                Manufacturer curManufacturer = manufacturerService.findById(id);
                req.setAttribute("curManufacturer", curManufacturer);
                req.getRequestDispatcher("/edit/editManufacturer.jsp").forward(req, resp);

            } else if (pathInfo.endsWith("/create")) {
                req.getRequestDispatcher("/create/addManufacturer.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid manufacturer ID format");
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

        String id = req.getParameter("manufacturerId");
        if (id != null && !id.isEmpty()) {
            doPut(req, resp);
        } else {

            try {
                final String name = req.getParameter("manufacturerName");
                final String contactPhone = req.getParameter("contactPhone");
                final String country = req.getParameter("country");

                manufacturerService.create(Manufacturer.builder()
                        .manufacturerName(name)
                        .contactPhone(contactPhone)
                        .country(country)
                        .build()
                );

                resp.setStatus(HttpServletResponse.SC_CREATED);
                resp.sendRedirect(req.getContextPath() + "/manufacturer");

            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error creating manufacturer: " + e.getMessage());
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        req.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("manufacturerId"));
            final String name = req.getParameter("manufacturerName");
            final String contactPhone = req.getParameter("contactPhone");
            final String country = req.getParameter("country");

            final Manufacturer manufacturer = Manufacturer.builder()
                    .manufacturerId(id)
                    .manufacturerName(name)
                    .contactPhone(contactPhone)
                    .country(country)
                    .build();
            manufacturerService.update(manufacturer);

            resp.sendRedirect(req.getContextPath() + "/manufacturer");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid manufacturer ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating manufacturer: " + e.getMessage());
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
                resp.getWriter().write("Manufacturer ID required");
                return;
            }

            Long id = Long.valueOf(pathInfo.substring(1));
            manufacturerService.deleteById(id);
            resp.getWriter().write("Manufacturer deleted successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid manufacturer ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting manufacturer: " + e.getMessage());
        }
    }
}
