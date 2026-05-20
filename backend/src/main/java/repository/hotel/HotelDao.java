package repository.hotel;

import model.hotel.Hotel;
import repository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDao {
    private final Connection connection;

    public HotelDao() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Hotel insert(Hotel hotel) {
        String sql = "INSERT INTO hotels (name, city, stars) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getCity());
            stmt.setInt(3, hotel.getStars());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                hotel = new Hotel(keys.getInt(1), hotel.getName(), hotel.getCity(), hotel.getStars());
            }
            return hotel;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea hotelului.", e);
        }
    }

    public Hotel findById(int id) {
        String sql = "SELECT id, name, city, stars FROM hotels WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea hotelului.", e);
        }
    }

    public List<Hotel> findAll() {
        String sql = "SELECT id, name, city, stars FROM hotels ORDER BY city, name";
        List<Hotel> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea hotelurilor.", e);
        }
    }

    public void update(Hotel hotel) {
        String sql = "UPDATE hotels SET name = ?, city = ?, stars = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getCity());
            stmt.setInt(3, hotel.getStars());
            stmt.setInt(4, hotel.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea hotelului.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM hotels WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea hotelului.", e);
        }
    }

    private Hotel mapRow(ResultSet rs) throws SQLException {
        return new Hotel(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("city"),
                rs.getInt("stars")
        );
    }
}
