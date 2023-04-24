package UI.others;

import java.awt.*;

public class Grid {

    private static int drawingX;
    private static int drawingY;
    private static int drawingWidth;
    private static int drawingHeight;
    private static int size;
    private static final Grid instance = new Grid();

    private Grid() {

    }

    public static Grid getInstance(int size1, int drawingX1, int drawingY1, int drawingWidth1, int drawingHeight1) {
        size = size1;
        drawingX = drawingX1;
        drawingY = drawingY1;
        drawingWidth = drawingWidth1;
        drawingHeight = drawingHeight1;

        return instance;
    }

    public void drawGrid(Graphics g){

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

}
