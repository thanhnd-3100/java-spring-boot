import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final VehicleManager manager = new VehicleManager();

    public static void main(String[] args) {
        addSampleData();

        while (true) {
            System.out.println();
            System.out.println("===== VEHICLE MANAGEMENT =====");
            System.out.println("1. Add vehicle");
            System.out.println("2. Search vehicle by number");
            System.out.println("3. Find vehicles by owner ID number");
            System.out.println("4. Delete all vehicles of a manufacturer");
            System.out.println("5. Manufacturer with the most vehicles");
            System.out.println("6. Sort vehicles by manufacturer vehicle count (descending)");
            System.out.println("7. Statistics by vehicle type");
            System.out.println("8. Show all vehicles");
            System.out.println("0. Exit");
            int choice = readInt("Choose: ", 0, 8);
            switch (choice) {
                case 1 -> addVehicle();
                case 2 -> searchByNumber();
                case 3 -> findByOwner();
                case 4 -> deleteByManufacturer();
                case 5 -> showTopManufacturers();
                case 6 -> showSortedByManufacturerCount();
                case 7 -> showTypeStatistics();
                case 8 -> printList(manager.getAll());
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
            }
        }
    }

    private static void addSampleData() {
        Owner an = new Owner("001090000001", "Nguyen Van An", "an.nguyen@gmail.com");
        Owner binh = new Owner("001090000002", "Tran Thi Binh", "binh.tran@yahoo.com");
        Owner cuong = new Owner("001090000003", "Le Van Cuong", "cuong.le@company.vn");
        manager.add(new Car("30A01", Manufacturer.TOYOTA, 2020, "White", an, 5, "Gasoline"));
        manager.add(new Motorbike("29B12", Manufacturer.HONDA, 2018, "Black", an, 125));
        manager.add(new Motorbike("29C34", Manufacturer.HONDA, 2022, "Red", binh, 110));
        manager.add(new Motorbike("30D56", Manufacturer.YAMAHA, 2019, "Blue", binh, 150));
        manager.add(new Truck("51C78", Manufacturer.SUZUKI, 2015, "Silver", cuong, 2.5));
        manager.add(new Car("51F90", Manufacturer.HONDA, 2021, "Gray", cuong, 7, "Hybrid"));
    }

    private static void addVehicle() {
        System.out.println("Vehicle type: 1. Car  2. Motorbike  3. Truck");
        int type = readInt("Choose type: ", 1, 3);
        try {
            String number = readText("Vehicle number (5 characters): ");
            if (!Vehicle.isValidNumber(number)) {
                System.out.println("Failed: vehicle number must be exactly 5 letters or digits.");
                return;
            }
            if (manager.findByNumber(number) != null) {
                System.out.println("Failed: vehicle number " + Vehicle.normalizeNumber(number) + " already exists.");
                return;
            }
            Manufacturer manufacturer = Manufacturer.parse(readText("Manufacturer (Honda/Yamaha/Toyota/Suzuki): "));
            int year = readInt("Year of manufacture: ", 2001, Year.now().getValue());
            String color = readText("Color: ");
            Owner owner = readOwner();

            Vehicle vehicle = switch (type) {
                case 1 -> new Car(number, manufacturer, year, color, owner,
                        readInt("Number of seats: ", 1, Integer.MAX_VALUE), readText("Engine type: "));
                case 2 -> new Motorbike(number, manufacturer, year, color, owner,
                        readInt("Capacity (cc): ", 1, Integer.MAX_VALUE));
                default -> new Truck(number, manufacturer, year, color, owner,
                        readPositiveDouble("Tonnage: "));
            };
            manager.add(vehicle);
            System.out.println("Added successfully: " + vehicle);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    // Reuses the registered owner when the ID number already exists
    private static Owner readOwner() {
        String id = readText("Owner ID number (12 digits): ");
        if (!Owner.isValidId(id)) {
            throw new IllegalArgumentException("ID number must be exactly 12 digits");
        }
        Owner existing = manager.findOwner(id);
        if (existing != null) {
            System.out.println("Existing owner found: " + existing);
            return existing;
        }
        return new Owner(id, readText("Owner full name: "), readText("Owner email: "));
    }

    private static void searchByNumber() {
        Vehicle vehicle = manager.findByNumber(readText("Vehicle number: "));
        System.out.println(vehicle == null ? "No vehicle found." : vehicle);
    }

    private static void findByOwner() {
        List<Vehicle> result = manager.findByOwnerId(readText("Owner ID number: "));
        if (result.isEmpty()) {
            System.out.println("No vehicle found for this ID number.");
        } else {
            System.out.println("Owner: " + result.get(0).getOwner());
            printList(result);
        }
    }

    private static void deleteByManufacturer() {
        try {
            Manufacturer manufacturer = Manufacturer.parse(readText("Manufacturer to delete: "));
            int removed = manager.removeByManufacturer(manufacturer);
            System.out.printf("Deleted %d vehicle(s) of %s.%n", removed, manufacturer);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed: " + e.getMessage());
        }
    }

    private static void showTopManufacturers() {
        List<Manufacturer> top = manager.getTopManufacturers();
        if (top.isEmpty()) {
            System.out.println("No vehicles under management.");
            return;
        }
        int count = manager.countByManufacturer().get(top.get(0));
        System.out.printf("Most vehicles: %s (%d vehicles)%n", top, count);
    }

    private static void showSortedByManufacturerCount() {
        Map<Manufacturer, Integer> counts = manager.countByManufacturer();
        counts.forEach((m, count) -> System.out.printf("%-8s %d vehicle(s)%n", m, count));
        System.out.println("---");
        printList(manager.sortByManufacturerCount());
    }

    private static void showTypeStatistics() {
        manager.countByType().forEach((type, count) -> System.out.printf("%-10s %d%n", type, count));
    }

    private static void printList(List<Vehicle> list) {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
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

    private static double readPositiveDouble(String prompt) {
        while (true) {
            try {
                double value = Double.parseDouble(readText(prompt));
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
            }
            System.out.println("Please enter a number > 0.");
        }
    }
}
