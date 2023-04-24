package shapes;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Rectangle extends Shape {

    private boolean raised = true;

    public Rectangle(){}

    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public Rectangle(Color fillColor) {
        super(fillColor);
    }

    public Rectangle(Color fillColor, Color strokeColor) {
        super(fillColor, strokeColor);
    }

    @Override
    public void draw(Graphics g) {

        if (stroke == 0){
            g.setColor(fillColor);
            g.fillRect(point.x, point.y, width, height);
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
        g2.fill3DRect(point.x, point.y, width, height, raised);
        g2.setColor(strokeColor);
        g2.setStroke(new BasicStroke(stroke));
        g2.draw(new Rectangle2D.Double(point.x, point.y, width, height));
        g2.setStroke(new BasicStroke(1));

    }

    public void setBounds(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setRaised(boolean raised) {
        this.raised = raised;
    }

    /**
     * @return the state of the Rectangle
     */
    @Override
    public String toString() {
        return "Rectangle\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + strokeColor.getRed() + "," + strokeColor.getGreen() + "," + strokeColor.getBlue() + "\n" + stroke + "\n" + point.x + "," + point.y + "\n" + width + "\n" + height + "\n";
    }
}
