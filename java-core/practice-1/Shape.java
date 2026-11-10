public class Shape {
    protected double width;
    protected double height;

    public Shape(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public String getName() {
        return "Shape";
    }

    public void display() {
        System.out.printf("%s: width=%.2f, height=%.2f%n", getName(), width, height);
    }
}
