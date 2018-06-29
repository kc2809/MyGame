package com.mygdx.game.object;


import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.screens.MainGameScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mygdx.game.util.Constants.SQUARE_HEIGHT;
import static com.mygdx.game.util.Constants.SQUARE_WIDTH;
import static com.mygdx.game.util.Constants.VIEWPORT_WIDTH;

public class Level extends Stage {
    World world;

    int currentLevel = 0;
    List<int[]> listLevels;

    MainGameScreen screen;

    Random random = new Random();

    public Level(MainGameScreen screen, Viewport viewport, World world) {
        super(viewport);
        this.screen = screen;
//        stage = new Stage(viewport);
        this.world = world;
        listLevels = new ArrayList<>();
        initLevel();
        //  generateLevelByNumber(currentLevel);
    }

//    public void draw() {
//        stage.draw();
//    }

//    public void update(float dentaTime) {
//        stage.act(dentaTime);
//    }

    public void moveOneRow() {
        if (isLevelFinish()) generateLevelByNumber(++currentLevel);
        if (currentLevel > 0) currentLevel = 0;
        addActionToAllSquare();
    }

    private boolean isLevelFinish() {
        //level finish when there's no square on game
        //then we generate next level
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square) return false;
        }
        return true;
    }

    private void addActionToAllSquare() {
        for (Actor actor : this.getActors()) {
            if (actor instanceof Square || actor instanceof Item1 || actor instanceof MoneyItem) {
                actor.addAction(Actions.moveTo(actor.getX(), actor.getY() - SQUARE_HEIGHT, 1));
            }
        }
    }

    private void initLevel() {
        listLevels.add(new int[]{123, 50, 90, 457, 135});
        listLevels.add(new int[]{491, 8, 12, 0, 125});
        listLevels.add(new int[]{12, 25, 134, 1});
        listLevels.add(new int[]{100, 255, 14, 35});
        listLevels.add(new int[]{100, 496, 87, 499});
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
                //position
                float x = -VIEWPORT_WIDTH / 2 + 0.1f + SQUARE_WIDTH * j + 0.1f * j;
                float y = i * SQUARE_HEIGHT + 0.1f * i;
                if ((val & mask) != 0) {
                    // add square if it is 1
                    Square s = new Square(screen, world, x, y);
                    this.addActor(s);
                } else {
                    // 5% generate Item1
                    if (belowPercent(5)) {
                        this.addActor(new Item1(world, x, y));
                    } else {
                        this.addActor(new MoneyItem(world, x, y));
                    }
                }
                val = val >> 1;
            }
        }
    }

    private boolean belowPercent(int percent) {
        if (random.nextInt(100) > percent) return false;
        return true;
    }

    private boolean boundPercent(int low, int upper) {
        int x = random.nextInt(100);
        if (x < low || x > upper) return false;
        return true;
    }

    public void increaseBallWillBeAddNextStep() {
        ++screen.ballBeAddedNextRow;
    }

}
