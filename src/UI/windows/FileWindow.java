package UI.windows;

import UI.Listener;
import UI.Panel;
import UI.others.TextBox;
import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.CloseButton;
import UI.toolbars.FileToolBar;
import UI.toolbars.ToolBar;

import static UI.Panel.*;

public class FileWindow extends Window{

    public FileWindow(int x, int y, int width, int height) {
        super(x, y, width, height);
        initWindow();
    }

    private void initWindow(){

        int columnWidth = width/16;
        int rowHeight = height/14;
        int spacing = 5;


        TextBox fileSelected = new TextBox("File name: ", x + columnWidth, y + rowHeight*11, columnWidth*14, rowHeight);
        fileSelected.LEFT_ALIGN();
        textBoxes.add(fileSelected);

        Button close = (new CloseButton(x + width - columnWidth, y + 1, columnWidth, rowHeight - 1));
        close.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                close.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (close.isPressed())
                    close();
            }

            @Override
            public void onHover(int x, int y) {
                close.hover(x, y);
            }
        });
        buttons.add(close);

        Button open = (new ActiveButton("Open", x + width - 6*(columnWidth) + spacing, y + height - rowHeight + spacing, 3*(columnWidth - spacing), rowHeight - spacing*2));
        open.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                open.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (open.isPressed()) {
                    Panel.openFile();
                }
            }

            @Override
            public void onHover(int x, int y) {
                open.hover(x, y);
            }
        });
        buttons.add(open);

        Button cancel = (new ActiveButton("Cancel", x + width - 3*(columnWidth) + spacing, y + height - rowHeight + spacing, 3*(columnWidth - spacing), rowHeight - spacing*2));
        cancel.setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                cancel.isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (cancel.isPressed())
                    close();
            }

            @Override
            public void onHover(int x, int y) {
                cancel.hover(x, y);
            }
        });
        buttons.add(cancel);

        TextBox title = new TextBox("Open File", x, y, width, rowHeight);
        title.LEFT_ALIGN();
        textBoxes.add(title);


        ToolBar filesToolBar = new FileToolBar("Files:", x + columnWidth, y + rowHeight*2, columnWidth*14, rowHeight*8, 8, 1);
        toolBars.add(filesToolBar);

    }

}
