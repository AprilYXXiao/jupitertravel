package com.laioffer.jupiter.servlet;

import com.laioffer.jupiter.entity.Location;
import com.laioffer.jupiter.external.GoogleClient;
import com.laioffer.jupiter.external.GoogleException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("cityName");
        if (cityName == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        GoogleClient client = new GoogleClient();
        try {
            ServletUtil.writeLocationMap(response, client.searchPlace(cityName));
        } catch (GoogleException e) {
            throw new ServletException(e);
        }
    }
}