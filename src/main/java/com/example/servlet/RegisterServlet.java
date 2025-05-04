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
        String error = checkRegistrationData(login, password, email); // Для бдшки

        if (error != null) {
            req.setAttribute("error", error);
            req.getRequestDispatcher("registration.jsp").forward(req, resp);
            return;
        }

        UserProfile profile = new UserProfile(login, password, email);
        accountService.addNewUser(profile);
        resp.sendRedirect("login");
    }

    private String checkRegistrationData(String login, String password, String email) {
        String emailDomain = email.substring(email.indexOf("@") + 1).toLowerCase();
        if (accountService.getUserByLogin(login) != null) {
            return "Пользователь с таким именем уже существует";
        }

        if (login.length() > 100) {
            return "Слишком длинное имя пользователя. Допускается до 100 символов";
        }

        if (password.length() > 60) {
            return "Слишком длинный пароль. Допускается до 60 символов";
        }

        if (email.length() > 100) {
            return "Слишком длинный адрес электронной почты. Допускается до 100 символов";
        }

        if (!emailDomain.equals("gmail.com") && !emailDomain.equals("mail.ru") && !emailDomain.equals("yandex.ru")) {
            return "Поддерживаются только адреса gmail.com, mail.ru и yandex.ru";
        }

        return null;
    }
}