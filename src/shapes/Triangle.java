package shapes;

import java.awt.*;

public abstract class Triangle extends Shape {

    public Triangle() {}

    public Triangle(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    public Triangle(Color fillColor) {
        super(fillColor);
    }

    @Override
    public abstract void draw(Graphics g);

}
