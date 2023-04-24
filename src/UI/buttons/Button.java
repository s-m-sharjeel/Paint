package UI.buttons;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.LinkedList;

import UI.Listener;
import shapes.*;
import shapes.Rectangle;
import shapes.Shape;

public abstract class Button extends Rectangle implements Listener {

    private Shape shape;
    private String text = "";
    private String tip = "";
    protected LinkedList<Button> buttons;
    protected boolean pressed;
    protected boolean hovered;
    private boolean visible;
    private boolean leftAlign;
    protected int clicks;
    protected int size;
    private int textHeight = 0;
    private Listener listener;

    protected final Color defaultColor = super.getFillColor();
    protected Color hoverColor = new Color(188, 199, 216);

    public Button(String text) {
        this.text = text;
    }

    public Button(String text, String tip) {
        this.text = text;
        this.tip = tip;
    }

    public Button(Shape shape) {
        this.shape = shape;
    }

    public Button(Shape shape, String tip){
        this.shape = shape;
        this.tip = tip;
    }

    public Button(String text, int x, int y, int width, int height){
        super(x, y, width, height);
        this.text = text;
    }

    public Button(String text, LinkedList<Button> buttons) {
        this.text = text;
        this.buttons = buttons;
    }

    public Button(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    /**
     * paints a button w.r.t its state
     */
    public void paint(Graphics g){

        // sets the state of the 3D rectangle
        setRaised(!pressed);

        if (hovered){
            setFillColor(hoverColor);
        } else setFillColor(defaultColor);

        // draws a 3D rectangle
        super.draw(g);

        // draws the icon
        drawText(g);
        drawShape(g);

    }

    /**
     * writes the text for the button icon
     */
    private void drawText(Graphics g){

        if(text.length() > 0) {

            int fontSize = textHeight / 2;

            g.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));

            g.setColor(Color.black);
            if (shape != null) {
                textHeight = 20;
                g.setColor(new Color(128, 128, 128));
                g.drawLine(x + 10, y + height - textHeight, x + width - 10, y + height - textHeight);

            } else textHeight = height;

            g.setColor(Color.black);
            FontMetrics m = g.getFontMetrics();
            int stringWidth = m.stringWidth(text);
            int stringHeight = m.getAscent() - m.getDescent();

            while(stringWidth > width/2 + width/4){
                fontSize--;
                g.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
                m = g.getFontMetrics();
                stringWidth = m.stringWidth(text);
                stringHeight = m.getAscent() - m.getDescent();
            }

            if (leftAlign)
                g.drawString(text, x + stringHeight / 2, y + height - textHeight / 2 + stringHeight / 2);
            else g.drawString(text, x + width / 2 - stringWidth / 2, y + height - textHeight / 2 + stringHeight / 2);

        }

    }

    /**
     * draws the shape for the button icon
     */
    private void drawShape(Graphics g){

        if(shape != null){

            shape.setStrokeColor(Color.black);
            shape.setWidth(width/2 + width/4);
            shape.setHeight(height/2 + height/4 - textHeight);
            shape.setSize(Math.min(width, height - textHeight)/4 + Math.min(width, height - textHeight)/8);

            if (shape instanceof Circle || shape instanceof EquilateralTriangle || shape instanceof Pentagram)
                shape.setPoint(new Point(x + width/2, y + height/2 - textHeight/2));
            else shape.setPoint(new Point(x + width/8, y + height/8));

            if (shape instanceof FreeDraw){
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.black);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.draw(new Line2D.Double(x + width*0.25, y + height*0.25, x + width - width*0.25, y + height - textHeight - height*0.25));
                return;
            }

            if (shape instanceof BezierCurve){
                shape.setPoint(x + width/4, y + height/2, 0);
                shape.setPoint(x + width/3, y - textHeight, 1);
                shape.setPoint(x + 2*width/3, y + height - textHeight, 2);
                shape.setPoint(x + width/2 + width/4, y + textHeight, 3);
            }

            shape.draw(g);
        }

    }

    /**
     * an abstract function which sets the state of the button after a particular type of button is clicked
     * @param x is the x coordinate of the mouse
     * @param y is the y coordinate of the mouse
     */
    public abstract void isClicked(int x, int y);

    public Boolean isPressed() {return pressed;}

    public void hover(int x, int y){
        hovered = x > this.x && x < this.x + width && y > this.y && y < this.y + height;
    }

    public boolean isHovered() {
        return hovered;
    }

    /**
     * returns the icon shape of the button
     */
    public Shape getShape() {return shape;}

    public void setPressed(boolean pressed) {this.pressed = pressed;}

    public void setHovered(boolean hovered){
        this.hovered = hovered;
    }

    /**
     * aligns the text to the left of the button
     */
    public void LEFT_ALIGN(){leftAlign = true;}

    public void setBounds(int x, int y, int width, int height) {
        point.x = x;
        point.y = y;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setText(String text) {this.text = text;}

    public String getText() {return text;}

    public void setVisible(boolean visible) {this.visible = visible;}

    public boolean isVisible() {return visible;}

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public Listener getListener() {
        return listener;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getTip() {
        return tip;
    }

    public LinkedList<Button> getButtons() {
        return buttons;
    }
}

