package com.mygdx.game.box2d;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class Box2dManager {
    private static Box2dManager instance = null;

    private List<Body> bodiesWillBeDestroyed;

    private Box2dManager() {
        bodiesWillBeDestroyed = new ArrayList();
    }

    public static Box2dManager getInstance() {
        if (instance == null) {
            instance = new Box2dManager();
        }
        return instance;
    }

    public List<Body> getBodiesWillBeDestroyed() {
        return bodiesWillBeDestroyed;
    }

    public void addBodyToDestroy(Body body) {
        bodiesWillBeDestroyed.add(body);
    }

    public void destroyBody(World world) {
        if(!bodiesWillBeDestroyed.isEmpty())
        bodiesWillBeDestroyed.forEach(world::destroyBody);
        bodiesWillBeDestroyed.clear();
    }
}
