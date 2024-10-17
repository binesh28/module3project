package com.codegym.servletTask.web;

import com.codegym.servletTask.model.Answer;
import com.codegym.servletTask.model.Question;
import com.codegym.servletTask.model.Repository;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Collection;

public class QuestServlet extends HttpServlet {

    private final Repository repository = new Repository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String answerId = request.getParameter("answerId");
        Question question;
        if (answerId != null) {
            question = repository.getAnswerById(Integer.parseInt(answerId)).getTo();
        } else {
            question = repository.getQuestionById(1);
        }

        Collection<Answer> answers = repository.getAnswersByFromQuestionId(question.getId());
        request.setAttribute("question", question);
        request.setAttribute("answers", answers);
        addStatistics(request);
        request.getRequestDispatcher("/quest.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void addStatistics(HttpServletRequest request) {
        request.setAttribute("ip", request.getRemoteAddr());

        HttpSession session = request.getSession();
        request.setAttribute("userName", session.getAttribute("name"));

        Cookie[] cookies = request.getCookies();
        request.setAttribute("attempt", findCookiesValueByName(cookies));
    }

    private String findCookiesValueByName(Cookie[] cookies) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("gameAttempt")) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
