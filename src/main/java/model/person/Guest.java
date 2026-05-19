package model.person;

public class Guest extends Person {
    private int loyaltyPoints;

    public Guest(int id, String name, String email, int loyaltyPoints) {
        super(id, name, email);
        this.loyaltyPoints = loyaltyPoints;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    @Override
    public String toString() {
        return "Guest{id=" + id + ", name='" + getName() + "', email='" + getEmail()
                + "', loyaltyPoints=" + loyaltyPoints + "}";
    }
}
