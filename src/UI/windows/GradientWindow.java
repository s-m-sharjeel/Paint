package UI.windows;

import UI.Listener;
import UI.Panel;
import UI.others.TextBox;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.CloseButton;
import UI.toolbars.CustomColorToolBar;
import UI.toolbars.ToolBar;
import shapes.Rectangle;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static UI.Panel.*;

public class GradientWindow extends Window{

    private static final int width = 650;
    private static final int height = 550;
    private static final int x = getPanelX() + getPanelWidth()/2 - width/2;
    private static final int y = getPanelY() + getPanelHeight()/2 - height/2;

    private static final GradientWindow instance = new GradientWindow();

    public GradientWindow() {
        super(x, y, width, height);
        initGradientWindow();
    }

    public static GradientWindow getInstance(){
        return instance;
    }

    private void initGradientWindow(){

        int columnWidth = width/16;
        int rowHeight = height/14;
        int spacing = 5;

        UI.buttons.Button close = new CloseButton(x + width - columnWidth, y, columnWidth, rowHeight);
        close.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                close.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (close.isPressed()) {
                    close();
                }
            }

            @Override
            public void onHover(int x, int y) {
                close.hover(x, y);
            }
        });
        buttons.add(close);

        UI.buttons.Button ok = (new ActiveButton("OK", x + spacing, y + height - rowHeight + spacing, 3*(columnWidth - spacing), rowHeight - spacing*2));
        ok.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                ok.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (ok.isPressed()){
                    setColorButton(getMainWindow().getToolBars().get(4).getButtons().get(22 + getColorCount()%10), buttons.get(4));
                    setColorCount(getColorCount()+1);
                    close();
                }
            }

            @Override
            public void onHover(int x, int y) {
                ok.hover(x, y);
            }
        });
        buttons.add(ok);

        UI.buttons.Button cancel = (new ActiveButton("Cancel", x + spacing + 3*(columnWidth - spacing) + spacing, y + height - rowHeight + spacing, 3*(columnWidth - spacing), rowHeight - spacing*2));
        cancel.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                cancel.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (cancel.isPressed()){
                    close();
                }
            }

            @Override
            public void onHover(int x, int y) {
                cancel.hover(x, y);
            }
        });
        buttons.add(cancel);

        UI.buttons.Button addCustom = (new ActiveButton("Add to Custom Colors", x + columnWidth*8 + spacing, y + height - rowHeight + spacing, columnWidth*8 - spacing*2, rowHeight - spacing*2));
        addCustom.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                addCustom.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (addCustom.isPressed()){
                    setColorButton(toolBars.get(0).getButtons().get(getCustomCount()%16), buttons.get(4));
                    setCustomCount(getCustomCount()+1);
                }
            }

            @Override
            public void onHover(int x, int y) {
                addCustom.hover(x, y);
            }
        });
        getButtons().add(addCustom);

        UI.buttons.Button colorSelected = new ActiveButton(new Rectangle(Color.white));
        colorSelected.setBounds(x + columnWidth*8 + spacing, y + (rowHeight*9) + spacing, columnWidth*4 - spacing*2, rowHeight*3 - spacing*2);
        colorSelected.setText("Color Selected");
        getButtons().add(colorSelected);

        UI.buttons.Button colorGradient = new ActiveButton("", x + spacing + 40, y + rowHeight*2 + spacing, 510, 255);
        colorGradient.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                colorGradient.isClicked(x, y);
                if (colorGradient.isPressed())
                    setColorFromGradient(x, y);
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                colorGradient.hover(x, y);
            }
        });
        getButtons().add(colorGradient);

        UI.buttons.Button fade = new ActiveButton("", x + spacing + 40 + columnWidth*16 - spacing*2 - 120 + spacing + 20, y + rowHeight*2 + spacing, 30, 255);
        fade.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                fade.isClicked(x, y);
                if (fade.isPressed())
                    setFade(x, y);
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                fade.hover(x, y);
            }
        });
        getButtons().add(fade);

        TextBox title = new TextBox("Edit Colors", x, y, width, rowHeight);
        title.LEFT_ALIGN();

        textBoxes.add(title);
        textBoxes.add(new TextBox("Red:    " + colorSelected.getShape().getFillColor().getRed(), x + columnWidth*12 + spacing, y + rowHeight*9 + spacing, columnWidth*4 - spacing*2, rowHeight - spacing*2));
        textBoxes.add(new TextBox("Green:  " + colorSelected.getShape().getFillColor().getGreen(), x + columnWidth*12 + spacing, y + rowHeight*10 + spacing, columnWidth*4 - spacing*2, rowHeight - spacing*2));
        textBoxes.add(new TextBox("Blue:   " + colorSelected.getShape().getFillColor().getBlue(), x + columnWidth*12 + spacing, y + rowHeight*11 + spacing, columnWidth*4 - spacing*2, rowHeight - spacing*2));


        ToolBar customColors = new CustomColorToolBar("Custom Colors", x + spacing, y + spacing + (rowHeight*9), (columnWidth*8) - spacing*2, (rowHeight*3) - spacing*2, 2, 8);
        toolBars.add(customColors);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintColorGradient(buttons.get(5), getFade(), g);
        paintFade(buttons.get(6), g);
    }


    /**
     * sets the color from the color gradient
     */
    private void setColorFromGradient(int x, int y){

        int X = x - getColorWindow().getButtons().get(5).getX();
        int Y = y - getColorWindow().getButtons().get(5).getY();

        if (X >= 0 && X < 510 && Y >= 0 && Y < 254){

            int red = getGradientColor()[X][Y][0];
            int green = getGradientColor()[X][Y][1];
            int blue = getGradientColor()[X][Y][2];

            getColorWindow().getButtons().get(4).getShape().setFillColor(new Color(red, green, blue));
            getColorWindow().textBoxes.get(1).setText("Red:    " + red);
            getColorWindow().textBoxes.get(2).setText("Green:  " + green);
            getColorWindow().textBoxes.get(3).setText("Blue:   " + blue);
            getColorWindow().getButtons().get(4).setTip("R:" + red + " G:" + green + " B:" + blue);
        }

    }

    /**
     * paints a slider for setting the fade on the color gradient
     */
    private void paintFade(Button button, Graphics g){

        int x = button.getX();
        int y = button.getY();
        int width = button.getWidth();
        int height = button.getHeight();

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        for (int i = 0; i < height; i++) {
            g2.setColor(new Color(255 - i, 255 - i, 255 - i));
            g2.draw(new Rectangle2D.Double(x, y + i, width, 1));
            g2.fill(new Rectangle2D.Double(x, y + i, width, 1));
        }

        g2.setColor(Color.red);
        g2.fillRect(x, y + (255 - UI.Panel.getFade()) - 1, width, 3);

    }

    /**
     * sets the fade from the fade gradient
     */
    private void setFade(int x, int y){

        int X = x - getColorWindow().getButtons().get(6).getX();
        int Y = y - getColorWindow().getButtons().get(6).getY();

        Panel.setFade(255 - Y);

    }

}
