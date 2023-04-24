package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class Hexagon extends Shape{

    public Hexagon() {}

    public Hexagon(Color fillColor) {
        super(fillColor);
    }

    public Hexagon(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    @Override
    public void draw(Graphics g) {
        int[] x = new int[6];
        int[] y = new int[6];
        x[0] = point.x + width/2;
        y[0] = point.y + height;
        x[1] = point.x;
        y[1] = point.y + (int)(height*0.66);
        x[2] = point.x;
        y[2] = point.y + (int)(height*0.33);
        x[3] = point.x + width/2;
        y[3] = point.y;
        x[4] = point.x + width;
        y[4] = point.y + (int)(height*0.33);
        x[5] = point.x + width;
        y[5] = point.y + (int)(height*0.66);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
        g2.fill(new Polygon(x, y, 6));

        if (stroke > 0) {
            g2.setColor(strokeColor);
            g2.setStroke(new BasicStroke(stroke));
            g2.draw(new Polygon(x, y, 6));
            g2.setStroke(new BasicStroke(0));
        }
    }

    @Override
    public String toString() {
        return "Hexagon\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + width + "\n" + height + "\n";
    }
}

