package repository.payment;

import model.booking.Booking;
import model.payment.Payment;
import repository.DatabaseConnection;
import repository.booking.BookingDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    private final Connection connection;
    private final BookingDao bookingDao;

    public PaymentDao(BookingDao bookingDao) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.bookingDao = bookingDao;
    }

    public Payment insert(Payment payment) {
        String sql = "INSERT INTO payments (booking_id, amount, method, paid) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, payment.getBooking().getId());
            stmt.setDouble(2, payment.getAmount());
            stmt.setString(3, payment.getMethod());
            stmt.setInt(4, payment.isPaid() ? 1 : 0);
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return new Payment(keys.getInt(1), payment.getBooking(), payment.getAmount(), payment.getMethod(), payment.isPaid());
            }
            return payment;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea platii.", e);
        }
    }

    public Payment findById(int id) {
        String sql = "SELECT id, booking_id, amount, method, paid FROM payments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea platii.", e);
        }
    }

    public List<Payment> findAll() {
        String sql = "SELECT id, booking_id, amount, method, paid FROM payments";
        List<Payment> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea platilor.", e);
        }
    }

    public List<Payment> findAllByBookingId(int bookingId) {
        String sql = "SELECT id, booking_id, amount, method, paid FROM payments WHERE booking_id = ?";
        List<Payment> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bookingId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea platilor rezervarii.", e);
        }
    }

    public void update(Payment payment) {
        String sql = "UPDATE payments SET amount = ?, method = ?, paid = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDouble(1, payment.getAmount());
            stmt.setString(2, payment.getMethod());
            stmt.setInt(3, payment.isPaid() ? 1 : 0);
            stmt.setInt(4, payment.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea platii.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM payments WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea platii.", e);
        }
    }

    private Payment mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Booking booking = bookingDao.findById(rs.getInt("booking_id"));
        double amount = rs.getDouble("amount");
        String method = rs.getString("method");
        boolean paid = rs.getInt("paid") == 1;
        return new Payment(id, booking, amount, method, paid);
    }
}
