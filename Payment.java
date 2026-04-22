public class Payment {
    private int id;
    private Booking booking;
    private double amount;
    private String method;
    private boolean paid;

    public Payment(int id, Booking booking, double amount, String method, boolean paid) {
        this.id = id;
        this.booking = booking;
        this.amount = amount;
        this.method = method;
        this.paid = paid;
    }

    public int getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Payment{id=" + id + ", bookingId=" + booking.getId() + ", amount=" + amount
                + ", method='" + method + "', paid=" + paid + "}";
    }
}
