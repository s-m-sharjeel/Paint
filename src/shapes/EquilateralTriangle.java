package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class EquilateralTriangle extends Triangle {

    public EquilateralTriangle(Color fillColor, Color strokeColor, int initAngle) {
        super(fillColor, strokeColor);
        this.initAngle = initAngle;
    }

    public EquilateralTriangle(Color fillColor) {
        super(fillColor);
    }

    public EquilateralTriangle(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    public EquilateralTriangle() {

    }

    @Override
    public void draw(Graphics g) {

        int[] x = new int[3];
        int[] y = new int[3];

        int angle = 0;

        for (int i = 0; i < 3; i++) {

            int tempX;
            int tempY;

            tempX = point.x + (int) (size * Math.cos(Math.toRadians(initAngle + angle)));
            tempY = point.y - (int) (size * Math.sin(Math.toRadians(initAngle + angle)));

            x[i] = tempX;
            y[i] = tempY;

            angle += 120;

        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(fillColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new Polygon(x, y, 3));

        if (stroke > 0) {
            g2.setColor(strokeColor);
            g2.setStroke(new BasicStroke(stroke));
            g2.draw(new Polygon(x, y, 3));
            g2.setStroke(new BasicStroke(0));
        }
    }

    @Override
    public String toString() {
        return "Equilateral Triangle\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + size + "\n" + initAngle + "\n";
    }
}
