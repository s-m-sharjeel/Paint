package UI.buttons;

import java.awt.*;
import java.util.LinkedList;

public class MenuButton extends ToggleButton{

    public MenuButton(String text, LinkedList<Button> buttons) {
        super(text, buttons);
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        // paints the dropdown list if base button is pressed
        if(pressed)
            for (Button button : buttons)
                button.paint(g);

    }
}
