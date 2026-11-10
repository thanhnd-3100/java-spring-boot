public class Car extends Vehicle {
    private int seats;
    private String engineType;

    public Car(String number, Manufacturer manufacturer, int year, String color, Owner owner,
               int seats, String engineType) {
        super(number, manufacturer, year, color, owner);
        setSeats(seats);
        setEngineType(engineType);
    }

    public int getSeats() {
        return seats;
    }

    public final void setSeats(int seats) {
        if (seats <= 0) {
            throw new IllegalArgumentException("Number of seats must be > 0");
        }
        this.seats = seats;
    }

    public String getEngineType() {
        return engineType;
    }

    public final void setEngineType(String engineType) {
        if (engineType == null || engineType.isBlank()) {
            throw new IllegalArgumentException("Engine type must not be empty");
        }
        this.engineType = engineType.trim();
    }

    @Override
    public String getType() {
        return "Car";
    }

    @Override
    protected String getDetails() {
        return String.format("seats=%d, engine=%s", seats, engineType);
    }
}
