package UI.windows;

import UI.toolbars.MenuBar;
import UI.toolbars.*;

import static UI.Panel.*;

public class MainWindow extends Window{

    public MainWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
        initWindow();
    }

    private void initWindow(){

        int menuHeight = 30;
        int toolBarHeight = 140;
        int shapeToolBarWidth = 350;
        int gridToolBarWidth = 110;
        int strokeToolBarWidth = 150;
        int colorToolBarWidth = 590;
        int layerToolBarWidth = getPanelWidth() - shapeToolBarWidth - gridToolBarWidth - strokeToolBarWidth - colorToolBarWidth;


        // menu bar
        ToolBar menuBar = new MenuBar("", x, y, getPanelWidth(), menuHeight, 1, 19);
        toolBars.add(menuBar);

        //shapes toolbar
        ToolBar shapeToolBar = new ShapeToolBar("Shapes" ,x, y + menuHeight,shapeToolBarWidth, toolBarHeight, 2, 3, 1, 1);
        toolBars.add(shapeToolBar);

        // gridToolBar
        ToolBar gridToolBar = new GridToolBar("Grid" ,x + shapeToolBarWidth, y + menuHeight, gridToolBarWidth, toolBarHeight, 1, 1);
        toolBars.add(gridToolBar);

        // strokeToolBar
        ToolBar strokeToolBar = new StrokeToolBar("Stroke", x + shapeToolBarWidth + gridToolBarWidth, y + menuHeight, strokeToolBarWidth, toolBarHeight, 2, 1, 1, 0);
        toolBars.add(strokeToolBar);

        // color toolbar
        ToolBar colorToolBar = new ColorToolBar("Colors", x + shapeToolBarWidth + gridToolBarWidth + strokeToolBarWidth, y + menuHeight,colorToolBarWidth, toolBarHeight, 3, 10, 2, 1);
        toolBars.add(colorToolBar);

        // layers toolbar
        ToolBar layerToolBar = new LayerToolBar("Layers", x + shapeToolBarWidth + gridToolBarWidth + strokeToolBarWidth + colorToolBarWidth, y + menuHeight, layerToolBarWidth, getPanelHeight() - menuHeight);
        toolBars.add(layerToolBar);

    }

    @Override
    public void onPress(int x, int y) {

        // file menu dropdown
        if (toolBars.get(0).getButtons().get(0).isVisible()){
            for (int i = 0; i < 3; i++) {
                toolBars.get(0).getButtons().get(0).getButtons().get(i).getListener().onPress(x, y);
            }
            toolBars.get(0).getButtons().get(0).setVisible(false);
            return;
        }

        // edit menu dropdown
        if (toolBars.get(0).getButtons().get(1).isVisible()) {
            for (int i = 0; i < 2; i++) {
                toolBars.get(0).getButtons().get(1).getButtons().get(i).getListener().onPress(x, y);
            }
            toolBars.get(0).getButtons().get(1).setVisible(false);
            return;
        }

        // stroke dropdown
        if (toolBars.get(3).getButtons().get(0).isVisible()) {
            for (int i = 0; i < 7; i++) {
                toolBars.get(3).getButtons().get(0).getButtons().get(i).getListener().onPress(x, y);
            }
            return;
        }

        super.onPress(x, y);
    }

    @Override
    public void onHover(int x, int y) {

        // file menu dropdown
        if (toolBars.get(0).getButtons().get(0).isVisible()){
            for (int i = 0; i < 3; i++) {
                toolBars.get(0).getButtons().get(0).getButtons().get(i).getListener().onHover(x, y);
            }
            return;
        }

        // edit menu dropdown
        if (toolBars.get(0).getButtons().get(1).isVisible()) {
            for (int i = 0; i < 2; i++) {
                toolBars.get(0).getButtons().get(1).getButtons().get(i).getListener().onHover(x, y);
            }
            return;
        }

        // stroke dropdown
        if (toolBars.get(3).getButtons().get(0).isVisible()) {
            for (int i = 0; i < 7; i++) {
                toolBars.get(3).getButtons().get(0).getButtons().get(i).getListener().onHover(x, y);
            }
            return;
        }

        super.onHover(x, y);
    }
}
