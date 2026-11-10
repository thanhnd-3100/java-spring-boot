public class Electronics extends Product {
    private int warrantyMonths;
    private double powerKw;

    public Electronics(String code, String name, int quantity, double unitPrice,
                       int warrantyMonths, double powerKw) {
        super(code, name, quantity, unitPrice);
        setWarrantyMonths(warrantyMonths);
        setPowerKw(powerKw);
    }

    public int getWarrantyMonths() {
        return warrantyMonths;
    }

    public final void setWarrantyMonths(int warrantyMonths) {
        if (warrantyMonths < 0) {
            throw new IllegalArgumentException("Warranty months must be >= 0");
        }
        this.warrantyMonths = warrantyMonths;
    }

    public double getPowerKw() {
        return powerKw;
    }

    public final void setPowerKw(double powerKw) {
        if (powerKw < 0) {
            throw new IllegalArgumentException("Power (KW) must be >= 0");
        }
        this.powerKw = powerKw;
    }

    @Override
    public String getType() {
        return "Electronics";
    }

    @Override
    public double getVatRate() {
        return 0.10;
    }

    @Override
    public String evaluateConsumption() {
        if (getQuantity() < 3) {
            return "Sold";
        }
        return null;
    }

    @Override
    protected String getDetails() {
        return String.format("warranty=%d months, power=%.2f KW", warrantyMonths, powerKw);
    }
}
