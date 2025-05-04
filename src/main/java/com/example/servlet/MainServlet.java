package com.example.servlet;

import com.example.DBService.DBService;
import com.example.accounts.AccountService;
import com.example.accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.http.HttpSession;

@WebServlet("/files")
public class MainServlet extends HttpServlet {
    private AccountService accountService;
    @Override
    public void init() throws ServletException {

        accountService = (AccountService) getServletContext().getAttribute(AccountService.ACCOUNT_SERVICE_ATTRIBUTE);
        if (accountService == null) {
            accountService = new AccountService();
            getServletContext().setAttribute(AccountService.ACCOUNT_SERVICE_ATTRIBUTE, accountService);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect("login");
            return;
        }

        UserProfile profile = (UserProfile) session.getAttribute("user");
        String homeDir = accountService.getUserHomeDir(profile.getLogin());
        String requestedPath = req.getParameter("path");
        if (requestedPath == null || !Paths.get(requestedPath).normalize().startsWith(homeDir)) {
            requestedPath = homeDir;
        }

        File userDir = new File(requestedPath);
        if (!userDir.exists()) userDir.mkdirs();

        File dir = new File(requestedPath);
        String parentDir = dir.getParent();
        File[] files = dir.listFiles();
        System.out.println("Current path: " + requestedPath);
        System.out.println("Parent dir: " + parentDir);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String creationDate = dateFormat.format(new Date());
        if (parentDir != null && !Paths.get(parentDir).normalize().startsWith(homeDir)) {
            parentDir = homeDir;
        }

        req.setAttribute("currentDir", dir.getAbsolutePath());
        req.setAttribute("files", files);
        req.setAttribute("creationDate", creationDate);
        req.setAttribute("parentDir", parentDir);
        req.getRequestDispatcher("mypage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}