package UI.others;

import java.awt.*;
import static UI.Panel.*;

public class Grid {

    private static int drawingX;
    private static int drawingY;
    private static int drawingWidth;
    private static int drawingHeight;
    private int size = 0;
    private static final Grid instance = new Grid();

    private Grid() {

    }

    public static Grid getInstance() {
        drawingX = getDrawingX();
        drawingY = getDrawingY();
        drawingWidth = getDrawingWidth();
        drawingHeight = getDrawingHeight();

        return instance;
    }

    public void draw(Graphics g){

        if (size == 0)
            return;

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.lightGray);
        g2.setStroke(new BasicStroke(1));

        for (int i = drawingX; i < drawingX + drawingWidth; i+=size) {
            g2.drawLine(i, drawingY, i, drawingY + drawingHeight);
        }

        for (int i = drawingY; i < drawingY + drawingHeight; i+=size) {
            g2.drawLine(drawingX, i, drawingX + drawingWidth, i);
        }

    }

    public void setSize(int size) {
        this.size = size;
    }
}
