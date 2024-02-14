package UI.toolbars;

import UI.Listener;
import UI.buttons.Button;
import UI.buttons.ToggleButton;
import shapes.*;
import shapes.Rectangle;
import java.awt.*;

import static UI.Panel.setClicks;
import static UI.Panel.setShapeChoice;

public class ShapeToolBar extends ToolBar{

    public ShapeToolBar(String title, int x, int y, int width, int height, int rows, int columns, int leftButtons, int rightButtons) {
        super(title, x, y, width, height, rows, columns, leftButtons, rightButtons);
        initToolBar();
    }

    private void initToolBar(){

        Button freeDraw = new ToggleButton(new FreeDraw(Color.black));
        freeDraw.setText("Free Draw");
        addLeftButton(freeDraw);

        addButton(new ToggleButton(new Circle(), "Circle"));
        addButton(new ToggleButton(new Rectangle(), "Rectangle"));
        addButton(new ToggleButton(new RightTriangle(), "Right Triangle"));
        addButton(new ToggleButton(new EquilateralTriangle(), "Equilateral Triangle"));
        addButton(new ToggleButton(new Hexagon(), "Hexagon"));
        addButton(new ToggleButton(new Pentagram(), "Pentagram"));

        Button bezierButton = new ToggleButton(new BezierCurve(Color.black));
        bezierButton.setText("Bezier Curve");
        addRightButton(bezierButton);

        for (Button button : buttons) {
            button.setListener(new Listener() {
                @Override
                public void onPress(int x, int y) {
                    button.isClicked(x, y);
                }

                @Override
                public void onRelease() {

                }

                @Override
                public void onHover(int x, int y) {
                    button.hover(x, y);
                }
            });
        }

    }

    @Override
    public void onPress(int x, int y) {
        // gets the index of the toggle button pressed if any so that it is later depressed after any click on the toolbar
        int index = -1;
        for (int i = 0; i < buttons.size(); i++) {
            if(buttons.get(i).isPressed() && buttons.get(i) instanceof ToggleButton) {
                index = i;
            }
        }

        // buttons pressed
        for (int i = 0; i < buttons.size(); i++) {
            if (buttons.get(i).getListener() != null){
                buttons.get(i).getListener().onPress(x, y);
                if (i != index && index != -1 && buttons.get(i) instanceof ToggleButton && buttons.get(i).isPressed()){
                    // button previously toggled is depressed
                    buttons.get(index).setPressed(false);
                }
            }

        }
    }

}
