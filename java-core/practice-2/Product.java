public abstract class Product {
    private final String code;
    private String name;
    private int quantity;
    private double unitPrice;

    protected Product(String code, String name, int quantity, double unitPrice) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Product code must not be empty");
        }
        this.code = code.trim();
        setName(name);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product name must not be empty");
        }
        this.name = name.trim();
    }

    public int getQuantity() {
        return quantity;
    }

    public final void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity must be >= 0");
        }
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public final void setUnitPrice(double unitPrice) {
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price must be >= 0");
        }
        this.unitPrice = unitPrice;
    }

    // VAT on the whole stock of this product: quantity * unit price * VAT rate
    public double getVatAmount() {
        return quantity * unitPrice * getVatRate();
    }

    public abstract String getType();

    public abstract double getVatRate();

    // Returns the consumption rating, or null when the product is not evaluated
    public abstract String evaluateConsumption();

    protected abstract String getDetails();

    @Override
    public String toString() {
        String rating = evaluateConsumption();
        return String.format("[%s] %s - %s | qty=%d, price=%.2f, VAT=%.2f | %s | %s",
                getType(), code, name, quantity, unitPrice, getVatAmount(),
                getDetails(), rating == null ? "not evaluated" : rating);
    }
}
