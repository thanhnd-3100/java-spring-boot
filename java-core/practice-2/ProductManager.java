import java.util.Arrays;

// DSHH (danh sách hàng hóa): stores the products in an array
public class ProductManager {
    private Product[] products;
    private int size;

    public ProductManager(int capacity) {
        products = new Product[capacity];
    }

    // Returns false when the product code already exists
    public boolean add(Product product) {
        if (findByCode(product.getCode()) != null) {
            return false;
        }
        if (size == products.length) {
            products = Arrays.copyOf(products, Math.max(1, products.length * 2));
        }
        products[size++] = product;
        return true;
    }

    public Product findByCode(String code) {
        for (int i = 0; i < size; i++) {
            if (products[i].getCode().equalsIgnoreCase(code.trim())) {
                return products[i];
            }
        }
        return null;
    }

    public int getSize() {
        return size;
    }

    public int getTotalQuantity(String type) {
        int total = 0;
        for (int i = 0; i < size; i++) {
            if (products[i].getType().equals(type)) {
                total += products[i].getQuantity();
            }
        }
        return total;
    }

    public double getTotalVat(String type) {
        double total = 0;
        for (int i = 0; i < size; i++) {
            if (products[i].getType().equals(type)) {
                total += products[i].getVatAmount();
            }
        }
        return total;
    }

    public void displayAll() {
        if (size == 0) {
            System.out.println("The list is empty.");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println((i + 1) + ". " + products[i]);
        }
    }

    public void displaySummary() {
        System.out.printf("%-12s %10s %14s%n", "Type", "Quantity", "VAT");
        for (String type : new String[] {"Food", "Electronics", "Crockery"}) {
            System.out.printf("%-12s %10d %14.2f%n", type, getTotalQuantity(type), getTotalVat(type));
        }
    }
}
