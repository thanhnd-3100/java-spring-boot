import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ProductManager manager = new ProductManager(10);
        addSampleData(manager);

        while (true) {
            System.out.println();
            System.out.println("===== SUPERMARKET INVENTORY =====");
            System.out.println("1. Add goods");
            System.out.println("2. Show all goods");
            System.out.println("3. Show quantity and VAT by type");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ", 0, 3);
            switch (choice) {
                case 1 -> addProduct(manager);
                case 2 -> manager.displayAll();
                case 3 -> manager.displaySummary();
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }

    private static void addSampleData(ProductManager manager) {
        LocalDate today = LocalDate.now();
        manager.add(new Food("F01", "Milk", 20, 30000, today.minusDays(40), today.minusDays(5), "Vinamilk"));
        manager.add(new Food("F02", "Bread", 15, 15000, today.minusDays(1), today.plusDays(3), "ABC Bakery"));
        manager.add(new Electronics("E01", "Fan", 2, 500000, 12, 0.05));
        manager.add(new Electronics("E02", "Fridge", 10, 8000000, 24, 0.15));
        manager.add(new Crockery("C01", "Bowl", 60, 20000, "Minh Long", today.minusDays(15)));
        manager.add(new Crockery("C02", "Plate", 30, 25000, "Minh Long", today.minusDays(20)));
    }

    private static void addProduct(ProductManager manager) {
        System.out.println("Type of goods: 1. Food  2. Electronics  3. Crockery");
        int type = readInt("Choose type: ", 1, 3);

        String code = readText("Product code: ");
        if (manager.findByCode(code) != null) {
            System.out.println("Failed: product code " + code + " already exists.");
            return;
        }
        String name = readText("Name: ");
        int quantity = readInt("Inventory quantity: ", 0, Integer.MAX_VALUE);
        double price = readDouble("Unit price: ");

        try {
            Product product = switch (type) {
                case 1 -> {
                    LocalDate mfg = readDate("Manufacture date (dd/MM/yyyy): ");
                    LocalDate exp = readDate("Expiry date (dd/MM/yyyy): ");
                    String supplier = readText("Supplier: ");
                    yield new Food(code, name, quantity, price, mfg, exp, supplier);
                }
                case 2 -> {
                    int warranty = readInt("Warranty (months): ", 0, Integer.MAX_VALUE);
                    double power = readDouble("Power (KW): ");
                    yield new Electronics(code, name, quantity, price, warranty, power);
                }
                default -> {
                    String manufacturer = readText("Manufacturer: ");
                    LocalDate arrival = readDate("Arrival date (dd/MM/yyyy): ");
                    yield new Crockery(code, name, quantity, price, manufacturer, arrival);
                }
            };
            if (manager.add(product)) {
                System.out.println("Added successfully: " + product);
            } else {
                System.out.println("Failed: product code " + code + " already exists.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Value must not be empty.");
        }
    }

    private static int readInt(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(readText(prompt));
                if (value >= min && value <= max) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.printf("Please enter an integer from %d to %d.%n", min, max);
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(readText(prompt));
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a number >= 0.");
        }
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(readText(prompt), DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date, use dd/MM/yyyy.");
            }
        }
    }
}
