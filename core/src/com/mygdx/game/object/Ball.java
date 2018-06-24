package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.effect.EffectManager;

import static com.mygdx.game.util.Constants.BALL_PHYSIC;
import static com.mygdx.game.util.Constants.PPM;
import static com.mygdx.game.util.Constants.WORLD_PHYSIC;

public class Ball extends Actor {

    Sprite sprite;
    Body body;
    World world;

    public Ball(World world) {
        sprite = new Sprite(Assets.instance.circle);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        this.world = world;
        sprite.setPosition(0, 0);
        createPhysics();
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

    }

    boolean updateByBody = true;


    private void createPhysics() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.position.set(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);

        body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        //  shape.setAsBox(sprite.getWidth()/2 , sprite.getHeight() /2 );
        shape.setRadius(sprite.getWidth() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.filter.categoryBits = BALL_PHYSIC;
        fixtureDef.filter.maskBits = WORLD_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);

    }

    public Body getBody() {
        return body;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(getX(), getY());
        body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
//        sprite.draw(batch);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (updateByBody) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            setX(sprite.getX());
            setY(sprite.getY());
        } else {
            setPosition(getX(), getY());
        }

        if (body.getLinearVelocity().len() > 0 && body.getLinearVelocity().len() < 10) {
            body.setLinearVelocity(body.getLinearVelocity().scl(10));
        }
    }

    @Override
    public boolean remove() {
        world.destroyBody(body);
        return super.remove();
    }

    public void fire(float x, float y) {
        updateByBody = true;
        body.setLinearVelocity(x, y);
    }

    public void fireWithVelocity(Vector2 velocity) {
        updateByBody = true;
        body.setLinearVelocity(velocity);
    }


    public void stop() {
        updateByBody = false;
        body.setLinearVelocity(0, 0);
        Player player = (Player) getStage();
        player.eventBallTouchGround(this);
    }
}
