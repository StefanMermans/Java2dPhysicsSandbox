import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;

/**
 * CG2D
 * Class MyMouseMotionListener
 * Created by Stefan Mermans on 23/03/2016.
 */
class MyMouseMotionListener implements MouseMotionListener{
    private Point2D mousePosition;

    /**
     * Constructor for MyMouseMotionListener
     */
    MyMouseMotionListener(){
        mousePosition = new Point2D.Double(0,0);
    }

    @Override
    /**
     * This method is called when the mouse button is clicked and the mouse has moved
     * this method is used to update the position of the mouse.
     */
    public void mouseDragged(MouseEvent e) {
        mousePosition.setLocation(e.getX(),e.getY());
    }

    @Override
    /**
     * THis method is called when the mouse button is NOT clicked, but the mouse has moved
     * this method is used to update the position of the mouse.
     */
    public void mouseMoved(MouseEvent e) {
        mousePosition.setLocation(e.getX(),e.getY());
    }

    Point2D getMousePosition() {
        return mousePosition;
    }
}
