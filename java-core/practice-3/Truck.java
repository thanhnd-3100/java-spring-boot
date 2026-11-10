public class Truck extends Vehicle {
    private double tonnage;

    public Truck(String number, Manufacturer manufacturer, int year, String color, Owner owner,
                 double tonnage) {
        super(number, manufacturer, year, color, owner);
        setTonnage(tonnage);
    }

    public double getTonnage() {
        return tonnage;
    }

    public final void setTonnage(double tonnage) {
        if (tonnage <= 0) {
            throw new IllegalArgumentException("Tonnage must be > 0");
        }
        this.tonnage = tonnage;
    }

    @Override
    public String getType() {
        return "Truck";
    }

    @Override
    protected String getDetails() {
        return String.format("tonnage=%.1f tons", tonnage);
    }
}
