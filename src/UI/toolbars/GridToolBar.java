package UI.toolbars;

import UI.Listener;
import UI.others.Grid;
import UI.buttons.Button;
import UI.buttons.GridButton;

import static UI.Panel.*;

public class GridToolBar extends ToolBar{

    public GridToolBar(String title, int x, int y, int width, int height, int rows, int columns) {
        super(title, x, y, width, height, rows, columns);
        initToolBar();
    }

    private void initToolBar(){

        Button gridButton = new GridButton("OFF");
        gridButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                gridButton.isClicked(x, y);
                if (gridButton.isPressed()){
                    int size;
                    if (gridButton.getText().equals("OFF"))
                        size = 0;
                    else size = Integer.parseInt(gridButton.getText());
                    setGrid(Grid.getInstance(size, getDrawingX(), getDrawingY(), getDrawingWidth(), getDrawingHeight()));
                }
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                gridButton.hover(x, y);
            }
        });
        addButton(gridButton);

    }

    @Override
    public void onPress(int x, int y) {
        for (Button b: buttons) {
            b.getListener().onPress(x, y);
        }
    }

}
