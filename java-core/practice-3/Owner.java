import java.util.regex.Pattern;

public class Owner {
    private static final Pattern ID_PATTERN = Pattern.compile("\\d{12}");
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private final String idNumber;
    private String fullName;
    private String email;

    public Owner(String idNumber, String fullName, String email) {
        if (idNumber == null || !ID_PATTERN.matcher(idNumber.trim()).matches()) {
            throw new IllegalArgumentException("ID number must be exactly 12 digits");
        }
        this.idNumber = idNumber.trim();
        setFullName(fullName);
        setEmail(email);
    }

    public static boolean isValidId(String idNumber) {
        return idNumber != null && ID_PATTERN.matcher(idNumber.trim()).matches();
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public final void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name must not be empty");
        }
        this.fullName = fullName.trim();
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new IllegalArgumentException("Email is not in a valid format");
        }
        this.email = email.trim();
    }

    @Override
    public String toString() {
        return String.format("%s (ID %s, %s)", fullName, idNumber, email);
    }
}
