// Extension: a square is a rectangle with equal sides
public class Square extends Rectangle {
    public Square(double side) {
        super(side, side);
    }

    public double getDiagonal() {
        return width * Math.sqrt(2);
    }

    @Override
    public String getName() {
        return "Square";
    }

    @Override
    public void display() {
        super.display();
        System.out.printf("  diagonal=%.2f%n", getDiagonal());
    }
}
