package com.github.dd_buntar.goods_warehouse.app.servlet;

import com.github.dd_buntar.goods_warehouse.app.services.domain.DomainStorageLocationService;
import com.github.dd_buntar.goods_warehouse.domain.entities.StorageLocation;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@WebServlet("api/storage-locations/*")
public class StorageLocationServletImpl extends HttpServlet {
    private DomainStorageLocationService storageLocationService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        String pathInfo = req.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {  // GET /api/storage-locations - получить все
                List<StorageLocation> locations = storageLocationService.findAll();
                StringBuilder sb = new StringBuilder();
                for (StorageLocation loc : locations) {
                    sb.append(loc.toString()).append("\n");
                }
                resp.getWriter().write(sb.toString());

            } else if (pathInfo.startsWith("/")) {  // GET /api/storage-locations/{id} - получить по ID
                Long id = Long.parseLong(pathInfo.substring(1));
                StorageLocation location = storageLocationService.findById(id);

                if (location != null) {
                    resp.getWriter().write(location.toString());
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Storage location not found");
                }
            }

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid location ID format");
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
            final Integer rackNum = Integer.valueOf(req.getParameter("rack_num"));
            final Integer shelfNum = Integer.valueOf(req.getParameter("shelf_num"));

            storageLocationService.create(StorageLocation.builder()
                    .rackNum(rackNum)
                    .shelfNum(shelfNum)
                    .build()
            );

            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Storage location created successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for rack_num or shelf_num");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating storage location: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");

        try {
            final Long id = Long.valueOf(req.getParameter("location_id"));
            final Integer rackNum = Integer.valueOf(req.getParameter("rack_num"));
            final Integer shelfNum = Integer.valueOf(req.getParameter("shelf_num"));

            storageLocationService.update(StorageLocation.builder()
                    .locationId(id)
                    .rackNum(rackNum)
                    .shelfNum(shelfNum)
                    .build()
            );
            resp.getWriter().write("Storage location updated successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid number format for location_id, rack_num or shelf_num");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating storage location: " + e.getMessage());
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
                resp.getWriter().write("Storage location ID required");
                return;
            }

            Long id = Long.valueOf(pathInfo.substring(1));
            storageLocationService.deleteById(id);
            resp.getWriter().write("Storage location deleted successfully");

        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid location ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting storage location: " + e.getMessage());
        }
    }
}