package com.example.servlet;

import com.example.accounts.AccountService;
import com.example.accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute(AccountService.ACCOUNT_SERVICE_ATTRIBUTE);
        if (accountService == null) {
            accountService = new AccountService();
            getServletContext().setAttribute(AccountService.ACCOUNT_SERVICE_ATTRIBUTE, accountService);
        }
    }
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            resp.sendRedirect("files");
            return;
        }

        req.getRequestDispatcher("authorization.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");

        if (accountService.checkUser(login, password)) {
            HttpSession session = req.getSession();
            UserProfile profile = accountService.getUserByLogin(login);
            session.setAttribute("user", profile);
            resp.sendRedirect("files");
        } else {
            req.setAttribute("error", "Неверный логин или пароль");
            req.getRequestDispatcher("authorization.jsp").forward(req, resp);
        }
    }
}