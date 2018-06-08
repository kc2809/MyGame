package com.mygdx.game.core;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.util.Constants;

public abstract class GameObjectBox2d {
    protected Sprite sprite;
    protected World world;
    protected Body body;

    public GameObjectBox2d(World world){
        this.world = world;
        createPhysics();
    }

    public GameObjectBox2d(AtlasRegion atlasRegion, World world) {
        sprite = new Sprite(atlasRegion);
        sprite.setSize(sprite.getWidth() / Constants.PPM, sprite.getHeight() / Constants.PPM);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.world = world;
        createPhysics();
    }


    abstract public void init();

    public void update() {
        // update sprite position follow body position
        if (sprite != null)
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }

    public void draw(Batch batch){
        sprite.draw(batch);
    }

    abstract public void createPhysics();

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}