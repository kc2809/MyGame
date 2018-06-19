package com.mygdx.game.object;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mygdx.game.util.Constants.SQUARE_HEIGHT;
import static com.mygdx.game.util.Constants.SQUARE_WIDTH;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;

public class Level {
    Stage stage;
    World world;

    int count = 0;
    int move = 1;

    public Level(Viewport viewport, World world) {
        stage = new Stage(viewport);
        this.world = world;
    }

    public void draw() {
        stage.draw();
    }

    public void update(float dentaTime) {
        stage.act(dentaTime);
    }

    public void generateNextRow() {
        for (int i = 0; i < 9; i++) {
            float x = -VIEWPORT_WIDTH / 2 + 0.1f + SQUARE_WIDTH * i + 0.1f * i;
            float y = count * SQUARE_HEIGHT + 0.1f * count;
            Square s = new Square(world, x, y);
            stage.addActor(s);
        }
        count++;
    }

    public void moveOneRow() {
        for (int i = 0; i < stage.getActors().size; ++i) {
            Actor actor = stage.getActors().get(i);
            actor.addAction(Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT, 1));
        }
//        stage.addAction(Actions.moveTo(0, -move * SQUARE_HEIGHT, 3));
        //move++;
    }
}
