package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class Pentagram extends Shape {

    public Pentagram() {}

    public Pentagram(Color fillColor) {
        super(fillColor);
    }

    public Pentagram(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    public void draw(Graphics g) {

        int[] x = new int[10];
        int[] y = new int[10];

        int angle = 0;

        for (int i = 0; i < 10; i++) {

            int tempX;
            int tempY;

            if (i % 2 == 0) {

                tempX = point.x + (int) (size * Math.cos(Math.toRadians(initAngle + angle)));
                tempY = point.y - (int) (size * Math.sin(Math.toRadians(initAngle + angle)));

            } else {

                tempX = point.x + (int) ((size / 2) * Math.cos(Math.toRadians(initAngle + angle)));
                tempY = point.y - (int) ((size / 2) * Math.sin(Math.toRadians(initAngle + angle)));

            }

            angle += 36;

            x[i] = tempX;
            y[i] = tempY;

        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
        g2.fill(new Polygon(x, y, 10));

        if (stroke > 0) {
            g2.setColor(strokeColor);
            g2.setStroke(new BasicStroke(stroke));
            g2.draw(new Polygon(x, y, 10));
            g2.setStroke(new BasicStroke(0));
        }

    }

    @Override
    public String toString() {
        return "Pentagram\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + size + "\n" + initAngle + "\n";
    }
}
