public class StandardRoom extends Room {
    private boolean balcony;

    public StandardRoom(int id, String number, int capacity, double pricePerNight, boolean available, boolean balcony) {
        super(id, number, capacity, pricePerNight, available);
        this.balcony = balcony;
    }

    public boolean hasBalcony() {
        return balcony;
    }

    public void setBalcony(boolean balcony) {
        this.balcony = balcony;
    }

    @Override
    public String getRoomType() {
        return "StandardRoom";
    }

    @Override
    public String toString() {
        return super.toString().replace("}", ", balcony=" + balcony + "}");
    }
}
