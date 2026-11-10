public class Rectangle extends Shape {
    public Rectangle(double width, double length) {
        super(width, length);
    }

    // w * l
    public double getArea() {
        return width * height;
    }

    // 2 * (l + w)
    public double getPerimeter() {
        return 2 * (height + width);
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("  area=%.2f, perimeter=%.2f%n", getArea(), getPerimeter());
    }
}
