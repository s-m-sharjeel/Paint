package UI.toolbars;

import UI.Listener;
import UI.Panel;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.MenuButton;
import UI.buttons.ToggleButton;

import java.util.LinkedList;

import static UI.Panel.*;

public class MenuBar extends ToolBar {
    public MenuBar(String title, int x, int y, int width, int height, int rows, int columns) {
        super(title, x, y, width, height, rows, columns);
        initToolBar();
    }

    private void initToolBar(){

        LinkedList<Button> buttons1 = new LinkedList<>();

        Button newButton = new ActiveButton("New", "CTRL + N");
        newButton.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                newButton.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (newButton.isPressed()) {
                    newButton.setHovered(false);
                    renew();
                }
            }

            @Override
            public void onHover(int x, int y) {
                newButton.hover(x, y);
            }
        });
        buttons1.add(newButton);

        Button open = new ActiveButton("Open", "CTRL + F");
        open.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                open.isClicked(x, y);
                if (open.isPressed()) {
                    open.setHovered(false);
                    getFileWindow().open();
                }
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                open.hover(x, y);
            }

        });
        buttons1.add(open);

        Button save = new ActiveButton("Save", "CTRL + S");
        save.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                save.isClicked(x, y);
                if (save.isPressed()) {
                    save.setHovered(false);
                    Panel.saveFile();
                }
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                save.hover(x, y);
            }
        });
        buttons1.add(save);

        Button file = new MenuButton("File", buttons1);
        file.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                file.isClicked(x, y);
                if (file.isPressed())
                    file.setVisible(true);
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                file.hover(x, y);
            }
        });
        addButton(file);

        LinkedList<Button> buttons2 = new LinkedList<>();

        Button undoMenu = new ActiveButton("Undo", "CTRL + Z");
        undoMenu.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                undoMenu.isClicked(x, y);
                if (undoMenu.isPressed()) {
                    undoMenu.setHovered(false);
                    Panel.undo();
                }
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                undoMenu.hover(x, y);
            }
        });
        buttons2.add(undoMenu);

        Button redoMenu = new ActiveButton("Redo", "CTRL + Y");
        redoMenu.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                redoMenu.isClicked(x, y);
                if (redoMenu.isPressed()) {
                    redoMenu.setHovered(false);
                    redo();
                }
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                redoMenu.hover(x, y);
            }
        });
        buttons2.add(redoMenu);

        Button edit = new MenuButton("Edit", buttons2);
        edit.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                edit.isClicked(x, y);
                if (edit.isPressed())
                    edit.setVisible(true);
            }

            @Override
            public void onRelease() {

            }

            @Override
            public void onHover(int x, int y) {
                edit.hover(x, y);
            }
        });
        addButton(edit);

        Button undo = new ActiveButton("Undo", "CTRL + Z");
        undo.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                undo.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (undo.isPressed())
                    Panel.undo();
            }

            @Override
            public void onHover(int x, int y) {
                undo.hover(x, y);
            }
        });
        addButton(undo);

        Button redo = new ActiveButton("Redo", "CTRL + Y");
        redo.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                redo.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (redo.isPressed())
                    redo();
            }

            @Override
            public void onHover(int x, int y) {
                redo.hover(x, y);
            }
        });
        addButton(redo);

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
