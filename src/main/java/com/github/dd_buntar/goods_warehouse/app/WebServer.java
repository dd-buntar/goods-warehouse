package com.github.dd_buntar.goods_warehouse.app;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class WebServer {
    public static void main(String[] args) throws LifecycleException {
        // Создаем экземпляр Tomcat
        Tomcat tomcat = new Tomcat();

        // Устанавливаем порт (по умолчанию 8080)
        tomcat.setPort(8080);

        // Указываем временную директорию
        tomcat.setBaseDir("temp");

        // Добавляем webapp (вашу папку webapp)
        String webappDirLocation = "src/main/webapp/";
        Context context = tomcat.addWebapp("",
                new File(webappDirLocation).getAbsolutePath());

        // Настройка кодировки
        context.setRequestCharacterEncoding("UTF-8");
        context.setResponseCharacterEncoding("UTF-8");

        // Запускаем сервер
        tomcat.start();
        System.out.println("Tomcat запущен на http://localhost:8080");
        System.out.println("Нажмите Ctrl+C для остановки");

        // Ждем остановки
        tomcat.getServer().await();
    }
}
