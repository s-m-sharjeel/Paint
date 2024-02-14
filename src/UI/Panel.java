package UI;

import UI.buttons.ActiveButton;
import UI.buttons.Button;
import UI.buttons.MenuButton;
import UI.buttons.ToggleButton;
import UI.others.Grid;
import UI.others.ToolTip;
import UI.toolbars.ToolBar;
import UI.windows.Window;
import UI.windows.*;
import shapes.Rectangle;
import shapes.Shape;
import shapes.*;

import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class Panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    private static final int panelX = 0;
    private static final int panelY = 0;
    private static final int panelWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int panelHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

    private static int time = 0;

    private static Window mainWindow;
    private static Window colorWindow;
    private static Window fileWindow;
    private static Window messageWindow;

    private static int drawingX;
    private static int drawingY;
    private static int drawingWidth;
    private static int drawingHeight;
    private Shape shape;
    private static LinkedList<String> files;
    private static LinkedList<LinkedList<Shape>> layer;
    private static LinkedList<LinkedList<Shape>> redoList;
    private static int layerSelected = -1;
    private static boolean drawing;
    private boolean control;
    private static int clicks;
    private static int shapeChoice;
    private int x1;
    private int y1;
    private static int colorCount = 0;
    private static int customCount = 0;

    private static int[][][] gradientColor;
    private static Point gradientCursor;
    private static int fade = 255;

    private static ToolTip toolTip;
    private static Grid grid;

    private static LinkedList<String> layerName;

    Panel(){
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBounds(0, 0, panelWidth, panelHeight);
        setFocusable(true);
        initializeAssets();

        int DELAY = 1;
        Timer timer = new Timer(DELAY, this);
        timer.start();

        addMouseListener( this );
        addMouseMotionListener(this);
        addKeyListener(this);
    }

    void initializeAssets(){

        layer = new LinkedList<>();
        redoList = new LinkedList<>();
        files = new LinkedList<>();

        getFilesFromFolder();

        mainWindow = new MainWindow(panelX, panelY, panelWidth, 30 + 140);
        fileWindow = new FileWindow(panelX + panelWidth/2 - 600/2, panelY + panelHeight/2 - 550/2, 600, 550);

        drawingX = panelX + 24;
        drawingY = panelY + mainWindow.getToolBars().get(0).getHeight() + mainWindow.getToolBars().get(1).getHeight() + 20 - 4;
        drawingWidth = mainWindow.getToolBars().get(1).getWidth() + mainWindow.getToolBars().get(2).getWidth() + mainWindow.getToolBars().get(3).getWidth() + mainWindow.getToolBars().get(4).getWidth() - 48;
        drawingHeight = panelHeight - mainWindow.getToolBars().get(0).getHeight() - mainWindow.getToolBars().get(1).getHeight() - 62 + 8;

        colorWindow = GradientWindow.getInstance();
        toolTip = ToolTip.getInstance();
        grid = Grid.getInstance();

        layerName = new LinkedList<>();
        List<String> list = Arrays.asList("Gold", "Iron", "Sapphire", "Lapis Lazuli", "Diamond", "Obsidian", "Emerald", "Amethyst", "Slime", "Granite", "Carrot", "Sand");
        layerName.addAll(list);

        // adds one layer by default
        addLayer();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        time+=3;

        // paints a boundary for the drawing region
        g.setColor(Color.white);
        g.fill3DRect(drawingX, drawingY, drawingWidth, drawingHeight, true);
        g.setColor(Color.lightGray);
        g.drawRect(drawingX, drawingY, drawingWidth, drawingHeight);

        drawShapes(g);
        grid.draw(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(24));
        g2.draw(new java.awt.Rectangle(drawingX - 12, drawingY - 12, drawingWidth + 24, drawingHeight + 24));
        g2.setStroke(new BasicStroke(0));

        mainWindow.paint(g);

        // paints a non-functional color gradient as an icon for the edit-color-button
        paintColorGradient(mainWindow.getToolBars().get(4).getButtons().get(32), 127, g);

        if (colorWindow.isOpen())
            colorWindow.paint(g);

        if (fileWindow.isOpen())
            fileWindow.paint(g);

        if (messageWindow != null)
            messageWindow.paint(g);

        toolTip.draw(g);

    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        // messageWindow opened
        if (messageWindow != null){
            messageWindow.onPress(x, y);
            return;
        }

        // colorWindow opened
        if (colorWindow.isOpen()){
            colorWindow.onPress(x, y);
            return;
        }

        // fileWindow opened
        if (fileWindow.isOpen()){
            fileWindow.onPress(x, y);
            return;
        }

        // file menu dropdown
        if (mainWindow.getToolBars().get(0).getButtons().get(0).isVisible()){
            for (int i = 0; i < 3; i++) {
                mainWindow.getToolBars().get(0).getButtons().get(0).getButtons().get(i).getListener().onPress(x, y);
            }
            mainWindow.getToolBars().get(0).getButtons().get(0).setVisible(false);
            return;
        }

        // edit menu dropdown
        if (mainWindow.getToolBars().get(0).getButtons().get(1).isVisible()) {
            for (int i = 0; i < 2; i++) {
                mainWindow.getToolBars().get(0).getButtons().get(1).getButtons().get(i).getListener().onPress(x, y);
            }
            mainWindow.getToolBars().get(0).getButtons().get(1).setVisible(false);
            return;
        }

        // stroke dropdown
        if (mainWindow.getToolBars().get(3).getButtons().get(0).isVisible()) {
            for (int i = 0; i < 7; i++) {
                mainWindow.getToolBars().get(3).getButtons().get(0).getButtons().get(i).getListener().onPress(x, y);
            }
            mainWindow.getToolBars().get(3).getButtons().get(0).setVisible(false);
            return;
        }

        mainWindow.onPress(x, y);

        // drawing shapes
        if (y > drawingY && x < drawingX + drawingWidth){

            if (SwingUtilities.isLeftMouseButton(e)) {

                if (shapeChoice > 0 && shapeChoice < 9) {

                    drawing = true;

                    // bezier curve
                    if (shapeChoice == 8){

                        if (clicks == 0) {

                            // get color from stroke color button
                            shape = new BezierCurve(mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                            layer.get(0).push(shape);
                            redoList.get(0).clear();

                            // get stroke value from stroke button
                            shape.setStroke(Integer.parseInt(mainWindow.getToolBars().get(3).getButtons().get(0).getText()));
                            shape.setPoint(x, y, 0);
                            shape.setPoint(x, y, 3);

                        }

                        // set first control point
                        if (clicks == 1)
                            shape.setPoint(x, y, 1);

                        // set second control point
                        if (clicks == 2)
                            shape.setPoint(x, y, 2);

                        clicks++;
                        return;

                    } else clicks = 0;

                    switch (shapeChoice) {
                        case 1 -> shape = new FreeDraw(mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 2 -> shape = new Circle(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 3 -> shape = new Rectangle(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 4 -> shape = new RightTriangle(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 5 -> shape = new EquilateralTriangle(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 6 -> shape = new Hexagon(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                        case 7 -> shape = new Pentagram(mainWindow.getToolBars().get(4).getButtons().get(1).getShape().getFillColor(), mainWindow.getToolBars().get(4).getButtons().get(0).getShape().getFillColor());
                    }

                    layer.get(0).push(shape);
                    redoList.get(0).clear();
                    shape.setStroke(Integer.parseInt(mainWindow.getToolBars().get(3).getButtons().get(0).getText()));

                    // initial point set
                    x1 = e.getX();
                    y1 = e.getY();
                    shape.setPoint(new Point(x1, y1));

                    if (shape instanceof FreeDraw)
                        shape.addPixel(x1, y1);

                    clicks++;

                } else messageWindow = MessageWindow.getInstance("Error", "Select a shape first!");

            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        if (messageWindow != null)
            return;

        if (SwingUtilities.isLeftMouseButton(e)){

            int x = e.getX();
            int y = e.getY();

            if (colorWindow.isOpen()){

                // mouse drag on color gradient
                colorWindow.getButtons().get(5).getListener().onPress(x, y);

                // mouse drag on fade gradient
                colorWindow.getButtons().get(6).getListener().onPress(x, y);

            }


            // mouse is dragged on drawing region when windows and dropdowns are not open
            if (drawing){

                // bezier curve
                if (shapeChoice == 8){

                    // final anchor point
                    if (clicks == 1)
                        shape.setPoint(x, y, 3);

                    // first control point
                    if (clicks == 2)
                        shape.setPoint(x, y, 1);

                    // second control point
                    if (clicks == 3)
                        shape.setPoint(x, y, 2);

                }

                int width = Math.abs(x - x1);
                int height = Math.abs(y - y1);

                double base = x - shape.getX();
                double perp = shape.getY() - y;
                int initAngle = (int)(Math.toDegrees(Math.atan2(perp, base)));
                shape.setInitAngle(initAngle);

                // pythagoras theorem to find radius of circle
                int size = (int) (Math.sqrt((width * width) + (height * height)));

                // free drawing
                if (shapeChoice == 1) {
                    shape.addPixel(e.getX(), e.getY());
                    return;
                }

                // circle/equilateral-triangle/pentagram
                if (shapeChoice == 2 || shapeChoice == 5 || shapeChoice == 7) {
                    shape.setSize(size);
                    return;
                }

                // finds top left depending on which quadrant the mouse is dragged into w.r.t the initial point
                Point topLeft = new Point(x1, y1);
                if (y < y1 && x < x1)
                    topLeft = new Point(x, y);
                else if (y < y1)
                    topLeft = new Point(x1, y);
                else if (x < x1)
                    topLeft = new Point(x, y1);

                shape.setPoint(topLeft);
                shape.setWidth(width);
                shape.setHeight(height);

            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (messageWindow != null)
            messageWindow.onRelease();

        if (colorWindow.isOpen())
            colorWindow.onRelease();

        if (fileWindow.isOpen())
            fileWindow.onRelease();

        mainWindow.onRelease();

        //panel
        if(SwingUtilities.isLeftMouseButton(e)){

            // shape completed
            if (shapeChoice > 0 && shapeChoice < 9) {

                if (shapeChoice == 8 && clicks < 3) {
                    drawing = false;
                    return;
                }

                shapeChoice = 0;
                clicks = 0;
                shape = null;
                drawing = false;

            }

        }

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        int x = e.getX();
        int y = e.getY();

        // messageWindow opened
        if (messageWindow != null){
            messageWindow.onHover(x, y);
            setToolTip();
            return;
        }

        // colorWindow opened
        if (colorWindow.isOpen()){
            colorWindow.onHover(x, y);
            setToolTip();
            return;
        }

        // fileWindow opened
        if (fileWindow.isOpen()){
            fileWindow.onHover(x, y);
            setToolTip();
            return;
        }

        mainWindow.onHover(x, y);
        setToolTip();

    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        // returns if a message window is open
        if (messageWindow != null)
            return;

        // returns if key is pressed while shape is being drawn
        if(drawing) {
            messageWindow = MessageWindow.getInstance("Error", "Complete drawing the shape first!");
            return;
        }

        int keyCode = e.getKeyCode();

        if (keyCode == 17) {
            control = true;
            return;
        }

        switch (keyCode) {

            // layer++ (NumPad + key)
            case KeyEvent.VK_ADD -> addLayer();

            // layer-- (NumPad - key)
            case KeyEvent.VK_SUBTRACT -> removeLayer(layerSelected);

            // raise layer (up arrow key)
            case KeyEvent.VK_UP -> raiseLayer(layerSelected);

            // lower layer (down arrow key)
            case KeyEvent.VK_DOWN -> lowerLayer(layerSelected);

            // new page
            case 'N' -> {
                if (control)
                    renew();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");
            }

            // opens file window
            case 'F' -> {
                if (!colorWindow.isOpen() && control)
                    fileWindow.open();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");
            }

            case 'S' -> {
                if (control)
                    saveFile();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");
            }

            case 'Z' -> {
                if (control)
                    undo();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");

            }

            case 'Y' -> {
                if (control)
                    redo();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");
            }

            // opens color gradient window
            case 'C' -> {
                if (!fileWindow.isOpen() && control)
                    colorWindow.open();
                else messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");
            }

            // esc key closes any opened window and the program otherwise
            case KeyEvent.VK_ESCAPE -> {
                if (messageWindow != null) {
                    messageWindow.close();
                } else if (colorWindow.isOpen()) {
                    colorWindow.close();
                } else if (fileWindow.isOpen()) {
                    fileWindow.close();
                } else {
                    confirmExit();
                }
            }

            default -> messageWindow = MessageWindow.getInstance("Error", "Invalid Key Pressed!");

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 17)
            control = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // sentinel value for layerSelected
        layerSelected = -1;
        for (int i = 4; i < mainWindow.getToolBars().get(5).getButtons().size(); i++) {
            if (mainWindow.getToolBars().get(5).getButtons().get(i).isPressed()){
                layerSelected = i - 4;
            }
        }

        // sentinel value for shapeChoice
        shapeChoice = -1;
        for (int i = 0; i < mainWindow.getToolBars().get(1).getButtons().size(); i++) {
            if (mainWindow.getToolBars().get(1).getButtons().get(i).isPressed()){
                shapeChoice = i + 1;
            }
        }

        Toolkit.getDefaultToolkit().sync();
        repaint();
    }

    /**
     * undoes the shape last drawn
     */
    public static void undo(){

        // undo
        if(drawing){
            messageWindow = MessageWindow.getInstance("Error", "Complete drawing the shape first!");
            return;
        }

        if (layerSelected == -1){
            messageWindow = MessageWindow.getInstance("Error", "Please select a layer!");
            return;
        }

        if (layer.get(layerSelected).isEmpty()) {
            messageWindow = MessageWindow.getInstance("Error", "Undo List Empty!");
            return;
        }

        redoList.get(layerSelected).push(layer.get(layerSelected).pop());

    }

    /**
     * redoes the shape last undone
     */
    public static void redo(){

        // redo
        if(drawing){
            messageWindow = MessageWindow.getInstance("Error", "Complete drawing the shape first!");
            return;
        }

        if (layerSelected == -1){
            messageWindow = MessageWindow.getInstance("Error", "Please select a layer!");
            return;
        }

        if (redoList.get(layerSelected).isEmpty()) {
            messageWindow = MessageWindow.getInstance("Error", "Redo List Empty!");
            return;
        }

        layer.get(layerSelected).push(redoList.get(layerSelected).pop());

    }

    private void drawShapes(Graphics g){

        for (int i = layer.size() - 1; i >= 0; i--) {
            for (int j = layer.get(i).size() - 1; j >= 0 ; j--)
                layer.get(i).get(j).draw(g);
        }

    }

    /**
     * gets the name of all the files in the files folder and stores them in the files list
     */
    private void getFilesFromFolder(){

        File folder = new File("./src/files");
        String[] fileName = folder.list();

        assert fileName != null;
        files.addAll(Arrays.asList(fileName));

    }

    /**
     * saves file with the current time stamp
     */
    public static void saveFile(){

        if (files.size() < 8) {

            try {

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy ~ HH mm ss");
                String timeStamp = df.format(new Date());

                String filePath = "./src/files/" + timeStamp + ".txt";

                File file = new File(filePath);

                if (file.createNewFile()){

                    storeShapes(filePath);

                    files.add(timeStamp + ".txt");

                    Button newFile = new ActiveButton(timeStamp + ".txt");
                    newFile.setListener(new Listener() {
                        @Override
                        public void onPress(int x, int y) {
                            newFile.isClicked(x, y);
                        }

                        @Override
                        public void onRelease() {
                            if (newFile.isPressed())
                                fileWindow.getTextBoxes().get(0).setText("File name: " + newFile.getText());
                        }

                        @Override
                        public void onHover(int x, int y) {
                            newFile.hover(x, y);
                        }
                    });

                    fileWindow.getToolBars().get(0).addButton(newFile);
                    messageWindow = MessageWindow.getInstance("Message", "File saved as \"" + timeStamp + ".txt\"!");

                } else messageWindow = MessageWindow.getInstance("Error", "File already exists!");

            } catch (IOException e){

                messageWindow = MessageWindow.getInstance("Error", "File not found!");
                e.printStackTrace();

            }

        }else messageWindow = MessageWindow.getInstance("Error", "Save slots full!");

    }

    /**
     * opens the selected file in the fileWindow
     */
    public static void openFile(){
        if (fileWindow.getTextBoxes().get(0).getText().substring(11).length() > 0) {
            getShapes("./src/files/" + fileWindow.getTextBoxes().get(0).getText().substring(11));
            fileWindow.close();
        } else messageWindow = MessageWindow.getInstance("Error", "No file selected!");
    }

    /**
     * creates a list of layers containing shapes from the text file
     */
    public static void getShapes(String filePath){

        try {

            File file = new File(filePath);
            Scanner input = new Scanner(file);

            Shape shape = null;
            String data;
            String[] temp;

            renew();

            int i = -1;

            while (input.hasNextLine()) {

                String shapeName = input.nextLine();

                if (shapeName.startsWith("layer")) {
                    if (i != -1)
                        addLayer();
                    input.nextLine();
                    i++;
                    continue;
                }

                data = input.nextLine();
                temp = data.split(",");
                Color fillColor = new Color(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]));

                switch (shapeName) {

                    case "Line" -> shape = new FreeDraw(fillColor);
                    case "Circle" -> shape = new Circle(fillColor);
                    case "Rectangle" -> shape = new Rectangle(fillColor);
                    case "Equilateral Triangle" -> shape = new EquilateralTriangle(fillColor);
                    case "Right Triangle" -> shape = new RightTriangle(fillColor);
                    case "Hexagon" -> shape = new Hexagon(fillColor);
                    case "Pentagram" -> shape = new Pentagram(fillColor);
                    case "Bezier" -> shape = new BezierCurve(fillColor);

                }

                if (shapeName.equals("Line")){
                    shape.setStroke(Integer.parseInt(input.nextLine()));
                    data = input.nextLine();
                    while (data.length() > 0){
                        temp = data.split(",");
                        shape.addPixel(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                        data = input.nextLine();
                    }

                } else if (shapeName.equals("Bezier")){
                    shape.setStroke(Integer.parseInt(input.nextLine()));
                    for (int j = 0; j < 4; j++) {
                        data = input.nextLine();
                        if (!data.equals("null")) {
                            temp = data.split(",");
                            shape.setPoint(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), j);
                        }
                    }

                } else {

                    data = input.nextLine();
                    temp = data.split(",");
                    assert shape != null;
                    shape.setStrokeColor(new Color(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[2])));
                    shape.setStroke(Integer.parseInt(input.nextLine()));
                    data = input.nextLine();
                    temp = data.split(",");
                    shape.setPoint(new Point(Integer.parseInt(temp[0]), Integer.parseInt(temp[1])));

                    if (shapeName.equals("Circle") || shapeName.equals("Pentagram") || shapeName.equals("Equilateral Triangle")) {
                        shape.setSize(Integer.parseInt(input.nextLine()));
                        if (!shapeName.equals("Circle"))
                            shape.setInitAngle(Integer.parseInt(input.nextLine()));
                    } else {
                        shape.setWidth(Integer.parseInt(input.nextLine()));
                        shape.setHeight(Integer.parseInt(input.nextLine()));
                    }

                }

                layer.get(i).add(shape);

                if (input.hasNextLine() && !shapeName.equals("Line"))
                    input.nextLine();

            }

            input.close();

        } catch (FileNotFoundException e) {
            messageWindow = MessageWindow.getInstance("Error", "File not found!");
            e.printStackTrace();
        }

    }

    /**
     * stores all the shapes in a text file
     */
    private static void storeShapes(String filePath){

        try{

            FileWriter fWriter = new FileWriter(filePath, false);
            StringBuilder finalData = new StringBuilder();

            // arranges the states such that the last shape drawn is in the end
            for (int i = 0; i < layer.size(); i++) {
                finalData.append("layer ").append(i).append("\n\n");
                for (Shape shape : layer.get(i)) {
                    finalData.append(shape.toString()).append('\n');
                }
            }


            fWriter.write(finalData.toString());
            fWriter.close();

        } catch (IOException ex){
            messageWindow = MessageWindow.getInstance("Error", "File not found!");
            ex.printStackTrace();
        }

    }

    /**
     * paints a color gradient on the button
     */
    public static void paintColorGradient(Button button, int fade, Graphics g) {

        int x;
        int y;
        int width;
        int height;

        if (button.getShape() != null) {
            x = button.getShape().getX();
            y = button.getShape().getY();
            width = button.getShape().getWidth();
            height = button.getShape().getHeight();
        } else {
            x = button.getX();
            y = button.getY();
            width = button.getWidth();
            height = button.getHeight();
        }

        gradientColor = new int[width][height][3];
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(1));

        for (int j = 0; j < height; j++) {

            for (int i = 0; i < width; i++) {

                double red = 255.0;
                double green = 255.0;
                double blue = 255.0;

                int interval = width / 6;
                double step = 255.0 / interval;

                if (i < interval) {
                    blue = 0;
                    green = (int) (i * step);
                } else if (i < interval * 2) {
                    blue = 0;
                    red = 255 - (int) ((i - interval) * step);
                } else if (i < interval * 3) {
                    red = 0;
                    blue = (int) ((i - interval * 2) * step);
                } else if (i < interval * 4) {
                    red = 0;
                    green = 255 - (int) ((i - interval * 3) * step);
                } else if (i < interval * 5) {
                    green = 0;
                    red = (int) ((i - interval * 4) * step);
                } else if (i < interval * 6){
                    green = 0;
                    blue = 255 - (int) ((i - interval * 5) * step);
                }


                double changeRed = Math.abs((red - fade)/height);
                if (red >= fade)
                    red = (int)(red - (changeRed*j));
                else red = (int)(red + (changeRed*j));

                double changeGreen = Math.abs((green - fade)/height);
                if (green >= fade)
                    green = (int)(green - (changeGreen*j));
                else green = (int)(green + (changeGreen*j));

                double changeBlue = Math.abs((blue - fade)/height);
                if (blue >= fade)
                    blue = (int)(blue - (changeBlue*j));
                else blue = (int)(blue + (changeBlue*j));

                g2.setColor(new Color((int)red, (int)green, (int)blue));
                g2.draw(new Rectangle2D.Double(x + i, y + j, 1, 1));
                g2.fill(new Rectangle2D.Double(x + i, y + j, 1, 1));

                gradientColor[i][j][0] = (int)red;
                gradientColor[i][j][1] = (int)green;
                gradientColor[i][j][2] = (int)blue;

            }
        }

        // drawing cursor on gradient
        if (colorWindow.isOpen() && gradientCursor != null){
            g2.setStroke(new BasicStroke(3));
            g2.setColor(Color.red);
            g2.drawLine(gradientCursor.x, gradientCursor.y - 4, gradientCursor.x, gradientCursor.y - 10);
            g2.drawLine(gradientCursor.x, gradientCursor.y + 4, gradientCursor.x, gradientCursor.y + 10);
            g2.drawLine(gradientCursor.x - 4, gradientCursor.y, gradientCursor.x - 10, gradientCursor.y);
            g2.drawLine(gradientCursor.x + 4, gradientCursor.y, gradientCursor.x + 10, gradientCursor.y);
        }

        g2.setStroke(new BasicStroke(1));

    }

    /**
     * sets the color of first button to the color of the second button
     * @param b1 is the first button
     * @param b2 is the second button
     */
    public static void setColorButton(Button b1, Button b2){
        b1.setTip(b2.getTip());
        b1.getShape().setFillColor(b2.getShape().getFillColor());
    }

    /**
     * adds a layer at the bottom of all layers
     */
    public static void addLayer(){

        // 10 layer buttons + 4 navigation control buttons max
        if (mainWindow.getToolBars().get(5).getButtons().size() < 14) {

            // every layer has its own redo list on corresponding indices
            layer.add(new LinkedList<>());

            redoList.add(new LinkedList<>());

            // new button created with a random name assigned from the layer names list
            Button newButton = (new ToggleButton(layerName.remove((int)(Math.random()*layerName.size()))));
            newButton.setListener(new Listener() {
                @Override
                public void onPress(int x, int y) {
                    newButton.isClicked(x, y);
                }

                @Override
                public void onRelease() {}

                @Override
                public void onHover(int x, int y) {
                    newButton.hover(x, y);
                }
            });

            mainWindow.getToolBars().get(5).addButton(newButton, mainWindow.getToolBars().get(5).getButtons().size());

        } else messageWindow = MessageWindow.getInstance("Error", "Can not add more than 10 layers!");
    }

    /**
     * removes the layer and its button at index i
     */
    public static void removeLayer(int i){

        if (i < 0){
            messageWindow = MessageWindow.getInstance("Error", "Please select a layer!");
            return;
        }

        if (layer.size() == 1) {
            messageWindow = MessageWindow.getInstance("Error", "You can not remove the last layer!");
            return;
        }

        layerName.add(mainWindow.getToolBars().get(5).getButtons().get(i + 4).getText());
        mainWindow.getToolBars().get(5).removeButton(i + 4);
        // subtract 4 since 4 control buttons
        layer.remove(i);
        redoList.remove(i);
    }

    /**
     * raises the layer and its button at index i
     */
    public static void raiseLayer(int i){
        if (i > 0){
            mainWindow.getToolBars().get(5).addButton(mainWindow.getToolBars().get(5).getButtons().remove(i + 4), i + 4 - 1);
            Collections.swap(layer, i, i - 1);
            Collections.swap(redoList, i, i- 1);
        } else if (i < 0){
            messageWindow = MessageWindow.getInstance("Error", "Please select a layer!");
        } else {
            messageWindow = MessageWindow.getInstance("Error", "Layer can not be raised further!");
        }
    }

    /**
     * lowers layer and its button at index i
     */
    public static void lowerLayer(int i){
        if (i < 0){
            messageWindow = MessageWindow.getInstance("Error", "Please select a layer!");
            return;
        }
        if (i + 4 < mainWindow.getToolBars().get(5).getButtons().size() - 1){
            raiseLayer(i + 1);
        } else messageWindow = MessageWindow.getInstance("Error", "Layer can not be lowered further!");
    }

    /**
     * refreshes the page by clearing all the shapes and layers
     */
    public static void renew(){

        // saving all the layer names before deleting them
        for (int i = 4; i < mainWindow.getToolBars().get(5).getButtons().size(); i++)
            layerName.add(mainWindow.getToolBars().get(5).getButtons().get(i).getText());

        // deleting layers
        if (mainWindow.getToolBars().get(5).getButtons().size() > 4)
            mainWindow.getToolBars().get(5).getButtons().subList(4, mainWindow.getToolBars().get(5).getButtons().size()).clear();

        layer.clear();
        redoList.clear();
        addLayer();
    }

    /**
     * sets tooltip of the button hovered
     */
    private static void setToolTip(){

        if (messageWindow != null) {

            for (Button b : messageWindow.getButtons()) {
                if (b.isHovered()) {
                    toolTip.setButton(b);
                    return;
                }
            }
        }

        if (fileWindow.isOpen()){

            for (Button b : fileWindow.getButtons()) {
                if (b.isHovered()) {
                    toolTip.setButton(b);
                    return;
                }
            }

            for (ToolBar t : fileWindow.getToolBars()) {
                for (Button b : t.getButtons()) {
                    if (b.isHovered()) {
                        toolTip.setButton(b);
                        return;
                    }
                }
            }

        }

        if (colorWindow.isOpen()){

            for (Button b : colorWindow.getButtons()) {
                if (b.isHovered()) {
                    toolTip.setButton(b);
                    return;
                }
            }

            for (ToolBar t : colorWindow.getToolBars()) {
                for (Button b : t.getButtons()) {
                    if (b.isHovered()) {
                        toolTip.setButton(b);
                        return;
                    }
                }
            }

        }

        for (Button b : mainWindow.getButtons()) {
            if (b.isHovered()) {
                toolTip.setButton(b);
                return;
            }
        }

        for (ToolBar t : mainWindow.getToolBars()) {
            for (Button b : t.getButtons()) {
                if (b instanceof MenuButton){
                    for (Button mb : b.getButtons()) {
                        if (mb.isHovered()){
                            toolTip.setButton(mb);
                            return;
                        }
                    }
                }
                if (b.isHovered()) {
                    toolTip.setButton(b);
                    return;
                }
            }
        }

        toolTip.setButton(null);

    }

    public static int getTime() {
        return time;
    }

    public static Window getMainWindow() {
        return mainWindow;
    }

    public static Window getColorWindow() {
        return colorWindow;
    }

    public static Window getFileWindow() {
        return fileWindow;
    }

    public static int getPanelX() {
        return panelX;
    }

    public static int getPanelY() {
        return panelY;
    }

    public static int getPanelWidth() {
        return panelWidth;
    }

    public static int getPanelHeight() {
        return panelHeight;
    }

    public static int getDrawingX() {
        return drawingX;
    }

    public static int getDrawingY() {
        return drawingY;
    }

    public static int getDrawingWidth() {
        return drawingWidth;
    }

    public static int getDrawingHeight() {
        return drawingHeight;
    }

    public static LinkedList<String> getFiles() {
        return files;
    }

    public static int getLayerSelected() {
        return layerSelected;
    }

    public static int getColorCount() {
        return colorCount;
    }

    public static int getCustomCount() {
        return customCount;
    }

    public static int[][][] getGradientColor() {
        return gradientColor;
    }

    public static int getFade() {
        return fade;
    }

    public static Point getGradientCursor() {
        return gradientCursor;
    }

    public static void setColorCount(int colorCount) {
        Panel.colorCount = colorCount;
    }

    public static void setCustomCount(int customCount) {
        Panel.customCount = customCount;
    }

    public static void setFade(int fade) {
        Panel.fade = fade;
    }

    public static void setGridSize(int size) {
        grid.setSize(size);
    }

    public static void setMessageWindow(Window messageWindow) {
        Panel.messageWindow = messageWindow;
    }

    public static void setShapeChoice(int shapeChoice) {
        Panel.shapeChoice = shapeChoice;
    }

    public static void setClicks(int clicks) {
        Panel.clicks = clicks;
    }

    public static void setGradientCursor(Point gradientCursor) {
        Panel.gradientCursor = gradientCursor;
    }

    /**
     * displays a window to confirm exit when close window button pressed
     */
    public static void confirmExit(){
        messageWindow = MessageWindow.getInstance("Confirm Exit", "Are you sure you want to exit?");
        messageWindow.getButtons().get(1).setText("Exit");
        messageWindow.getButtons().get(1).setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                messageWindow.getButtons().get(1).isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (messageWindow.getButtons().get(1).isPressed()) {
                    System.exit(0);
                }
            }

            @Override
            public void onHover(int x, int y) {
                messageWindow.getButtons().get(1).hover(x, y);
            }
        });

        messageWindow.getButtons().get(0).setListener(new Listener() {
            @Override
            public void onPress(int x, int y) {
                messageWindow.getButtons().get(0).isClicked(x, y);
            }

            @Override
            public void onRelease() {
                if (messageWindow.getButtons().get(0).isPressed()) {

                    messageWindow.getButtons().get(1).setText("OK");
                    messageWindow.getButtons().get(1).setListener(new Listener() {
                        @Override
                        public void onPress(int x, int y) {
                            messageWindow.getButtons().get(1).isClicked(x, y);
                        }

                        @Override
                        public void onRelease() {
                            if (messageWindow != null && messageWindow.getButtons().get(1).isPressed()) {
                                messageWindow.close();
                            }
                        }

                        @Override
                        public void onHover(int x, int y) {
                            messageWindow.getButtons().get(1).hover(x, y);
                        }
                    });

                    messageWindow.close();
                }
            }

            @Override
            public void onHover(int x, int y) {
                messageWindow.getButtons().get(0).hover(x, y);
            }
        });

    }

}
