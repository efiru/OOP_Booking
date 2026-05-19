package model.room;

public class SuiteRoom extends Room {
    private boolean livingArea;

    public SuiteRoom(int id, String number, int capacity, double pricePerNight, boolean available, boolean livingArea) {
        super(id, number, capacity, pricePerNight, available);
        this.livingArea = livingArea;
    }

    public boolean hasLivingArea() {
        return livingArea;
    }

    public void setLivingArea(boolean livingArea) {
        this.livingArea = livingArea;
    }

    @Override
    public String getRoomType() {
        return "SuiteRoom";
    }

    @Override
    public String toString() {
        return super.toString().replace("}", ", livingArea=" + livingArea + "}");
    }
}
