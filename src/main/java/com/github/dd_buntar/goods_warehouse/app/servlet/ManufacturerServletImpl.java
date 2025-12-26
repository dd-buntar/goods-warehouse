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

//            } else if (pathInfo.equals("/")) {  // GET /api/books/{id} - получить по ID
//                Long id = Long.parseLong(pathInfo.substring(1));
//                Manufacturer manufacturer = manufacturerService.findById(id);
//
//                if (manufacturer != null) {
//                    resp.getWriter().write(manufacturer.toString());
//                } else {
//                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    resp.getWriter().write("Manufacturer not found");
//                }

            } else if (pathInfo.endsWith("/edit")) {
                doPut(req, resp);
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

        try {
            final String name = req.getParameter("manufacturerName");
            final String contactPhone = req.getParameter("contactPhone");
            final String country = req.getParameter("country");
            System.out.println(country);

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("manufacturer_id"));
            final String name = req.getParameter("name");
            final String contactPhone = req.getParameter("contact_phone");
            final String country = req.getParameter("country");

            manufacturerService.update(Manufacturer.builder()
                    .manufacturerId(id)
                    .manufacturerName(name)
                    .contactPhone(contactPhone)
                    .country(country)
                    .build()
            );
            resp.getWriter().write("Manufacturer updated successfully");

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
