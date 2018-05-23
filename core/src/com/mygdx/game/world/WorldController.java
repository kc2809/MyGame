package com.mygdx.game.world;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.util.CameraHelper;

import static com.mygdx.game.util.Constants.PPM;

public class WorldController extends InputAdapter {

    public CameraHelper cameraHelper;
    Sprite sprite;
    World world;

    public WorldController() {
        world = new World(new Vector2(0,0),true);
        init();
    }

    private void init() {
        Texture texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        sprite.setSize(sprite.getWidth()/PPM, sprite.getHeight()/PPM);
        sprite.setPosition(0, 0);
       sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        cameraHelper = new CameraHelper();
        createPhysics();
        world.setContactListener(new WorldContactListener());
    }

    public void update(float deltaTime) {
        world.step(1f/60f, 6, 2);

        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != ApplicationType.Desktop) return;
// Selected Sprite Controls
        float sprMoveSpeed = 5 * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(
                -sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.D))
            moveSelectedSprite(sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0,
                sprMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0,
                -sprMoveSpeed);

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
                camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
                -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    private void moveSelectedSprite(float v, float i) {
        sprite.translate(v, i);
    }

    private void createPhysics(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((sprite.getX() + sprite.getWidth()/2),
                (sprite.getY() + sprite.getHeight()/2) );

        Body body =  world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData("player");

        body.setLinearVelocity(-1,0.2f);

//        BodyDef bodyDef2 = new BodyDef();
//        bodyDef2.type = BodyType.StaticBody;
//        bodyDef2.position.set(0, -4);
//        Body body2 =  world.createBody(bodyDef2);
//
//        EdgeShape shape2 = new EdgeShape();
//        shape2.set(-4,0,4,0);
//
//        FixtureDef fixtureDef2 = new FixtureDef();
//        fixtureDef2.shape = shape2;
//        fixtureDef2.density = 0.1f;
//
//        body2.createFixture(fixtureDef2);
//        shape2.dispose();
    }

    //need camera to get viewport width, height
    public void createBoundWalls(OrthographicCamera camera) {
        createWall(new Vector2(-5, 0), new Vector2(0, camera.viewportHeight / 2)
                , new Vector2(0, camera.viewportHeight / 2 * -1), "leftWall");
        createWall(new Vector2(5f, 0), new Vector2(0, camera.viewportHeight / 2)
                , new Vector2(0, camera.viewportHeight / 2 * -1), "rightWall");
        createWall(new Vector2(0, camera.viewportHeight / 2), new Vector2(-5, 0)
                , new Vector2(5, 0), "topWall");
        createWall(new Vector2(0, camera.viewportHeight / 2 * -1), new Vector2(-5, 0)
                , new Vector2(5, 0), "botWall");
    }

    private void createWall(Vector2 position, Vector2 start, Vector2 end, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(position);

        Body b = world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(start, end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
//        fixtureDef.restitution = 0.1f;
//        fixtureDef.friction = 0;
//        fixtureDef.density = 10f;

        b.createFixture(fixtureDef);
        edgeShape.dispose();
        b.setUserData("name)");
    }
}
