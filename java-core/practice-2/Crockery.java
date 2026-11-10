import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Crockery extends Product {
    private String manufacturer;
    private LocalDate arrivalDate;

    public Crockery(String code, String name, int quantity, double unitPrice,
                    String manufacturer, LocalDate arrivalDate) {
        super(code, name, quantity, unitPrice);
        this.manufacturer = manufacturer;
        setArrivalDate(arrivalDate);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public final void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public final void setArrivalDate(LocalDate arrivalDate) {
        if (arrivalDate == null) {
            throw new IllegalArgumentException("Arrival date must not be empty");
        }
        this.arrivalDate = arrivalDate;
    }

    public long getStorageDays() {
        return ChronoUnit.DAYS.between(arrivalDate, LocalDate.now());
    }

    @Override
    public String getType() {
        return "Crockery";
    }

    @Override
    public double getVatRate() {
        return 0.10;
    }

    @Override
    public String evaluateConsumption() {
        if (getQuantity() > 50 && getStorageDays() > 10) {
            return "Slow sale";
        }
        return null;
    }

    @Override
    protected String getDetails() {
        return String.format("manufacturer=%s, arrival=%s (%d days in stock)",
                manufacturer, arrivalDate, getStorageDays());
    }
}
