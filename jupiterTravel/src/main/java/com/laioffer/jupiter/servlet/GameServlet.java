package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.external.GoogleClient;
import com.laioffer.jupiter.external.GoogleException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// this is a place holder. Duplicate functions

@WebServlet(name = "GameServlet", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameName = request.getParameter("game_name");
        GoogleClient client = new GoogleClient();

        response.setContentType("application/json;charset=UTF-8");
        try {
            if (gameName != null) {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchPlace("SanFrancisco")));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.searchPlace("SanFrancisco")));
            }
        } catch (GoogleException e) {
            throw new ServletException(e);
        }
    }
}