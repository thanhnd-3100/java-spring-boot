// width = base, height = height from the base
public class Triangle extends Shape {
    public Triangle(double base, double height) {
        super(base, height);
    }

    // (b * h) / 2
    public double getArea() {
        return width * height / 2;
    }

    @Override
    public String getName() {
        return "Triangle";
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("  area=%.2f%n", getArea());
    }
}
