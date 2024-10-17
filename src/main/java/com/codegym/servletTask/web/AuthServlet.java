package com.codegym.servletTask.web;

import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");

        int attempt = 1;
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("gameAttempt")) {
                attempt = Integer.parseInt(cookie.getValue());
                attempt++;
                cookie.setValue("" + attempt);
                response.addCookie(cookie);
                break;
            }
        }

        if (attempt == 1) {
            Cookie cookie = new Cookie("gameAttempt", "" + attempt);
            response.addCookie(cookie);
        }

        HttpSession session = request.getSession(true);
        String name = request.getParameter("name");
        session.setAttribute("name", name);
        response.sendRedirect(request.getContextPath() + "/quest");
    }
}
