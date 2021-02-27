package com.laioffer.jupiter.db;
import com.laioffer.jupiter.entity.Location;
import com.laioffer.jupiter.entity.Place;
import com.laioffer.jupiter.entity.User;
import java.sql.*;
import java.util.*;
public class MySQLConnection {
    private final Connection conn;
    public MySQLConnection() throws MySQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(MySQLDBUtil.getMySQLAddress());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MySQLException("Failed to connect to Database");
        }
    }
    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addTripPlace(String userId, Place place ) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }

        // 一旦用戶收蕆後，就把place的相關資料存入place table中

        savePlace(place);
        String sql = "INSERT IGNORE INTO trips (user_id, place_id) VALUES (?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, place.getPlaceId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to save favorite item to Database");
        }
    }

    //提供userID 和 placeId 給資料庫，資料庫找到資料後，進行刪除

    public void removeTripPlace(String userId, String placeId) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }
        String sql = "DELETE FROM trips WHERE user_id = ? AND place_id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, placeId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to delete favorite location to Database");
        }
    }

    //
    public void savePlace(Place place) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }
        String sql = "INSERT IGNORE INTO locations VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, place.getPlaceId());
            statement.setString(2, place.getName());
            statement.setDouble(3, place.getLat());
            statement.setDouble(4, place.getLng());
            statement.setString(5, place.getAddress());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to add location to Database");
        }
    }
    // 在資料庫中以user_id來搜尋其選擇過的景點的ID，返回景點ID所組成的list
    public List<String> getTripPlaceIds(String userId) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }
        String sql = "SELECT place_id FROM trips WHERE user_id = ?";
        List<String> tripPlaceIds = new ArrayList<>();
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String placeId = rs.getString("place_id");
                tripPlaceIds.add(placeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to delete favorite item to Database");
        }
        return tripPlaceIds;
    }

    //用getTripPlaceIds 方法中拿到的ID，去place table找地名
    public List<Place> getTripPlaces(List<String> tripPlaceIds) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }

//        Map<String, String> placeMap = new HashMap<>();

        List<Place> tripPlaces = new ArrayList<>();

        String sql = "SELECT * FROM places WHERE id = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            for (String id : tripPlaceIds) {
                statement.setString(1, id);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    Place place = new Place.Builder().placeId(rs.getString("id")).name(rs.getString("name"))
                            .lat(rs.getDouble("lat")).lng(rs.getDouble("lng"))
                            .address(rs.getString("address")).build();
                    tripPlaces.add(place);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to get trip places from Database");
        }
        return tripPlaces;
    }

    public String verifyLogin(String userId, String password) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }
        String name = "";
        String sql = "SELECT first_name, last_name FROM users WHERE id = ? AND password = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                name = rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to verify user id and password from Database");
        }
        return name;
    }
    public boolean addUser(User user) throws MySQLException {
        if (conn == null) {
            System.err.println("DB connection failed");
            throw new MySQLException("Failed to connect to Database");
        }
        String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getUserId());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getFirstName());
            statement.setString(4, user.getLastName());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new MySQLException("Failed to get user information from Database");
        }
    }
}
