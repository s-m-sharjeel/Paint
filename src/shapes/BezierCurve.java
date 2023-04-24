package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;

public class BezierCurve extends Shape {

    float precision = 0.001F;

    private final LinkedList<Point> vertices;

    public BezierCurve(Color fillColor) {
        super(fillColor);
        points = new Point[4];
        vertices = new LinkedList<>();
    }

    @Override
    public void draw(Graphics g) {

        //g.setColor(Color.black);
        initCurve(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(fillColor);
        g2.setStroke(new BasicStroke(stroke));

        // draw small circles of size stroke on vertices
        for (Point vertex : vertices)
            g2.fill(new Ellipse2D.Double(vertex.x - stroke*0.5, vertex.y - stroke*0.5, stroke, stroke));

        // a red dot flows through bezier
        /*g2.setColor(Color.red);
        g2.fill(new Ellipse2D.Double(vertices.get(Panel.time%vertices.size()).x - stroke, vertices.get(Panel.time% vertices.size()).y - stroke, stroke*2, stroke*2));
        g2.setStroke(new BasicStroke(1));*/

    }

    private void initCurve(Graphics g){

        // clears the array of vertices
        vertices.clear();
        Point p;

        for (float t = 0; t <= 1.0; t += precision) {

            if (points[1] == null) {
                // no control point
                p = linear(points[0], points[3], t, g);
            }

            // in case there is one control point a quadratic bezier is drawn
            else if (points[2] == null) {
                // first control point
                p = quadratic(points[0], points[1], points[3], t, g);
            }

            // in case there are two control points a cubic bezier is drawn
            else {
                // second control point
                p = cubic(points[0], points[1], points[2], points[3], t, g);
            }

            vertices.add(p);

        }
    }

    /**
     * cubic interpolation at t percent
     */
    private Point cubic(Point p1, Point p2, Point p3, Point p4, float t, Graphics g){

        Point v1 = (quadratic(p1, p2, p3, t, g));
        Point v2 = (quadratic(p2, p3, p4, t, g));

        //g.drawLine(v1.x, v1.y, v2.x, v2.y);

        int x = lerp(v1.x, v2.x, t);
        int y = lerp(v1.y, v2.y, t);

        return new Point(x, y);

    }

    /**
     * quadratic interpolation at t percent
     */
    private Point quadratic(Point p1, Point p2, Point p3, float t, Graphics g){
        int x1 = lerp(p1.x, p2.x, t);
        int y1 = lerp(p1.y, p2.y, t);
        int x2 = lerp(p2.x, p3.x, t);
        int y2 = lerp(p2.y, p3.y, t);
        int x = lerp(x1, x2, t);
        int y = lerp(y1, y2, t);

        //g.drawLine(x1, y1, x2, y2);

        return new Point(x, y);
    }

    private Point linear(Point p1, Point p2, float t, Graphics g){
        int x = lerp(p1.x, p2.x, t);
        int y = lerp(p1.y, p2.y, t);

        return new Point(x, y);
    }

    /**
     * returns a value at t percent between the init and final value (linear interpolation)
     */
    private int lerp(int z1, int z2, float t){
        return z1 + (int)((z2 - z1)*t);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("Bezier\n" + fillColor.getRed() + "," + fillColor.getGreen() + "," + fillColor.getBlue() + "\n" + stroke + "\n");
        for (Point p: points) {
            s.append(p.x).append(",").append(p.y).append("\n");
        }
        return s.toString();
    }
}
