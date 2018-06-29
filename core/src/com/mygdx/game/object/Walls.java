package com.mygdx.game.object;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;
import static com.mygdx.game.util.VectorUtil.VECTOR2_ZERO;

public class Walls {
    World world;
    Body left;
    Body right;
    Body top;
    Body bottom;

    public Walls(World world, OrthographicCamera camera) {
        this.world = world;
        initWallPosition(camera);
    }

    public void initWallPosition(OrthographicCamera camera) {
        left = createWall(VECTOR2_ZERO
                , new Vector2(0, -30)
                , new Vector2(0, 30)
                , "leftWall");
        right = createWall(VECTOR2_ZERO
                , new Vector2(0, -30)
                , new Vector2(0, 30)
                , "rightWall");
        top = createWall(VECTOR2_ZERO
                , new Vector2(-5, 0)
                , new Vector2(5, 0)
                , "topWall");
        bottom = createWall(VECTOR2_ZERO, new Vector2(-5, 0)
                , new Vector2(5, 0), "botWall");

        setWallPositionByCamera(camera);
    }

    public void setWallPositionByCamera(OrthographicCamera camera) {
        left.setTransform(new Vector2(-camera.viewportWidth / 2, 0), 0);
        right.setTransform(new Vector2(camera.viewportWidth / 2, 0), 0);
        bottom.setTransform(new Vector2(0, -camera.viewportHeight / 2), 0);
//        top.setTransform(new Vector2(0, camera.viewportHeight / 2), 0);
        top.setTransform(new Vector2(0, camera.viewportHeight * 5 / 12), 0);
    }

    private Body createWall(Vector2 position, Vector2 start, Vector2 end, String name) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(position);

        Body body = this.world.createBody(bodyDef);

        EdgeShape edgeShape = new EdgeShape();
        edgeShape.set(start, end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = edgeShape;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;


        body.createFixture(fixtureDef);
        edgeShape.dispose();
        body.setUserData(name);

        return body;
    }
}
