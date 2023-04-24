package UI.toolbars;

import UI.Listener;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import shapes.Rectangle;

import java.awt.*;

import static UI.Panel.getColorWindow;
import static UI.Panel.setColorButton;

public class CustomColorToolBar extends ToolBar{

    public CustomColorToolBar(String title, int x, int y, int width, int height, int rows, int columns) {
        super(title, x, y, width, height, rows, columns);
        initToolBar();
    }

    private void initToolBar(){

        TITLE_ON_TOP();

        for (int i = 0; i < 16; i++) {
            addButton(new ActiveButton(new Rectangle(Color.white)));
            buttons.get(i).setTip("R:" + 255 + " G:" + 255 + " B:" + 255);
        }

        for (UI.buttons.Button b : buttons) {
            b.setListener(new Listener() {
                @Override
                public void onPress(int x, int y) {
                    b.isClicked(x, y);
                }

                @Override
                public void onRelease() {
                    if (b.isPressed())
                        setColorButton(getColorWindow().getButtons().get(4), b);
                }

                @Override
                public void onHover(int x, int y) {
                    b.hover(x, y);
                }
            });
        }

    }

    @Override
    public void onPress(int x, int y) {
        for (Button b: buttons) {
            b.getListener().onPress(x, y);
        }
    }

}
