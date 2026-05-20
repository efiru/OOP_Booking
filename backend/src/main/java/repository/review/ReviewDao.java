package repository.review;

import model.hotel.Hotel;
import model.person.Guest;
import model.review.Review;
import repository.DatabaseConnection;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDao {
    private final Connection connection;
    private final GuestDao guestDao;
    private final HotelDao hotelDao;

    public ReviewDao(GuestDao guestDao, HotelDao hotelDao) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.guestDao = guestDao;
        this.hotelDao = hotelDao;
    }

    public Review insert(Review review) {
        String sql = "INSERT INTO reviews (guest_id, hotel_id, stars, comment) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, review.getGuest().getId());
            stmt.setInt(2, review.getHotel().getId());
            stmt.setInt(3, review.getStars());
            stmt.setString(4, review.getComment());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return new Review(keys.getInt(1), review.getGuest(), review.getHotel(), review.getStars(), review.getComment());
            }
            return review;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea recenziei.", e);
        }
    }

    public Review findById(int id) {
        String sql = "SELECT id, guest_id, hotel_id, stars, comment FROM reviews WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea recenziei.", e);
        }
    }

    public List<Review> findAll() {
        String sql = "SELECT id, guest_id, hotel_id, stars, comment FROM reviews";
        List<Review> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea recenziilor.", e);
        }
    }

    public List<Review> findAllByHotelId(int hotelId) {
        String sql = "SELECT id, guest_id, hotel_id, stars, comment FROM reviews WHERE hotel_id = ?";
        List<Review> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, hotelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea recenziilor hotelului.", e);
        }
    }

    public void update(Review review) {
        String sql = "UPDATE reviews SET stars = ?, comment = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, review.getStars());
            stmt.setString(2, review.getComment());
            stmt.setInt(3, review.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea recenziei.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM reviews WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea recenziei.", e);
        }
    }

    private Review mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Guest guest = guestDao.findById(rs.getInt("guest_id"));
        Hotel hotel = hotelDao.findById(rs.getInt("hotel_id"));
        int stars = rs.getInt("stars");
        String comment = rs.getString("comment");
        return new Review(id, guest, hotel, stars, comment);
    }
}
