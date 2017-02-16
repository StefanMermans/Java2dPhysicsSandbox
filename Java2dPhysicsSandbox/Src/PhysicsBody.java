import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.*;

import java.awt.geom.Rectangle2D;

/**
 * CG2D
 * Class PhysicsBody
 * Created by Stefan Mermans on 23/03/2016.
 */
class PhysicsBody {
    private BodyDef bodyDef;
    private Body body;
    private ShapeType2D shapeType2D;

    /**
     * Constructor for a circular PhysicsBody
     * @param x             starting X position for the circle From the center of the body
     * @param y             starting y position for the circle From the center of the body
     * @param radius        The radius of the circle
     * @param bodyType      The bodyType the circle will use
     * @param world         The world in which the body will be placed
     */
    PhysicsBody(int x, int y, float radius, BodyType bodyType, World world){
        shapeType2D = ShapeType2D.CIRCLE;

        bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x,y);

        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = radius;

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circleShape;
        fixtureDef.density = 100 * (float)(radius*radius*Math.PI);
        fixtureDef.friction = 5.0f;
        body.createFixture(fixtureDef);
    }


    /**
     * Constructor for a rectangular PhysicsBody
     * @param x             starting X position for the rectangle From the center of the body
     * @param y             starting y position for the rectangle From the center of the body
     * @param rectangle2D   The rectangle to be used
     * @param bodyType      The bodyType the rectangle will use
     * @param world         The world in which the body will be placed
     */
    PhysicsBody(int x, int y, Rectangle2D rectangle2D, BodyType bodyType, World world){
        shapeType2D = ShapeType2D.RECTANGLE;

        bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(x,y);

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox((float)(rectangle2D.getWidth() * 0.5f),(float)(rectangle2D.getHeight() * 0.5f));

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygonShape;
        fixtureDef.density = 100 * (float)(rectangle2D.getWidth()*rectangle2D.getHeight());
        fixtureDef.friction = 5.0f;
        body.createFixture(fixtureDef);

    }

    ShapeType2D getShapeType2D() {
        return shapeType2D;
    }

    Body getBody() {
        return body;
    }
}
