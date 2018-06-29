package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
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

public class Item1 extends Actor {

    public Sprite sprite;
    public Body body;
    public ParticleEffect effect;
    World world;
    boolean isCreatePhysics = false;

    public Item1(World world, float x, float y) {
        super();
        this.world = world;
//        setBounds(getX(), getY(), 0.5f, 0.5f);
        sprite = new Sprite(Assets.instance.circle);
        sprite.setSize(sprite.getWidth() / PPM, sprite.getHeight() / PPM);
        createPhysics();
        effect = getTestEffect();
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
        fixtureDef.filter.maskBits = BALL_PHYSIC;
        fixtureDef.filter.categoryBits = WORLD_PHYSIC;

        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x + 0.5f - sprite.getWidth() / 2, y + 0.5f - sprite.getHeight() / 2);
        effect.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        if (body != null)
            body.setTransform(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
//        sprite.setPosition(getX() + 0.5f - sprite.getWidth()/2, getY() + 0.5f -  sprite.getHeight()/2);
//        effect.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        setPosition(getX(), getY());
        sprite.draw(batch);
        effect.draw(batch);
        if (isCreatePhysics) {
            createPhysics();
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.update(delta);
        if (effect.isComplete()) {
            effect.start();
        }
    }

    private ParticleEffect getTestEffect() {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Gdx.files.internal("effect3.party"), Gdx.files.internal(""));
        pe.getEmitters().first().setPosition(0, 0);
        pe.scaleEffect(1.0f / 200f);
        pe.start();
        return pe;
    }

    @Override
    public boolean remove() {
        Level levelStage = (Level) getStage();
        levelStage.increaseBallWillBeAddNextStep();
        Box2dManager.getInstance().addBodyToDestroy(body);
        sprite = null;
        effect = null;
        return super.remove();
    }
}
