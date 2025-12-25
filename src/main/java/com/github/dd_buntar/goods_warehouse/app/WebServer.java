package com.github.dd_buntar.goods_warehouse.app;

import com.github.dd_buntar.goods_warehouse.app.servlet.ProductServletImpl;
import com.github.dd_buntar.goods_warehouse.app.servlet.ShipmentServletImpl;
import com.github.dd_buntar.goods_warehouse.app.servlet.StorageLocationServletImpl;
import com.github.dd_buntar.goods_warehouse.app.servlet.StorehouseServletImpl;
import java.io.File;

import com.github.dd_buntar.goods_warehouse.app.services.ServiceFactory;
import com.github.dd_buntar.goods_warehouse.app.servlet.ManufacturerServletImpl;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class WebServer {

    private ServiceFactory serviceFactory;

    private Tomcat tomcat;

    public WebServer(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    public void start() throws LifecycleException {
        // Создаем экземпляр Tomcat
        tomcat = new Tomcat();

        // Устанавливаем порт (по умолчанию 8080)
        tomcat.setPort(8080);

        // Указываем временную директорию
        tomcat.setBaseDir("temp");

        tomcat.getConnector();
        // Добавляем webapp (вашу папку webapp)
        String webappDirLocation = "src/main/webapp";
        Context context = tomcat.addWebapp("",
                new File(webappDirLocation).getAbsolutePath());

        // Настройка кодировки
        context.setRequestCharacterEncoding("UTF-8");
        context.setResponseCharacterEncoding("UTF-8");

        // context.addApplicationListener(JasperInitializer.class.getName());
        context.setParentClassLoader(Thread.currentThread().getContextClassLoader());

        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                new File("target/classes").getAbsolutePath(), "/"));
        context.setResources(resources);

        addServlets(context);

        // Запускаем сервер
        tomcat.start();
        System.out.println("Tomcat запущен на http://localhost:8080");
        System.out.println("Нажмите Ctrl+C для остановки");

        // Ждем остановки
        tomcat.getServer().await();
    }

    public void stop() throws LifecycleException {
        if (tomcat != null) {
            tomcat.stop();
        }
    }

    private void addServlets(Context ctx) {
        Tomcat.addServlet(ctx, "manufacturerServlet",
                new ManufacturerServletImpl(serviceFactory.getDomainManufacturerService()));
        ctx.addServletMappingDecoded("/manufacturer", "manufacturerServlet");

        Tomcat.addServlet(ctx, "productServlet",
                new ProductServletImpl(serviceFactory.getDomainProductService()));
        ctx.addServletMappingDecoded("/product", "productServlet");

        Tomcat.addServlet(ctx, "shipmentServlet",
                new ShipmentServletImpl(serviceFactory.getDomainShipmentService()));
        ctx.addServletMappingDecoded("/shipment", "shipmentServlet");

        Tomcat.addServlet(ctx, "storageLocationServlet",
                new StorageLocationServletImpl(serviceFactory.getDomainStorageLocationService()));
        ctx.addServletMappingDecoded("/storageLocation", "storageLocationServlet");

        Tomcat.addServlet(ctx, "storehouseServlet",
                new StorehouseServletImpl(serviceFactory.getDomainStorehouseService()));
        ctx.addServletMappingDecoded("/storehouse", "storehouseServlet");
    }
}
