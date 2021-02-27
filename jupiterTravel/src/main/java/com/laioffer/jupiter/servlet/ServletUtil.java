package com.laioffer.jupiter.servlet;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.entity.Location;
import com.laioffer.jupiter.entity.Place;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class ServletUtil {
    public static void writeLocationMap(HttpServletResponse response, List<Place> tripPlaces) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().print(new ObjectMapper().writeValueAsString(tripPlaces));
    }
    public static String encryptPassword(String userId, String password) throws IOException {
        return DigestUtils.md5Hex(userId + DigestUtils.md5Hex(password)).toLowerCase();
    }

    public static <T> T readRequestBody(Class<T> cl, HttpServletRequest request) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(request.getReader(), cl);
        } catch (JsonParseException | JsonMappingException e) {
            return null;
        }
    }
}