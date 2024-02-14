package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.LinkedList;

public class FreeDraw extends Shape{

    public FreeDraw(Color fillColor) {
        super(fillColor);
        pixels = new LinkedList<>();
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);

        if (stroke > 0) {
            for (Circle pixel : pixels) {
                g2.fill(new Ellipse2D.Double(pixel.point.x - stroke / 2, pixel.point.y - stroke / 2, stroke, stroke));
            }
            g2.setStroke(new BasicStroke(stroke));
            for (int i = 0; i < pixels.size() - 1; i++) {
                g2.draw(new Line2D.Double(pixels.get(i).x, pixels.get(i).y, pixels.get(i + 1).x, pixels.get(i + 1).y));
            }
            g2.setStroke(new BasicStroke(0));
        }

    }

    @Override
    public String toString() {
        String s = "Line\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + stroke + "\n";
        for (Circle pixel: pixels) {
            s += pixel.point.x + "," + pixel.point.y + "\n";
        }
        return s;
    }
}
