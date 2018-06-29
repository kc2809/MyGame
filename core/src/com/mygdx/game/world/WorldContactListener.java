package com.mygdx.game.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.object.Ball;
import com.mygdx.game.object.Item1;
import com.mygdx.game.object.MoneyItem;
import com.mygdx.game.object.Square;
import com.mygdx.game.screens.MainGameScreen;

import static com.mygdx.game.util.VectorUtil.reflectVector;

public class WorldContactListener implements ContactListener {

    MainGameScreen screen;

    public WorldContactListener(MainGameScreen gameScreen) {
        this.screen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        Vector2 normal = contact.getWorldManifold().getNormal();
        Vector2 roundNormal = new Vector2(Math.round(normal.x), Math.round(normal.y));

        if (fixA.getBody().getUserData() instanceof Item1 || fixB.getBody().getUserData() instanceof Item1) {
//            System.out.println("dis me may");
//            contact.setEnabled(false);
            Item1 item1 = (Item1) ((fixA.getBody().getUserData() instanceof Item1
                    ? fixA.getBody().getUserData()
                    : fixB.getBody().getUserData()));
            item1.remove();
            return;
        }

        if (fixA.getBody().getUserData() instanceof MoneyItem || fixB.getBody().getUserData() instanceof MoneyItem) {
//            System.out.println("dis me may");
//            contact.setEnabled(false);
            MoneyItem item1 = (MoneyItem) ((fixA.getBody().getUserData() instanceof MoneyItem
                    ? fixA.getBody().getUserData()
                    : fixB.getBody().getUserData()));
            item1.remove();
            return;
        }


        Square s = null;
        if (fixA.getBody().getUserData() instanceof Square) {
            s = (Square) fixA.getBody().getUserData();
        }

        if (fixB.getBody().getUserData() instanceof Square) {
            s = (Square) fixB.getBody().getUserData();
        }
        if (s != null) {
//            screen.getPlayer().addNewBall();
//            screen.setEffectAtPosition(s.getBody().getPosition());
//            s.remove();
            s.descreaseValue();
            //   return;
        }

        Ball ball = fixA.getBody().getUserData() instanceof Ball ? (Ball) fixA.getBody().getUserData() : (Ball) fixB.getBody().getUserData();

        if ("botWall".equals(fixA.getBody().getUserData()) || "botWall".equals(fixB.getBody().getUserData())) {
            ball.stop();
            return;
        }

        Vector2 velocity = ball.getBody().getLinearVelocity();
        Vector2 r = reflectVector(velocity, roundNormal).nor().scl(20f);
        ball.fire(r.x, r.y);


        if (fixA.getBody().getUserData() instanceof Square || fixB.getBody().getUserData() instanceof Square) {
//            System.out.println("aaaa");
        }
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
}
