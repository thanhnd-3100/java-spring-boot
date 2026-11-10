public enum Manufacturer {
    HONDA, YAMAHA, TOYOTA, SUZUKI;

    // Accepts any letter case, e.g. "honda" or "Honda"
    public static Manufacturer parse(String text) {
        for (Manufacturer m : values()) {
            if (m.name().equalsIgnoreCase(text.trim())) {
                return m;
            }
        }
        throw new IllegalArgumentException("Manufacturer must be one of: Honda, Yamaha, Toyota, Suzuki");
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
