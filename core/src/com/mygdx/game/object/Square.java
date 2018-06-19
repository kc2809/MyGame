package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.box2d.Box2dManager;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Square extends Actor {

    Sprite sprite;
    Body body;
    World world;

    boolean createPhysics = false;

    public Square(World world, float x, float y) {
        super();
        this.world = world;
        sprite = new Sprite(Assets.instance.square);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        sprite.setPosition(getX(), getY());
        createPhysics();
        setPosition(x, y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(getX(), getY());
        if (body != null)
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    private void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);

        if (world.isLocked()) {
            createPhysics = true;
            return;
        }
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setAsBox(sprite.getWidth() / 2, sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);

        createPhysics = false;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
        setPosition(getX(), getY());
        if (createPhysics) createPhysics();
    }

    @Override
    public boolean remove() {
        Box2dManager.getInstance().addBodyToDestroy(body);
        return super.remove();
    }

}
