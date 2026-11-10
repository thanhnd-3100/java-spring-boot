import java.time.Year;
import java.util.regex.Pattern;

public abstract class Vehicle {
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[A-Z0-9]{5}");

    private final String number;
    private Manufacturer manufacturer;
    private int year;
    private String color;
    private Owner owner;

    protected Vehicle(String number, Manufacturer manufacturer, int year, String color, Owner owner) {
        if (!isValidNumber(number)) {
            throw new IllegalArgumentException("Vehicle number must be exactly 5 letters or digits");
        }
        this.number = normalizeNumber(number);
        setManufacturer(manufacturer);
        setYear(year);
        setColor(color);
        setOwner(owner);
    }

    public static String normalizeNumber(String number) {
        return number == null ? "" : number.trim().toUpperCase();
    }

    public static boolean isValidNumber(String number) {
        return NUMBER_PATTERN.matcher(normalizeNumber(number)).matches();
    }

    public String getNumber() {
        return number;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public final void setManufacturer(Manufacturer manufacturer) {
        if (manufacturer == null) {
            throw new IllegalArgumentException("Manufacturer must not be empty");
        }
        this.manufacturer = manufacturer;
    }

    public int getYear() {
        return year;
    }

    // 2000 < year <= current year
    public final void setYear(int year) {
        int currentYear = Year.now().getValue();
        if (year <= 2000 || year > currentYear) {
            throw new IllegalArgumentException("Year must be greater than 2000 and at most " + currentYear);
        }
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public final void setColor(String color) {
        if (color == null || color.isBlank()) {
            throw new IllegalArgumentException("Color must not be empty");
        }
        this.color = color.trim();
    }

    public Owner getOwner() {
        return owner;
    }

    public final void setOwner(Owner owner) {
        if (owner == null) {
            throw new IllegalArgumentException("Owner must not be empty");
        }
        this.owner = owner;
    }

    public abstract String getType();

    protected abstract String getDetails();

    @Override
    public String toString() {
        return String.format("[%s] %s | %s, %d, %s | %s | owner: %s",
                getType(), number, manufacturer, year, color, getDetails(), owner);
    }
}
