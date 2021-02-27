package com.laioffer.jupiter.servlet;

import com.laioffer.jupiter.db.MySQLConnection;
import com.laioffer.jupiter.db.MySQLException;
import com.laioffer.jupiter.entity.Place;
import com.laioffer.jupiter.recommendation.RouteRecommender;
import com.laioffer.jupiter.recommendation.RecommendationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// 目前只是放和Trip Servlet一樣的code => 把用戶選的地點返回前端。排序的功能待完成

@WebServlet(name = "RecommendationServlet", urlPatterns = {"/recommendation"})
public class RecommendationServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String userId = (String) session.getAttribute("user_id");
        MySQLConnection connection = null;
        try {
            connection = new MySQLConnection();
            List<Place> tripPlace = connection.getTripPlaces(connection.getTripPlaceIds(userId));
            ServletUtil.writeLocationMap(response, tripPlace);
        } catch (MySQLException e) {
            throw new ServletException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
