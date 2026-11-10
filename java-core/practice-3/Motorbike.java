public class Motorbike extends Vehicle {
    private int capacity;

    public Motorbike(String number, Manufacturer manufacturer, int year, String color, Owner owner,
                     int capacity) {
        super(number, manufacturer, year, color, owner);
        setCapacity(capacity);
    }

    // Engine capacity in cc
    public int getCapacity() {
        return capacity;
    }

    public final void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be > 0");
        }
        this.capacity = capacity;
    }

    @Override
    public String getType() {
        return "Motorbike";
    }

    @Override
    protected String getDetails() {
        return String.format("capacity=%dcc", capacity);
    }
}
