package UI.buttons;

public class GridButton extends ActiveButton {

    public GridButton(String text) {
        super(text);
    }

    @Override
    public void isClicked(int x, int y) {
        super.isClicked(x, y);
        if (pressed) {
            clicks++;
            clicks = clicks % 7;
        }
        if (clicks == 0)
            setText("OFF");
        else {
            setText("" + (int)Math.pow(2, clicks));
        }
    }
}
