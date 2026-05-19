package model.room;

public abstract class Room {
    private int id;
    private String number;
    private int capacity;
    protected double pricePerNight;
    private boolean available;

    public Room(int id, String number, int capacity, double pricePerNight, boolean available) {
        this.id = id;
        this.number = number;
        this.capacity = capacity;
        this.pricePerNight = pricePerNight;
        this.available = available;
    }

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract String getRoomType();

    @Override
    public String toString() {
        return getRoomType() + "{id=" + id + ", number='" + number + "', capacity=" + capacity
                + ", pricePerNight=" + pricePerNight + ", available=" + available + "}";
    }
}
