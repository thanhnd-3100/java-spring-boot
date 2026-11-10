import java.time.LocalDate;

public class Food extends Product {
    private LocalDate manufactureDate;
    private LocalDate expiryDate;
    private String supplier;

    public Food(String code, String name, int quantity, double unitPrice,
                LocalDate manufactureDate, LocalDate expiryDate, String supplier) {
        super(code, name, quantity, unitPrice);
        setDates(manufactureDate, expiryDate);
        this.supplier = supplier;
    }

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    // Both dates are set together so the expiry >= manufacture rule is always checked
    public final void setDates(LocalDate manufactureDate, LocalDate expiryDate) {
        if (manufactureDate == null || expiryDate == null) {
            throw new IllegalArgumentException("Dates must not be empty");
        }
        if (expiryDate.isBefore(manufactureDate)) {
            throw new IllegalArgumentException("Expiry date must be on or after the manufacture date");
        }
        this.manufactureDate = manufactureDate;
        this.expiryDate = expiryDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public final void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String getType() {
        return "Food";
    }

    @Override
    public double getVatRate() {
        return 0.05;
    }

    @Override
    public String evaluateConsumption() {
        if (getQuantity() > 0 && isExpired()) {
            return "Hard to sell";
        }
        return null;
    }

    @Override
    protected String getDetails() {
        return String.format("mfg=%s, exp=%s, supplier=%s", manufactureDate, expiryDate, supplier);
    }
}
