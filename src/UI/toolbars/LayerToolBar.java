package UI.toolbars;

import UI.Listener;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.ToggleButton;
import shapes.EquilateralTriangle;

import java.awt.*;

import static UI.Panel.*;

public class LayerToolBar extends ToolBar{

    public LayerToolBar(String title, int x, int y, int width, int height) {
        super(title, x, y, width, height);
        initToolBar();
    }

    private void initToolBar(){
        TITLE_ON_TOP();

        int spacing = 10;
        int buttonSize = width/5;

        Button addLayerButton = new ActiveButton("+", x + width/2 - spacing - buttonSize - buttonSize/2, y + 30 + spacing + buttonSize, buttonSize, buttonSize);
        addLayerButton.setTip("Add Layer");
        addLayerButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                addLayerButton.isClicked(x, y);
                if (addLayerButton.isPressed())
                    addLayer();
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                addLayerButton.hover(x, y);
            }
        });
        buttons.add(addLayerButton);

        Button removeLayerButton = (new ActiveButton("-", x + width/2 - buttonSize/2, y + 30 + spacing + buttonSize, buttonSize, buttonSize));
        removeLayerButton.setTip("Remove Layer");
        removeLayerButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                removeLayerButton.isClicked(x, y);
                if (removeLayerButton.isPressed())
                    removeLayer(getLayerSelected());
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                removeLayerButton.hover(x, y);
            }
        });
        buttons.add(removeLayerButton);

        Button raiseLayerButton = (new ActiveButton(new EquilateralTriangle(Color.black, Color.black)));
        raiseLayerButton.setTip("Raise");
        raiseLayerButton.setBounds(x + width/2 + buttonSize/2 + spacing, y + 30 + spacing + buttonSize, buttonSize, buttonSize / 2);
        raiseLayerButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                raiseLayerButton.isClicked(x, y);
                if (raiseLayerButton.isPressed())
                    raiseLayer(getLayerSelected());
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                raiseLayerButton.hover(x, y);
            }
        });
        buttons.add(raiseLayerButton);

        Button lowerLayerButton = (new ActiveButton(new EquilateralTriangle(Color.black, Color.black, 270)));
        lowerLayerButton.setBounds(x + width/2 + + buttonSize/2 + spacing, y + 30 + spacing + buttonSize / 2 + buttonSize, buttonSize, buttonSize / 2);
        lowerLayerButton.setTip("Lower");

        lowerLayerButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                lowerLayerButton.isClicked(x, y);
                if (lowerLayerButton.isPressed())
                    lowerLayer(getLayerSelected());
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                lowerLayerButton.hover(x, y);
            }
        });
        buttons.add(lowerLayerButton);

    }

    /**
     * adds a button to the list of buttons at index i and sets its bounds according to the layer buttons
     */
    @Override
    public void addButton(Button button, int index) {

        int buttonWidth = (width/5)*4;
        int rowHeight = (height - 30 - (width/5)*4)/10;

        buttons.add(index, button);

        for (int i = index; i < buttons.size(); i++)
            buttons.get(i).setBounds((x + 10) + spacing, (y + 30 + (width/5)*3) + spacing + rowHeight*(i-4), (width - 10*2) - spacing*2, rowHeight - spacing*2);

    }

    @Override
    public void onPress(int x, int y) {

        // 4 navigation control buttons
        for (int i = 0; i < 4; i++) {
            buttons.get(i).getListener().onPress(x, y);
        }

        // gets the index of the toggle button pressed if any so that it is later depressed after any click on the toolbar
        int index = -1;
        for (int i = 4; i < buttons.size(); i++) {
            if(buttons.get(i).isPressed() && buttons.get(i) instanceof ToggleButton) {
                index = i;
            }
        }

        // buttons pressed
        for (int i = 4; i < buttons.size(); i++) {
            buttons.get(i).getListener().onPress(x, y);
            if (i != index && index != -1 && buttons.get(i) instanceof ToggleButton && buttons.get(i).isPressed()){
                // button previously toggled is depressed
                buttons.get(index).setPressed(false);
            }

        }

    }

}
