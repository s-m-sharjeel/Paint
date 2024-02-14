package UI.toolbars;

import UI.Listener;
import UI.windows.MessageWindow;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.MenuButton;
import UI.buttons.ToggleButton;

import java.util.LinkedList;
import static UI.Panel.*;

public class StrokeToolBar extends ToolBar{

    public StrokeToolBar(String title, int x, int y, int width, int height, int rows, int columns, int leftButtons, int rightButtons) {
        super(title, x, y, width, height, rows, columns, leftButtons, rightButtons);
        initToolBar();
    }

    private void initToolBar(){

        // stroke dropdown button
        LinkedList<Button> buttons3 = new LinkedList<>();
        for (int i = 0; i < 7; i++)
            buttons3.add(new ActiveButton("" + i));

        Button strokeButton = new MenuButton("" + 1, buttons3);
        strokeButton.setTip("Stroke Value");
        strokeButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                strokeButton.isClicked(x, y);
                if (strokeButton.isPressed())
                    strokeButton.setVisible(true);
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                strokeButton.hover(x, y);
            }
        });
        addLeftButton(strokeButton);

        int j = 0;
        for (Button b : strokeButton.getButtons()) {
            int finalJ = j;
            b.setListener(new Listener() {
                @Override
                public void onPress(int x, int y) {
                    b.isClicked(x, y);
                    if (b.isPressed())
                        strokeButton.setText("" + finalJ);
                }

                @Override
                public void onRelease() {
                    if (b.isPressed())
                        strokeButton.setVisible(false);
                }

                @Override
                public void onHover(int x, int y) {
                    b.hover(x, y);
                }
            });
            j++;
        }

        // increase/decrease stroke buttons
        Button strokeInc = (new ActiveButton("+"));
        strokeInc.setTip("Inc. Stroke");
        strokeInc.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                strokeInc.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (strokeInc.isPressed()){
                    if( Integer.parseInt(strokeButton.getText()) == 6)
                        setMessageWindow(MessageWindow.getInstance("Error", "Stroke can't be greater than 6!"));
                    else {
                        // increments the value of stroke
                        strokeButton.setText("" + (Integer.parseInt(strokeButton.getText())+1));
                    }
                }
            }

            @Override
            public void onHover(int x, int y) {
                strokeInc.hover(x, y);
            }
        });
        addButton(strokeInc);

        Button strokeDec = (new ActiveButton("-"));
        strokeDec.setTip("Dec. Stroke");
        strokeDec.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                strokeDec.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (strokeDec.isPressed()){
                    if( Integer.parseInt(strokeButton.getText()) == 0)
                        setMessageWindow(MessageWindow.getInstance("Error", "Stroke can't be less than 0!"));
                    else {
                        // increments the value of stroke
                        strokeButton.setText("" + (Integer.parseInt(strokeButton.getText())-1));
                    }
                }
            }

            @Override
            public void onHover(int x, int y) {
                strokeDec.hover(x, y);
            }
        });
        addButton(strokeDec);

    }

    @Override
    public void onPress(int x, int y) {

        // buttons pressed
        for (Button button : buttons) {
            if (button.getListener() != null) {
                button.getListener().onPress(x, y);
            }

        }
    }

}
