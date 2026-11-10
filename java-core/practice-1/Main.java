public class Main {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Shape(3, 4),
                new Rectangle(5, 10),
                new Circle(7),
                new Square(6),
                new Triangle(8, 5),
        };

        for (Shape shape : shapes) {
            shape.display();
        }
    }
}
