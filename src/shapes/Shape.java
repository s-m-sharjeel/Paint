package shapes;

import java.awt.*;
import java.util.LinkedList;

public abstract class Shape {

    protected Point point = new Point(0, 0);
    protected int x;
    protected int y;
    protected Color strokeColor = new Color(128, 128, 128);
    protected Color fillColor = new Color(239, 239, 239);
    protected int stroke = 1;
    protected int size;
    protected int width;
    protected int height;
    protected int initAngle = 90;
    protected Point[] points;
    protected LinkedList<Circle> pixels;

    // constructor:
    public Shape(){}

    public Shape(int x, int y, int width, int height){
        this.point = new Point(x, y);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Shape(Color fillColor) {
        this.fillColor = fillColor;
    }

    protected Shape(Color fillColor, Color strokeColor) {
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
    }

    public abstract void draw(Graphics g);

    // getter(s):

    public int getX() {return x;}

    public int getY() {return y;}

    public Color getFillColor() {
        return fillColor;
    }


    public int getSize() {
        return size;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    // setter(s):

    public void setFillColor(Color fillColor) {this.fillColor = fillColor;}

    public void setStrokeColor(Color strokeColor) {this.strokeColor = strokeColor;}

    public void setPoint(Point point) {
        this.point = point;
        this.x = point.x;
        this.y = point.y;
    }

    public void setSize(int size) {this.size = size;}

    public void setWidth(int width) {this.width = width;}

    public void setHeight(int height) {this.height = height;}

    public void setInitAngle(int initAngle) {
        this.initAngle = initAngle;
    }

    public void setStroke(int stroke) {this.stroke = stroke;}

    public void addPixel(int x, int y){
        Circle pixel = new Circle(fillColor);
        pixel.setPoint(new Point(x, y));
        pixels.add(pixel);
    }

    public void setPoint(int x, int y, int index){
        points[index] = new Point(x, y);
    }

}

