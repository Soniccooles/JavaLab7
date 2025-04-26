package com.example.servlet;

import com.example.accounts.AccountService;
import com.example.accounts.UserProfile;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
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
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");

        if (accountService.getUserByLogin(login) != null) {
            req.setAttribute("error", "Пользователь с таким логином уже существует");
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }

        UserProfile profile = new UserProfile(login, password, email);
        accountService.addNewUser(profile);
        resp.sendRedirect("login");
    }
}