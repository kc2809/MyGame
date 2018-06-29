package com.mygdx.game.object;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.box2d.Box2dManager;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class MoneyItem extends Actor {
    public Sprite sprite;
    public Body body;
    World world;
    boolean isCreatePhysics = false;

    public MoneyItem(World world, float x, float y) {
        this.world = world;
        sprite = new Sprite(Assets.instance.money);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        createPhysics();
        setPosition(x, y);
    }

    private void createPhysics() {
        if (world.isLocked()) {
            isCreatePhysics = true;
            return;
        }
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        body = world.createBody(bodyDef);
        isCreatePhysics = false;

        CircleShape shape = new CircleShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;
        fixtureDef.filter.maskBits = BALL_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x + 0.5f - sprite.getWidth() / 2, y + 0.5f - sprite.getHeight() / 2);
        if (body != null)
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition(getX(), getY());
        sprite.draw(batch);
        if (isCreatePhysics) {
            createPhysics();
        }
    }

    @Override
    public boolean remove() {
        Box2dManager.getInstance().addBodyToDestroy(body);
        Level level = (Level) this.getStage();
        level.screen.uiObject.increateMoney();
        return super.remove();
    }
}
