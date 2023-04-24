package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class RightTriangle extends Triangle {

    public RightTriangle() {}

    public RightTriangle(Color fillColor) {
        super(fillColor);
    }

    public RightTriangle(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    @Override
    public void draw(Graphics g) {
        int[] x = new int[3];
        int[] y = new int[3];
        x[0] = point.x;
        y[0] = point.y + height;
        x[1] = point.x;
        y[1] = point.y;
        x[2] = point.x + width;
        y[2] = point.y + height;

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
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
        return "Right Triangle\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + width + "\n" + height + "\n";
    }
}
