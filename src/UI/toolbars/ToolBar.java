package UI.toolbars;

import UI.Listener;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.MenuButton;
import shapes.Rectangle;

import java.awt.*;
import java.util.LinkedList;

public abstract class ToolBar extends Rectangle implements Listener {

    private final String title;
    private int rows = 1;
    private int columns = 1;
    private int buttonWidth;
    private int buttonHeight;
    private int leftButtons;
    private int rightButtons;
    protected final int spacing = 5;
    protected final LinkedList<Button> buttons = new LinkedList<>();
    private boolean titleOnTop;
    private boolean leftAlign;
    private boolean pressed;

    public ToolBar(String title, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.title = title;
        initToolBar();
    }

    public ToolBar(String title, int x, int y, int width, int height, int rows, int columns, int leftButtons, int rightButtons) {

        super(x, y, width, height);
        this.title = title;
        this.rows = rows;
        this.columns = columns;
        this.leftButtons = leftButtons;
        this.rightButtons = rightButtons;
        initToolBar();
    }

    public ToolBar(String title, int x, int y, int width, int height, int rows, int columns) {

        super(x, y, width, height);
        this.title = title;
        this.rows = rows;
        this.columns = columns;
        initToolBar();
    }

    private void initToolBar() {

        int titleHeight = 0;
        if(title.length()>0)
            titleHeight = 30;

        buttonWidth = (width - spacing*(columns + 1 + leftButtons + rightButtons))/(columns + leftButtons*2 + rightButtons*2);
        buttonHeight = ((height - spacing*(rows + 1) - titleHeight)/rows);

    }

    public void paint(Graphics g) {

        super.setFillColor(new Color(239, 239, 239));
        super.draw(g);

        if(title.length() > 0){
            g.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
            FontMetrics m = g.getFontMetrics();
            int stringWidth = m.stringWidth(title);
            int stringHeight = m.getAscent() - m.getDescent();
            if (titleOnTop){
                g.setColor(new Color(128, 128, 128));
                g.drawLine(x + 10, y + 30, x + width - 10, y + 30);
                g.setColor(Color.black);
                if (leftAlign)
                    g.drawString(title, x + stringHeight/2, y + 15 + stringHeight / 2);
                else g.drawString(title, x + width / 2 - stringWidth / 2, y + 15 + stringHeight / 2);
            }else {
                g.setColor(new Color(128, 128, 128));
                g.drawLine(x + 10, y + height - 30, x + width - 10, y + height - 30);
                g.setColor(Color.black);
                if (leftAlign)
                    g.drawString(title, x + stringHeight/2, y + height - 15 + stringHeight / 2);
                else g.drawString(title, x + width / 2 - stringWidth / 2, y + height - 15 + stringHeight / 2);
            }
        }

        paintButtons(g);

    }

    private void paintButtons(Graphics g) {

        for (Button button: buttons) {
            button.paint(g);
            if (button instanceof MenuButton) {
                for (Button menuButton : button.getButtons()) {
                    if (menuButton.isPressed())
                        menuButton.paint(g);
                }
            }
        }
    }

    /**
     * adds a button to the list of buttons
     */
    public void addButton(Button button){

        int titleHeight = 0;
        if (titleOnTop)
            titleHeight = 30;

        button.setBounds(x + ((leftButtons * (buttonWidth*2 + spacing)) + spacing + (buttonWidth + spacing) * ((buttons.size() - leftButtons) % columns)), y + titleHeight + spacing + ((buttonHeight + spacing) * ((buttons.size() - leftButtons) / columns)), buttonWidth, buttonHeight);
        if (button instanceof MenuButton) {
            for (int i = 0; i < button.getButtons().size(); i++) {
                button.getButtons().get(i).setBounds(button.getX(), button.getY() + spacing + buttonHeight + (25 * i), 200, 25);
            }
        }
        buttons.add(button);

    }

    /**
     * adds a button to the list of buttons at some index i
     */
    public void addButton(Button button, int index){

        int rowHeight = height/10;

        buttons.add(index, button);

        for (int i = index; i < buttons.size(); i++)
            buttons.get(i).setBounds((x) + spacing, (y) + spacing + rowHeight*(i-4), (width) - spacing*2, rowHeight - spacing*2);

    }

    /**
     * removes the button from the list of buttons and sets the bounds of the buttons accordingly
     */
    public void removeButton(int index){

        for (int i = buttons.size() - 1; i > index; i--)
            buttons.get(i).setBounds(buttons.get(i - 1).getX(), buttons.get(i - 1).getY(), buttons.get(i - 1).getWidth(), buttons.get(i - 1).getHeight());

        buttons.remove(index);

    }

    /**
     * adds a button to the left of the toolbar
     */
    public void addLeftButton(Button button){

        int titleHeight = 0;
        if (titleOnTop)
            titleHeight = 30;

        button.setBounds(x + spacing + ((buttonWidth*2 + spacing) * buttons.size()), y + titleHeight + spacing, buttonWidth*2, buttonHeight*rows + (spacing * (rows - 1)));
        if (button instanceof MenuButton) {
            for (int i = 0; i < button.getButtons().size(); i++) {
                button.getButtons().get(i).setBounds(button.getX(), button.getY() + spacing*2 + buttonHeight*2 + (25 * i), buttonWidth*2, 25);
            }
        }
        buttons.add(button);

    }

    /**
     * adds a button to the right of the toolbar
     */
    public void addRightButton(Button button){

        int titleHeight = 0;
        if (titleOnTop)
            titleHeight = 30;

        button.setBounds(x + width - buttonWidth*2 - spacing - ((buttonWidth*2 + spacing)) * ((buttons.size() - leftButtons - (rows*columns))), y + titleHeight + spacing, buttonWidth*2, buttonHeight*rows + (spacing * (rows - 1)));
        if (button instanceof MenuButton) {
            for (int i = 0; i < button.getButtons().size(); i++) {
                button.getButtons().get(i).setBounds(button.getX(), button.getY() + spacing*2 + buttonHeight*2 + (25 * i), buttonWidth*2, 25);
            }
        }
        buttons.add(button);

    }

    /**
     * title is set to be displayed at the top of the toolbar which is by-default displayed at the bottom
     */
    public void TITLE_ON_TOP(){
        titleOnTop = true;
    }

    /**
     * aligns text to the left in the toolbar
     */
    public void LEFT_ALIGN(){
        leftAlign = true;
        for (Button button: buttons) {
            button.LEFT_ALIGN();
            if (button instanceof MenuButton) {
                for (Button menuButton : button.getButtons()) {
                    if (menuButton.isPressed())
                        menuButton.LEFT_ALIGN();
                }
            }
        }
    }

    public void setPressed(boolean pressed) {

        this.pressed = pressed;

        for (Button button: buttons) {
            if (button instanceof ActiveButton)
                button.setPressed(pressed);
            if (button instanceof MenuButton) {
                if (!button.isVisible())
                    button.setPressed(false);
                for (Button menuButton : button.getButtons()) {
                    menuButton.setPressed(pressed);
                }
            }
        }

    }

    public boolean isPressed() {
        return pressed;
    }

    public void setHovered(boolean hovered) {

        for (Button button: buttons) {
            if (button instanceof ActiveButton)
                button.setHovered(hovered);
            if (button instanceof MenuButton) {
                if (!button.isVisible())
                    button.setHovered(false);
                for (Button menuButton : button.getButtons()) {
                    menuButton.setHovered(hovered);
                }
            }
        }

    }

    public void onRelease() {

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

    public void onHover(int x, int y) {

        for (Button button : buttons) {
            if (button.getListener() != null) {
                button.getListener().onHover(x, y);
            }
        }

    }

    public LinkedList<Button> getButtons() {
        return buttons;
    }
}
