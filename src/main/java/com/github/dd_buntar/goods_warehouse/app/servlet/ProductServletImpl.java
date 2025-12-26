package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainManufacturerService;
import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainProductService;
import com.github.dd_buntar.goods_warehouse.domain.entities.Manufacturer;
import com.github.dd_buntar.goods_warehouse.domain.entities.Product;
import com.github.dd_buntar.goods_warehouse.domain.repositories.dto.ProductWithQuantity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
//@WebServlet("api/products/*")
public class ProductServletImpl extends HttpServlet {
    private DomainProductService productService;
    private DomainManufacturerService manufacturerService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/products - получить все продукты

                try {
                    List<ProductWithQuantity> products = productService.findAllWithQuantity();
                    req.setAttribute("productWithQuantity", products);
                    req.getRequestDispatcher("/views/products.jsp").forward(req, resp);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

//            } else if (pathInfo.startsWith("/")) {  // GET /api/products/{id} - получить по ID
//                Long id = Long.parseLong(pathInfo.substring(1));
//                Product product = productService.findById(id);
//
//                if (product != null) {
//                    resp.getWriter().write(product.toString());
//                } else {
//                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    resp.getWriter().write("Product not found");
//                }
//            }
            } else if (pathInfo.endsWith("/edit")) {
                doPut(req, resp);
            } else if (pathInfo.endsWith("/create")) {
                List<Manufacturer> manufacturers = manufacturerService.findAll();
                req.setAttribute("manufacturers", manufacturers);
                req.getRequestDispatcher("/create/addProduct.jsp").forward(req, resp);
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid product ID format");
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
            final String name = req.getParameter("productName");
            final Long manufacturerId = Long.parseLong(req.getParameter("manufacturerId"));
            final Integer weight = Integer.parseInt(req.getParameter("weight"));
            final String description = req.getParameter("description");

            System.out.println(manufacturerId);
            System.out.println(weight);

            productService.create(Product.builder()
                    .productName(name)
                    .manufacturerId(manufacturerId)
                    .weight(weight)
                    .description(description)
                    .build()
            );

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.sendRedirect(req.getContextPath() + "/product");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for manufacturer_id or weight_grams");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating product: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("product_id"));
            final String name = req.getParameter("name");
            final Long manufacturerId = Long.parseLong(req.getParameter("manufacturer_id"));
            final Integer weight = Integer.parseInt(req.getParameter("weight_grams"));
            final String description = req.getParameter("description");

            productService.update(Product.builder()
                    .productId(id)
                    .productName(name)
                    .manufacturerId(manufacturerId)
                    .weight(weight)
                    .description(description)
                    .build()
            );
            resp.getWriter().write("Product updated successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for product_id, manufacturer_id or weight_grams");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating product: " + e.getMessage());
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
                resp.getWriter().write("Product ID required");
                return;
            }

            Long id = Long.parseLong(pathInfo.substring(1));
            productService.deleteById(id);
            resp.getWriter().write("Product deleted successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid product ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting product: " + e.getMessage());
        }
    }
}