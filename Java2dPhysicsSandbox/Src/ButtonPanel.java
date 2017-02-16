import org.jbox2d.dynamics.BodyType;

import javax.swing.*;
import java.awt.*;

/**
 * CG2D
 * Class ButtonPanel
 * Created by Stefan Mermans on 23/03/2016.
 */
class ButtonPanel extends JPanel{
    private BodyType bodyType;
    private ShapeType2D shapeType2D;
    private boolean clear;  // This variable determines wither the clear button had been pressed

    ButtonPanel(){
        bodyType = BodyType.DYNAMIC;
        shapeType2D = ShapeType2D.RECTANGLE;
        clear = false;
        initComponents();
    }

    /**
     * This method creates all the components on the panel
     */
    private void initComponents(){
        setLayout(new FlowLayout());
        setBackground(Color.DARK_GRAY);

        JButton staticButton = new JButton("Static");
        staticButton.addActionListener(e -> bodyType = BodyType.STATIC);
        staticButton.setBackground(Color.CYAN);
        add(staticButton);

        JButton dynamicButton = new JButton("Dynamic");
        dynamicButton.addActionListener(e -> bodyType = BodyType.DYNAMIC);
        dynamicButton.setBackground(Color.CYAN);
        add(dynamicButton);

        JButton rectangleButton = new JButton("Rectangle");
        rectangleButton.addActionListener(e -> shapeType2D = ShapeType2D.RECTANGLE);
        rectangleButton.setBackground(Color.ORANGE);
        add(rectangleButton);

        JButton circleButton = new JButton("Circle");
        circleButton.addActionListener(e -> shapeType2D = ShapeType2D.CIRCLE);
        circleButton.setBackground(Color.ORANGE);
        add(circleButton);

        JButton clearButton = new JButton("ClearALL");
        clearButton.addActionListener(e -> clear = true);
        clearButton.setBackground(Color.RED);
        add(clearButton);
    }

    boolean isClear() {
        return clear;
    }

    void setClear(boolean clear) {
        this.clear = clear;
    }

    BodyType getBodyType() {
        return bodyType;
    }

    ShapeType2D getShapeType2D(){
        return shapeType2D;
    }
}
