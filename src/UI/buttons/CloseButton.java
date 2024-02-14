package UI.buttons;

import java.awt.*;

public class CloseButton extends ActiveButton{

    public CloseButton(int x, int y, int width, int height) {
        super(x + 1, y + 1, width - 2, height - 2);
        hoverColor = new Color(225, 0, 0, 200);
        setTip("Close");
    }

    @Override
    public void paint(Graphics g) {
        super.setStroke(0);
        setText("X");
        super.paint(g);
    }
}
