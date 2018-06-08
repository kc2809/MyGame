package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Vector2 normal = contact.getWorldManifold().getNormal();
        Vector2 a[] = contact.getWorldManifold().getPoints();
        Vector2 z = new Vector2();
        // float t =  v.angle();

        Fixture player = (fixA.getBody().getUserData() == "player") ? fixA : fixB;

      //  if (fixA.getBody().getUserData() == "player" || fixB.getBody().getUserData() == "player") {
            Vector2 velocity = player.getBody().getLinearVelocity();
//            Vector2 r = fixA.getBody().getLinearVelocity().add(normal.scl())
            Vector2 r = reflectVector(velocity, normal);
        player.getBody().setLinearVelocity(r);
      //  }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }

    private Vector2 reflectVector(final Vector2 velocity, final Vector2 normal) {
        Vector2 z = normal.scl(-2 * velocity.dot(normal));
        return velocity.add(z);
    }
}
