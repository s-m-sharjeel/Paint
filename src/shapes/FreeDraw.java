package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
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
        for (Circle pixel : pixels) {
            g2.fill(new Ellipse2D.Double(pixel.point.x - stroke/2, pixel.point.y - stroke/2, stroke, stroke));
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
