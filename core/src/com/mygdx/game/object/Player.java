package com.mygdx.game.object;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

public class Player extends Stage {

    private static final float SPEED = 20;
    public Vector2 positionToFire;
    int countOnFire = 0;
    MainGameScreen mainGameScreen;
    private Vector2 velocity;
    World world;

    public Player(Viewport viewport, Screen screen, World world) {
        super(viewport);
        this.mainGameScreen = (MainGameScreen) screen;
//        positionToFire = new Vector2(0, 0);
        this.world = world;
        setInitPositon();
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocityWithClickPoint(Vector2 clickPoint) {
        velocity = clickPoint.cpy().sub(positionToFire).nor().scl(SPEED);
//        velocity = new Vector2(1,1).scl(40);
    }

    public void increteCountOnFire() {
        countOnFire++;
    }

    public void resetWhenFireEventFinish() {
        countOnFire = 0;
    }

    public boolean isFirstBallTouchGround() {
        return countOnFire == 0;
    }

    //Actor is ball
    public void eventBallTouchGround(Ball actor) {
        if (!isFirstBallTouchGround()) {
            //           actor.addAction(Actions.moveTo(positionToFire.x, positionToFire.y, 0.2f));
            Action moveToAction = Actions.moveTo(positionToFire.x, positionToFire.y, 0.5f);
            actor.addAction(Actions.sequence(moveToAction, Actions.run(this::nextStep)));
        } else {
            positionToFire = new Vector2(actor.getSprite().getX(), actor.getSprite().getY());
            nextStep();
        }

    }

    private void nextStep() {
        countOnFire++;
        if (countOnFire == this.getActors().size) {
            countOnFire = 0;
            mainGameScreen.nextRow();
        }
    }

    public void setInitPositon() {
        positionToFire = new Vector2(0, -getCamera().viewportHeight / 2 + 1);
        if (this.getActors().size > 0) {
            for (int i = 0; i < this.getActors().size; ++i) {
                Ball b = (Ball) this.getActors().get(i);
                b.setPosition(this.positionToFire.x, this.positionToFire.y);
            }
        }
    }

    public void addNewBall() {
        this.addActor(new Ball(this.world, this.positionToFire));
    }
}
