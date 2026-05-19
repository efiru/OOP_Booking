package model.booking;

import model.hotel.Hotel;
import model.person.Guest;
import model.room.Room;

import java.time.LocalDate;

public class Booking {
    private int id;
    private Guest guest;
    private Hotel hotel;
    private Room room;
    private LocalDate checkInDate;
    private int nights;
    private BookingStatus status;

    public Booking(int id, Guest guest, Hotel hotel, Room room, LocalDate checkInDate, int nights, BookingStatus status) {
        this.id = id;
        this.guest = guest;
        this.hotel = hotel;
        this.room = room;
        this.checkInDate = checkInDate;
        this.nights = nights;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public Guest getGuest() {
        return guest;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public int getNights() {
        return nights;
    }

    public void setNights(int nights) {
        this.nights = nights;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double calculateTotalPrice() {
        return room.getPricePerNight() * nights;
    }

    public double getTotalPrice() {
        return calculateTotalPrice();
    }

    @Override
    public String toString() {
        return "Booking{id=" + id + ", guest=" + guest.getName() + ", hotel=" + hotel.getName()
                + ", room=" + room.getNumber() + ", checkInDate=" + checkInDate
                + ", nights=" + nights + ", status=" + status
                + ", total=" + calculateTotalPrice() + "}";
    }
}
