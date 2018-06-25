package com.mygdx.game.object;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.util.Constants.SQUARE_HEIGHT;
import static com.mygdx.game.util.Constants.SQUARE_WIDTH;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;

public class Level {
    public Stage stage;
    World world;

    int count = 0;

    int currentLevel = 0;
    List<int[]> listLevels;

    public Level(Viewport viewport, World world) {
        stage = new Stage(viewport);
        this.world = world;
        listLevels = new ArrayList<>();
        initLevel();
        generateLevelByNumber(currentLevel);
    }

    public void draw() {
        stage.draw();
    }

    public void update(float dentaTime) {
        stage.act(dentaTime);
    }

//    public void generateNextRow() {
//        for (int i = 0; i < 9; i++) {
//            float x = -VIEWPORT_WIDTH / 2 + 0.1f + SQUARE_WIDTH * i + 0.1f * i;
//            float y = count * SQUARE_HEIGHT + 0.1f * count;
//            Square s = new Square(world, x, y);
//            stage.addActor(s);
//        }
//        count++;
//        int x = 1 >> 2;
//        int z = 7;
//        int mask = 0b1;
//        String s = "";
//        for (int i = 0; i < 8; ++i) {
//            s += (z & mask);
//            z = z >> 1;
//        }
//        System.out.println("bit value: " + s);
//    }

    public void moveOneRow() {
        if (stage.getActors().size == 1) generateLevelByNumber(++currentLevel);
        if (currentLevel > 0) currentLevel = 0;
        for (int i = 0; i < stage.getActors().size; ++i) {
            Actor actor = stage.getActors().get(i);
            actor.addAction(Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT, 1));
        }
//        stage.addAction(Actions.moveTo(0, -move * SQUARE_HEIGHT, 3));
        //move++;
    }


    private void initLevel() {
        listLevels.add(new int[]{123, 50, 90, 457, 135});
        listLevels.add(new int[]{491, 8, 12, 0, 125});
        listLevels.add(new int[]{12, 25, 134, 1});
    }

    private void generateLevelByNumber(int i) {
        if (i > listLevels.size()) i = 0;
        generateLevel(listLevels.get(i));
    }

    private void generateLevel(int[] values) {
        //one value means one row
        // value from [0, 512]
        // generate 8 square based on binary value of it.
        int mask = 0b1;
        for (int i = 0; i < values.length; ++i) {
            int val = values[i];
            for (int j = 0; j < 9; ++j) {
                if ((val & mask) != 0) {
                    float x = -VIEWPORT_WIDTH / 2 + 0.1f + SQUARE_WIDTH * j + 0.1f * j;
                    float y = i * SQUARE_HEIGHT + 0.1f * i;
                    Square s = new Square(world, x, y);
                    stage.addActor(s);
                }
                val = val >> 1;
            }
        }
    }
}
