import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 * CG2D
 * Class Frame
 * Created by Stefan Mermans on 22/03/2016.
 */
class ShapePanel extends JPanel{
    private Physics physics;
    private MouseInputListener mouseInputListener;
    private MyMouseMotionListener myMouseMotionListener;
    private ButtonPanel buttonPanel;
    private long time;
    private long worldUpdateTime;

    ShapePanel(ButtonPanel buttonPanel){
        physics = new Physics();
        mouseInputListener = new MouseInputListener(physics, buttonPanel);
        myMouseMotionListener = new MyMouseMotionListener();
        time = System.currentTimeMillis();
        worldUpdateTime = System.nanoTime();

        this.buttonPanel = buttonPanel;
        addMouseListener(mouseInputListener);
        addMouseMotionListener(myMouseMotionListener);
    }

    /**
     * We know what this does...
     * @param graphics  The graphics used to draw
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D)graphics;

        // Antialiasing, CAUSES A LOT OF LAG!
//        RenderingHints antiAliasingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics2D.addRenderingHints(antiAliasingHints);

        graphics2D.setColor(Color.DARK_GRAY);
        graphics2D.fillRect(0,0,getWidth(),getHeight());

        drawBodies(graphics2D);
        drawInfoText(graphics2D);
        drawInProgress(graphics2D);

//        Messing with the deltaTime
//        physics.getWorld().step(0.0166667f, 8, 3); // Progressing the physics world
    }

    /**
     * Draw the text to display info on the screen
     * @param graphics2D    The 2D graphics used to draw
     */
    private void drawInfoText(Graphics2D graphics2D){
        String mousePositionX =
                "MouseX: " +
                myMouseMotionListener.getMousePosition().getX();
        String mousePositionY =
                "MouseY: " +
                myMouseMotionListener.getMousePosition().getY();
        String bodyTypeString =
                "BodyType: " +
                buttonPanel.getBodyType();
        String shapeTypeString =
                "ShapeType: " +
                buttonPanel.getShapeType2D();
        String fpsString =
                "Fps: " +
                (1000 / (System.currentTimeMillis() - time));
        time = System.currentTimeMillis();

        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(fpsString,30,40);
        graphics2D.drawString(mousePositionX,30,60);
        graphics2D.drawString(mousePositionY,30,80);
        graphics2D.drawString(shapeTypeString,30,100);
        graphics2D.drawString(bodyTypeString,30,120);
    }

    /**
     * This method will draw the shape while the mouse button is still being held down.
     * @param graphics2D    The 2D graphics used to draw
     */
    private void drawInProgress(Graphics2D graphics2D){
        if(mouseInputListener.isMousePressed()){
            int startX, startY, endX, endY;

            startX = (int)(mouseInputListener.getMousePosition().getX());
            startY = (int)(mouseInputListener.getMousePosition().getY());
            endX = (int)(myMouseMotionListener.getMousePosition().getX());
            endY = (int)(myMouseMotionListener.getMousePosition().getY());

            if(buttonPanel.getShapeType2D() == ShapeType2D.RECTANGLE) {
                int originX, originY;
                int width, height;

                width = endX - startX;
                height = endY - startY;
                originX = startX;
                originY = startY;

                if(height < 0){
                    height *= -1;

                    originY -= height;
                }
                if(width < 0){
                    width *= -1;
                    originX -= width;
                }

                graphics2D.setColor(Color.CYAN);
                graphics2D.drawRect(originX,originY,width,height);
            } else if(buttonPanel.getShapeType2D() == ShapeType2D.CIRCLE){
                int dx = endX - startX, dy = endY - startY;
                if(dx < 0){
                    dx *= -1;
                }
                if(dy < 0){
                    dy *= -1;
                }

                int radius = (int)(Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2)));

                graphics2D.setColor(Color.CYAN);
                graphics2D.drawOval(startX - radius,startY - radius,radius*2,radius*2);
            }
        }
    }

    /**
     * This method draws all of the bodies
     * @param graphics2D    The 2D graphics used to draw
     */
    private void drawBodies(Graphics2D graphics2D){
        for(PhysicsBody physicsBody: physics.getBodies()) {

            // Creating a transformation for the body
            AffineTransform affineTransform = new AffineTransform();

            java.awt.Shape shape;

            if(physicsBody.getShapeType2D() != ShapeType2D.CIRCLE) {
                PolygonShape polygonShape = (PolygonShape) physicsBody.getBody().getFixtureList().getShape();
                shape = createPolygon(polygonShape, affineTransform, physicsBody);
            } else {
                shape = createCircle(physicsBody.getBody().getFixtureList().getShape().m_radius, physicsBody.getBody().getPosition(), affineTransform);
            }

            // Drawing the polygon
            graphics2D.setColor(Color.GRAY);
            graphics2D.fill(affineTransform.createTransformedShape(shape));

            graphics2D.setColor(Color.BLACK);
            graphics2D.draw(affineTransform.createTransformedShape(shape));
        }
    }

    /**
     * This method creates a circle based on the position of the physics circle
     * @param radius            The radius of the circle
     * @param position          The center point position of the circle
     * @param affineTransform   The transformation of the circle
     * @return                  THe created circle as an awt shape
     */
    private java.awt.Shape createCircle(float radius, Vec2 position, AffineTransform affineTransform){
        affineTransform.translate(position.x - radius, position.y - radius);

        return new Ellipse2D.Double(0,0,radius*2,radius*2);
    }

    /**
     * This method creates a polygon bases on the coordinates of the physics polygon
     * @param polygonShape      The PolygonShape from the physics body
     * @param affineTransform   the transformation of the polygon
     * @param physicsBody       The PhysicsBody object
     * @return                  The created polygon as an awt shape
     */
    private java.awt.Shape createPolygon(PolygonShape polygonShape, AffineTransform affineTransform, PhysicsBody physicsBody){
        affineTransform.translate(physicsBody.getBody().getPosition().x, physicsBody.getBody().getPosition().y);
        affineTransform.rotate(physicsBody.getBody().getAngle());

        // Creating a polygon out of the vertices
        int size = polygonShape.getVertexCount();
        int[] xPoints = new int[size];
        int[] yPoints = new int[size];
        for (int i = 0; i < size; i++) {
            xPoints[i] = (int)(polygonShape.getVertex(i).x);
            yPoints[i] = (int)(polygonShape.getVertex(i).y);
        }

        return new Polygon(xPoints, yPoints, size);
    }

    /**
     * Update the simulation and the graphics
     */
     void update(){
        // clear the bodies ArrayList and create a new world if the clear button has been pressed
        if(buttonPanel.isClear()){
            physics.getBodies().clear();
            physics.setWorld(new World(Physics.GRAVITY_VECTOR));
            buttonPanel.setClear(false);
        }

         // progressing the world based on the deltaTime in nanoseconds, The physics engine needs it in seconds so it is multiplied by 1e-9
         physics.getWorld().step((System.nanoTime() - worldUpdateTime)*1e-9f, 8, 3);
         worldUpdateTime = System.nanoTime();

        repaint();
    }
}
