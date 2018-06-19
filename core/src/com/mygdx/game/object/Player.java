package com.mygdx.game.object;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

public class Player extends Stage {

    int countOnFire = 0;
    MainGameScreen mainGameScreen;

    Vector2 positionToFire;

    public Player(Viewport viewport, Screen screen) {
        super(viewport);
        this.mainGameScreen = (MainGameScreen) screen;
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
        if (!isFirstBallTouchGround())
           actor.addAction(Actions.moveTo(positionToFire.x, positionToFire.y, 0.2f));
        else
            positionToFire = new Vector2(actor.getSprite().getX(), actor.getSprite().getY());
        countOnFire++;
        if (countOnFire == this.getActors().size) {
            countOnFire = 0;
            mainGameScreen.nextRow();
        }
    }
}
