public class Circle extends Shape {
    // A circle fits in a square box: width = height = diameter
    public Circle(double radius) {
        super(radius * 2, radius * 2);
    }

    public double getRadius() {
        return width / 2;
    }

    // π * r^2
    public double getArea() {
        return Math.PI * getRadius() * getRadius();
    }

    // d * π
    public double getCircumference() {
        return width * Math.PI;
    }

    @Override
    public String getName() {
        return "Circle";
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("  radius=%.2f, area=%.2f, circumference=%.2f%n",
                getRadius(), getArea(), getCircumference());
    }
}
