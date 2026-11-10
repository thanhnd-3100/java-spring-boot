import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class VehicleManager {
    private final List<Vehicle> vehicles = new ArrayList<>();

    // Throws when the vehicle number is taken, or the owner's ID belongs to another person
    public void add(Vehicle vehicle) {
        if (findByNumber(vehicle.getNumber()) != null) {
            throw new IllegalArgumentException("Vehicle number " + vehicle.getNumber() + " already exists");
        }
        Owner existing = findOwner(vehicle.getOwner().getIdNumber());
        if (existing != null && existing != vehicle.getOwner()) {
            throw new IllegalArgumentException("ID number " + existing.getIdNumber()
                    + " already belongs to " + existing.getFullName());
        }
        vehicles.add(vehicle);
    }

    public Vehicle findByNumber(String number) {
        String normalized = Vehicle.normalizeNumber(number);
        for (Vehicle v : vehicles) {
            if (v.getNumber().equals(normalized)) {
                return v;
            }
        }
        return null;
    }

    public Owner findOwner(String idNumber) {
        for (Vehicle v : vehicles) {
            if (v.getOwner().getIdNumber().equals(idNumber.trim())) {
                return v.getOwner();
            }
        }
        return null;
    }

    public List<Vehicle> findByOwnerId(String idNumber) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle v : vehicles) {
            if (v.getOwner().getIdNumber().equals(idNumber.trim())) {
                result.add(v);
            }
        }
        return result;
    }

    // Returns how many vehicles were removed
    public int removeByManufacturer(Manufacturer manufacturer) {
        int before = vehicles.size();
        vehicles.removeIf(v -> v.getManufacturer() == manufacturer);
        return before - vehicles.size();
    }

    // Vehicle count per manufacturer, highest first (manufacturers with 0 vehicles included)
    public Map<Manufacturer, Integer> countByManufacturer() {
        Map<Manufacturer, Integer> counts = new EnumMap<>(Manufacturer.class);
        for (Manufacturer m : Manufacturer.values()) {
            counts.put(m, 0);
        }
        for (Vehicle v : vehicles) {
            counts.merge(v.getManufacturer(), 1, Integer::sum);
        }
        Map<Manufacturer, Integer> sorted = new LinkedHashMap<>();
        counts.entrySet().stream()
                .sorted(Map.Entry.<Manufacturer, Integer>comparingByValue().reversed())
                .forEach(e -> sorted.put(e.getKey(), e.getValue()));
        return sorted;
    }

    // All manufacturers sharing the highest count; empty when there are no vehicles
    public List<Manufacturer> getTopManufacturers() {
        Map<Manufacturer, Integer> counts = countByManufacturer();
        int max = counts.values().stream().max(Integer::compare).orElse(0);
        List<Manufacturer> top = new ArrayList<>();
        if (max == 0) {
            return top;
        }
        counts.forEach((m, count) -> {
            if (count == max) {
                top.add(m);
            }
        });
        return top;
    }

    // Vehicles grouped by manufacturer, manufacturers with more vehicles first, then by vehicle number
    public List<Vehicle> sortByManufacturerCount() {
        Map<Manufacturer, Integer> counts = countByManufacturer();
        List<Vehicle> sorted = new ArrayList<>(vehicles);
        sorted.sort(Comparator
                .comparing((Vehicle v) -> counts.get(v.getManufacturer()), Comparator.reverseOrder())
                .thenComparing(Vehicle::getManufacturer)
                .thenComparing(Vehicle::getNumber));
        return sorted;
    }

    public Map<String, Integer> countByType() {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String type : new String[] {"Car", "Motorbike", "Truck"}) {
            counts.put(type, 0);
        }
        for (Vehicle v : vehicles) {
            counts.merge(v.getType(), 1, Integer::sum);
        }
        return counts;
    }

    public List<Vehicle> getAll() {
        return List.copyOf(vehicles);
    }
}
