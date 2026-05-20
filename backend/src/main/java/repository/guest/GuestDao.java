package repository.guest;

import model.person.Guest;
import repository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GuestDao {
    private final Connection connection;

    public GuestDao() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Guest insert(Guest guest) {
        String sql = "INSERT INTO guests (name, email, loyalty_points) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getEmail());
            stmt.setInt(3, guest.getLoyaltyPoints());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                guest = new Guest(keys.getInt(1), guest.getName(), guest.getEmail(), guest.getLoyaltyPoints());
            }
            return guest;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea clientului.", e);
        }
    }

    public Guest findById(int id) {
        String sql = "SELECT id, name, email, loyalty_points FROM guests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea clientului.", e);
        }
    }

    public List<Guest> findAll() {
        String sql = "SELECT id, name, email, loyalty_points FROM guests ORDER BY name";
        List<Guest> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea clientilor.", e);
        }
    }

    public void update(Guest guest) {
        String sql = "UPDATE guests SET name = ?, email = ?, loyalty_points = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, guest.getName());
            stmt.setString(2, guest.getEmail());
            stmt.setInt(3, guest.getLoyaltyPoints());
            stmt.setInt(4, guest.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea clientului.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM guests WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea clientului.", e);
        }
    }

    private Guest mapRow(ResultSet rs) throws SQLException {
        return new Guest(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getInt("loyalty_points")
        );
    }
}
