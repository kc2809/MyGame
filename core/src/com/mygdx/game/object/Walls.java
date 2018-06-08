package com.mygdx.game.object;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.core.GameObjectBox2d;

public class Walls {
    World world;
    GameObjectBox2d left;
    GameObjectBox2d right;
    GameObjectBox2d top;
    GameObjectBox2d bottom;

    public Walls(World world, OrthographicCamera camera) {
        this.world = world;
        left = createWall(new Vector2(-5, 0), new Vector2(0, camera.viewportHeight / 2)
                , new Vector2(0, camera.viewportHeight / 2 * -1), "leftWall");
        right = createWall(new Vector2(5f, 0), new Vector2(0, camera.viewportHeight / 2)
                , new Vector2(0, camera.viewportHeight / 2 * -1), "rightWall");
        top = createWall(new Vector2(0, camera.viewportHeight / 2), new Vector2(-5, 0)
                , new Vector2(5, 0), "topWall");
        bottom = createWall(new Vector2(0, camera.viewportHeight / 2 * -1), new Vector2(-5, 0)
                , new Vector2(5, 0), "botWall");
    }

    private GameObjectBox2d createWall(Vector2 position, Vector2 start, Vector2 end, String name) {
        return new GameObjectBox2d(world) {
            @Override
            public void init() {
            }

            @Override
            public void createPhysics() {
                BodyDef bodyDef = new BodyDef();
                bodyDef.type = BodyType.StaticBody;
                bodyDef.position.set(position);

                body = this.world.createBody(bodyDef);

                EdgeShape edgeShape = new EdgeShape();
                edgeShape.set(start, end);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = edgeShape;

                body.createFixture(fixtureDef);
                edgeShape.dispose();
                body.setUserData(name);
            }
        };
    }
}
