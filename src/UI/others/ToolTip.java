package UI.others;

import UI.buttons.Button;

import java.awt.*;

public class ToolTip {

    private final int gap = 5;
    private UI.buttons.Button button;
    private static final ToolTip instance = new ToolTip();

    private	ToolTip() {

    }

    public static ToolTip getInstance() {
        return instance;
    }

    public void draw(Graphics g){

        if (button == null)
            return;

        String tip = button.getTip();
        if (tip.length() > 0){
            g.setColor(Color.black);
            g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 10));
            FontMetrics m = g.getFontMetrics();
            int stringWidth = m.stringWidth(tip);
            int stringHeight = m.getAscent() - m.getDescent();

            g.setColor(Color.white);
            g.fillRect(button.getX() + button.getWidth(), button.getY() + button.getHeight()/2, stringWidth + gap*2, stringHeight + gap*2);

            g.setColor(Color.black);
            g.drawRect(button.getX() + button.getWidth(), button.getY() + button.getHeight()/2, stringWidth + gap*2, stringHeight + gap*2);

            g.setColor(Color.black);
            g.drawString(tip, button.getX() + button.getWidth() + gap, button.getY() + button.getHeight()/2 + stringHeight + gap);
        }

    }

    //set tip
    public void setButton(Button button) {
        this.button = button;
    }

}

