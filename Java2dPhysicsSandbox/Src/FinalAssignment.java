import javax.swing.*;
import java.awt.*;

/**
 * CG2D
 * Class FinalAssignment
 * Created by Stefan Mermans on 22/03/2016.
 */
class FinalAssignment {
    private ShapePanel shapePanel;
    private static final int UPDATE_DELAY = 1000/120;

    /**
     * Constructor for FinalAssignment
     */
    private FinalAssignment(){

        // Setting the look and feel to the systems look and feel, because the default java look and feel is ugly
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException
                e) {
            e.printStackTrace();
        }

        initFramePanel();
        initUpdateTimers();
    }

    /**
     * Creating a timer to update the frame every UPDATE_DELAY
     */
    private void initUpdateTimers(){
        Timer updateTimer = new Timer(UPDATE_DELAY, e -> shapePanel.update());
        updateTimer.start();
    }

    /**
     * Initialize the frame and the panel on which everything will be drawn
     */
    private void initFramePanel(){
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(10);

        JFrame shapeFrame = new JFrame();
        shapeFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        shapeFrame.setContentPane(splitPane);
        shapeFrame.setPreferredSize(new Dimension(1280,720));
        shapeFrame.setVisible(true);
        shapeFrame.pack();

        ButtonPanel buttonPanel = new ButtonPanel();
        shapePanel = new ShapePanel(buttonPanel);
        shapePanel.setPreferredSize(new Dimension(1280,550));

        splitPane.add(shapePanel);
        splitPane.add(buttonPanel);
    }

    public static void main(String[] args){
        new FinalAssignment();
    }
}
