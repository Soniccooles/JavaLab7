package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/files")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String path = req.getParameter("path");

        if (path == null || path.isEmpty()) {
            path = System.getProperty("user.home");
        }

        File dir = new File(path);

        String parentDir = dir.getParent();
        File[] files = dir.listFiles();
        System.out.println("Current path: " + path); // Добавьте эту строку
        System.out.println("Parent dir: " + parentDir); // И эту
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String creationDate = dateFormat.format(new Date());

        req.setAttribute("currentDir", path);
        req.setAttribute("files", files);
        req.setAttribute("creationDate", creationDate);
        req.setAttribute("parentDir", parentDir);
        dir.lastModified().
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}