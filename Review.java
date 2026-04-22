public class Review {
    private int id;
    private Guest guest;
    private Hotel hotel;
    private int stars;
    private String comment;

    public Review(int id, Guest guest, Hotel hotel, int stars, String comment) {
        this.id = id;
        this.guest = guest;
        this.hotel = hotel;
        this.stars = stars;
        this.comment = comment;
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

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Review{id=" + id + ", guest=" + guest.getName() + ", hotel=" + hotel.getName()
                + ", stars=" + stars + ", comment='" + comment + "'}";
    }
}
