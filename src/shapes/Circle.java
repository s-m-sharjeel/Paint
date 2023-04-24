package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class Circle extends Shape {

    public Circle(Color fillColor) {
        super(fillColor);
    }

    public Circle(Color fillColor, Color strokeColor){
        super(fillColor, strokeColor);
    }

    public Circle() {

    }

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(fillColor);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new Ellipse2D.Double(point.x - size, point.y - size, size*2, size*2));

        if (stroke>0) {
            g2.setColor(strokeColor);
            g2.setStroke(new BasicStroke(stroke));
            g2.draw(new Ellipse2D.Double(point.x - size, point.y - size, size*2, size*2));
            g2.setStroke(new BasicStroke(1));
        }

    }

    /**
     * @return the state of the Circle
     */
    public String toString(){
        return "Circle\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + size + "\n";
    }

}
