import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * CG2D
 * Class Physics
 * Created by Stefan Mermans on 22/03/2016.
 */
class Physics {
    private World world;
    private ArrayList<PhysicsBody> bodies;
    static Vec2 GRAVITY_VECTOR = new Vec2(0,30);

    Physics(){
        world = new World(GRAVITY_VECTOR);
        bodies = new ArrayList<>();

        initComponents();
    }

    /**
     * Calling a few methods for testing purposes
     */
    private void initComponents(){
        /*Two falling cubes onto a static beam example*/
//        createBody(425,500,400,50, BodyType.STATIC);
//        createBody(650,200,200,200,BodyType.DYNAMIC);
//        createBody(500,-50,200,200,BodyType.DYNAMIC);

        /*Weight test example*/
        createBody(400,400,400,50, BodyType.STATIC);
        createBody(400,325,50,100, BodyType.STATIC);
        createBody(400,262,300,25, BodyType.DYNAMIC);
        createBody(500,50,50,100, BodyType.DYNAMIC);
        createBody(300,0,50,200, BodyType.DYNAMIC);
    }

    /**
     * Create a physics body and add it to the bodies arrayList
     * @param x         The starting x coordinate of the body
     * @param y         The starting y coordinate of the body
     * @param width     The width of the body
     * @param height    The height od the body
     * @param type      The BodyType of the body
     */
    void createBody(int x, int y, float width, float height, BodyType type){
        PhysicsBody physicsBody = new PhysicsBody(x,y,new Rectangle2D.Double(0,0,width,height),type, world);
        bodies.add(physicsBody);
    }

    void createBody(int x, int y, float radius, BodyType type){
        PhysicsBody physicsBody = new PhysicsBody(x,y,radius,type, world);
        bodies.add(physicsBody);
    }

    void setWorld(World world){
        this.world = world;
    }

    World getWorld() {
        return world;
    }

    ArrayList<PhysicsBody> getBodies() {
        return bodies;
    }
}
