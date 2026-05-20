package repository.booking;

import model.booking.Booking;
import model.booking.BookingStatus;
import model.hotel.Hotel;
import model.person.Guest;
import model.room.Room;
import repository.DatabaseConnection;
import repository.guest.GuestDao;
import repository.hotel.HotelDao;
import repository.room.RoomDao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDao {
    private final Connection connection;
    private final GuestDao guestDao;
    private final HotelDao hotelDao;
    private final RoomDao roomDao;

    public BookingDao(GuestDao guestDao, HotelDao hotelDao, RoomDao roomDao) {
        this.connection = DatabaseConnection.getInstance().getConnection();
        this.guestDao = guestDao;
        this.hotelDao = hotelDao;
        this.roomDao = roomDao;
    }

    public Booking insert(Booking booking) {
        String sql = "INSERT INTO bookings (guest_id, hotel_id, room_id, check_in_date, nights, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, booking.getGuest().getId());
            stmt.setInt(2, booking.getHotel().getId());
            stmt.setInt(3, booking.getRoom().getId());
            stmt.setString(4, booking.getCheckInDate().toString());
            stmt.setInt(5, booking.getNights());
            stmt.setString(6, booking.getStatus().name());
            stmt.executeUpdate();
            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                return new Booking(keys.getInt(1), booking.getGuest(), booking.getHotel(),
                        booking.getRoom(), booking.getCheckInDate(), booking.getNights(), booking.getStatus());
            }
            return booking;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la inserarea rezervarii.", e);
        }
    }

    public Booking findById(int id) {
        String sql = "SELECT id, guest_id, hotel_id, room_id, check_in_date, nights, status FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la cautarea rezervarii.", e);
        }
    }

    public List<Booking> findAll() {
        String sql = "SELECT id, guest_id, hotel_id, room_id, check_in_date, nights, status FROM bookings";
        List<Booking> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea rezervarilor.", e);
        }
    }

    public List<Booking> findAllByGuestId(int guestId) {
        String sql = "SELECT id, guest_id, hotel_id, room_id, check_in_date, nights, status FROM bookings WHERE guest_id = ?";
        List<Booking> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, guestId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(mapRow(rs));
            }
            return result;
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la listarea rezervarilor clientului.", e);
        }
    }

    public void update(Booking booking) {
        String sql = "UPDATE bookings SET check_in_date = ?, nights = ?, status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, booking.getCheckInDate().toString());
            stmt.setInt(2, booking.getNights());
            stmt.setString(3, booking.getStatus().name());
            stmt.setInt(4, booking.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la actualizarea rezervarii.", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la stergerea rezervarii.", e);
        }
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        Guest guest = guestDao.findById(rs.getInt("guest_id"));
        Hotel hotel = hotelDao.findById(rs.getInt("hotel_id"));
        Room room = roomDao.findById(rs.getInt("room_id"));
        LocalDate checkInDate = LocalDate.parse(rs.getString("check_in_date"));
        int nights = rs.getInt("nights");
        BookingStatus status = BookingStatus.valueOf(rs.getString("status"));
        return new Booking(id, guest, hotel, room, checkInDate, nights, status);
    }
}
