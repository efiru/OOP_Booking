package repository.room;

import model.room.Room;
import model.room.StandardRoom;
import model.room.SuiteRoom;
import repository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomDao {
    private final Connection connection;

    public RoomDao() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Room insert(int hotelId, Room room) {
        String sql = "INSERT INTO rooms (hotel_id, number, capacity, price_per_night, available, room_type, has_balcony, has_living_area) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, hotelId);
            stmt.setString(2, room.getNumber());
            stmt.setInt(3, room.getCapacity());
            stmt.setDouble(4, room.getPricePerNight());
            stmt.setInt(5, room.isAvailable() ? 1 : 0);
            stmt.setString(6, room.getRoomType());

            if (room instanceof StandardRoom) {
                stmt.setInt(7, ((StandardRoom) room).hasBalcony() ? 1 : 0);
                stmt.setNull(8, Types.INTEGER);
            } else {
                stmt.setNull(7, Types.INTEGER);
                stmt.setInt(8, ((SuiteRoom) room).hasLivingArea() ? 1 : 0);
            }

            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return buildRoom(keys.getInt(1), room);
            }
            return room;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea camerei.", e);
        }
    }

    public Room findById(int id) {
        String sql = "SELECT id, number, capacity, price_per_night, available, room_type, has_balcony, has_living_area FROM rooms WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea camerei.", e);
        }
    }

    public List<Room> findAllByHotelId(int hotelId) {
        String sql = "SELECT id, number, capacity, price_per_night, available, room_type, has_balcony, has_living_area FROM rooms WHERE hotel_id = ?";
        List<Room> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea camerelor.", e);
        }
    }

    public void update(Room room) {
        String sql = "UPDATE rooms SET number = ?, capacity = ?, price_per_night = ?, available = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, room.getNumber());
            stmt.setInt(2, room.getCapacity());
            stmt.setDouble(3, room.getPricePerNight());
            stmt.setInt(4, room.isAvailable() ? 1 : 0);
            stmt.setInt(5, room.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea camerei.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM rooms WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea camerei.", e);
        }
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String number = rs.getString("number");
        int capacity = rs.getInt("capacity");
        double price = rs.getDouble("price_per_night");
        boolean available = rs.getInt("available") == 1;
        String roomType = rs.getString("room_type");

        if ("StandardRoom".equals(roomType)) {
            boolean balcony = rs.getInt("has_balcony") == 1;
            return new StandardRoom(id, number, capacity, price, available, balcony);
        } else {
            boolean livingArea = rs.getInt("has_living_area") == 1;
            return new SuiteRoom(id, number, capacity, price, available, livingArea);
        }
    }

    private Room buildRoom(int newId, Room original) {
        if (original instanceof StandardRoom) {
            return new StandardRoom(newId, original.getNumber(), original.getCapacity(),
                    original.getPricePerNight(), original.isAvailable(), ((StandardRoom) original).hasBalcony());
        } else {
            return new SuiteRoom(newId, original.getNumber(), original.getCapacity(),
                    original.getPricePerNight(), original.isAvailable(), ((SuiteRoom) original).hasLivingArea());
        }
    }
}
