import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;

/**
 * CG2D
 * Class FinalAssignment
 * Created by Stefan Mermans on 22/03/2016.
 */
class MouseInputListener implements MouseListener{
    private boolean mousePressed;
    private Point2D mousePosition;
    private Physics physics;
    private ButtonPanel buttonPanel;

    /**
     * Constructor for MouseInputListener
     * @param physics       This object is used to interface with the physics world
     * @param buttonPanel   Is used to determine what events have to occur
     */
    MouseInputListener(Physics physics, ButtonPanel buttonPanel){
        mousePressed = false;
        mousePosition = new Point2D.Double(0,0);
        this.buttonPanel = buttonPanel;
        this.physics = physics;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mousePosition = new Point2D.Double(e.getX(),e.getY());
    }

    @Override
    /**
     * is Called when the mouse button is released and will create a shape based on the location
     */
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;

        if(buttonPanel.getShapeType2D() == ShapeType2D.RECTANGLE)
            createRect(e);
        else if(buttonPanel.getShapeType2D() == ShapeType2D.CIRCLE){
            createCircle(e);
        }

    }

    /* The following methods are empty, but they have to exist in order for this class to implement MouseListener*/
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * This method extends the mouseReleased method
     * and will create a circle based on the mouse location
     * @param e     The MouseEvent that will be used as the end point of the circle
     */
    private void createCircle(MouseEvent e){
        Point2D start = new Point2D.Double(getMousePosition().getX(),getMousePosition().getY())
                , end = new Point2D.Double(e.getX(),e.getY());

        double dx = end.getX() - start.getX();
        if(dx < 0){
            dx *= -1;
        }

        double dy = end.getY() - start.getY();
        if(dy < 0){
            dy *= -1;
        }

        double radius = Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));

        if(radius > 0){
            physics.createBody((int)(start.getX()),(int)(start.getY()),(float)radius,buttonPanel.getBodyType());
        }
    }

    /**
     * This method extends the mouseReleased method
     * and will create a rectangle based on the mouse location
     * @param e     The MouseEvent that will be used as the end point of the rectangle
     */
    private void createRect(MouseEvent e){
        Point2D start = new Point2D.Double(getMousePosition().getX(),getMousePosition().getY())
                , end = new Point2D.Double(e.getX(),e.getY());

        float width = (float)(end.getX() - start.getX());
        float height = (float)(end.getY() - start.getY());

        int startX, startY;
        startX = (int)(start.getX() + width/2);
        startY = (int)(start.getY() + height/2);

        if(height < 0){
            height *= -1;

            startY = (int)(start.getY() - height/2);
        }
        if(width < 0){
            width *= -1;

            startX = (int)(start.getX() - width/2);
        }

        if(width > 0 && height > 0){
            physics.createBody(startX,startY,width,height,buttonPanel.getBodyType());
        }
    }

    /**
     *  @return The most recent mouse position from before the mouseDragged method was called
     */
    Point2D getMousePosition() {
        return mousePosition;
    }

    /**
     * @return  true if the mouse is pressed, else false
     */
    boolean isMousePressed() {
        return mousePressed;
    }
}
