package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

public class Player extends Stage {

    int countOnFire = 0;
    MainGameScreen mainGameScreen;

    private static final float SPEED = 20;
    public Vector2 positionToFire;
    private Vector2 velocity;

    public Player(Viewport viewport, Screen screen) {
        super(viewport);
        this.mainGameScreen = (MainGameScreen) screen;
        positionToFire = new Vector2(0, 0);

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
        } else{
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
}
