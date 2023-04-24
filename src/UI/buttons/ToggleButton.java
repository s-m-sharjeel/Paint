package UI.buttons;

import java.util.LinkedList;
import shapes.Shape;

public class ToggleButton extends Button{

    public ToggleButton(Shape shape) {
        super(shape);
    }

    public ToggleButton(Shape shape, String tip) {
        super(shape, tip);
    }

    public ToggleButton(String text) {
        super(text);
    }

    public ToggleButton(String text, LinkedList<Button> buttons) {
        super(text, buttons);
    }

    @Override
    public void isClicked(int x, int y) {

        // if within the bounds of the button
        if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
            pressed = !pressed;

    }

    @Override
    public void onPress(int x, int y) {

    }

    @Override
    public void onRelease() {

    }

    @Override
    public void onHover(int x, int y) {

    }

}
