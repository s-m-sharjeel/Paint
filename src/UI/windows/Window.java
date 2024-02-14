package UI.windows;

import UI.Listener;
import UI.buttons.ActiveButton;
import UI.buttons.MenuButton;
import UI.others.TextBox;
import UI.buttons.Button;
import UI.toolbars.ToolBar;
import shapes.Rectangle;
import java.awt.*;
import java.util.LinkedList;

public abstract class Window extends Rectangle implements Listener {

    protected LinkedList<ToolBar> toolBars = new LinkedList<>();

    protected LinkedList<Button> buttons = new LinkedList<>();

    protected LinkedList<TextBox> textBoxes = new LinkedList<>();

    private boolean open;

    private boolean pressed;
    private boolean hovered;

    public Window(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void paint(Graphics g) {

        super.draw(g);

        paintTextBoxes(g);
        paintButtons(g);
        paintToolBars(g);

    }

    private void paintTextBoxes(Graphics g) {
        for (TextBox tb : textBoxes)
            tb.paint(g);
    }

    private void paintButtons(Graphics g){
        for (Button button : buttons)
            button.paint(g);
    }

    private void paintToolBars(Graphics g){
        for (int i = toolBars.size() - 1; i >= 0; i--)
            toolBars.get(i).paint(g);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        this.open = true;
    }

    public void setPressed(boolean pressed) {
        this.pressed = pressed;

        for (ToolBar toolBar : toolBars)
            toolBar.setPressed(false);

        for (Button button : buttons)
            button.setPressed(false);

    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;

        for (ToolBar toolBar : toolBars)
            toolBar.setHovered(false);

        for (Button button : buttons)
            button.setHovered(false);

    }

    public void close(){
        setPressed(false);
        setHovered(false);
        this.open = false;
    }

    @Override
    public void onPress(int x, int y) {

        for (Button b : buttons) {
            if (b.getListener() != null) {
                b.getListener().onPress(x, y);
            }
        }

        for (ToolBar t : toolBars) {
            t.onPress(x, y);
        }

    }

    @Override
    public void onRelease() {

        for (ToolBar t : toolBars) {
            t.onRelease();
        }

        for (Button button : buttons) {
            if (button.getListener() != null) {
                button.getListener().onRelease();
                if (button instanceof MenuButton){
                    for (Button menuButton: button.getButtons()) {
                        if (menuButton.getListener() != null)
                            menuButton.getListener().onRelease();
                    }
                }
            }
        }

        for (Button button: buttons) {
            if (button instanceof ActiveButton)
                button.setPressed(false);
            if (button instanceof MenuButton) {
                if (!button.isVisible())
                    button.setPressed(false);
                for (Button menuButton : button.getButtons()) {
                    menuButton.setPressed(false);
                }
            }
        }

    }

    public void onHover(int x, int y){

        for (Button b : buttons) {
            if (b.getListener() != null) {
                b.getListener().onHover(x, y);
            }
        }

        for (ToolBar t : toolBars) {
            t.onHover(x, y);
        }

    }

    public LinkedList<Button> getButtons() {
        return buttons;
    }

    public LinkedList<ToolBar> getToolBars() {
        return toolBars;
    }

    public LinkedList<TextBox> getTextBoxes() {
        return textBoxes;
    }
}
