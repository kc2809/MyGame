package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.box2d.Box2dManager;
import com.mygdx.game.object.Ball;
import com.mygdx.game.object.Level;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.Walls;
import com.mygdx.game.util.Constants;
import com.mygdx.game.world.WorldContactListener;

public class MainGameScreen implements Screen, InputProcessor {
    World world;
    Viewport viewport;
    OrthographicCamera camera;
    Box2DDebugRenderer debugRenderer;

    Walls walls;

    //    Stage player;
    Level level;

    Player player;

    /*
     game attribute
     */
    /*
        0: can be fire
        1: fire
        2: waiting

     */
    int fireFlag = 0;
    int count = 0;
    long timeAtFire;

    //
    @Override
    public void show() {
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        viewport = new ExtendViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT, Constants.VIEWPORT_WIDTH, 50, camera);
        viewport.apply();
        camera.position.set(0, 0, 0);
        world = new World(new Vector2(0, 0), true);

        Gdx.input.setInputProcessor(this);
        initObject();
    }

    private void initObject() {
        walls = new Walls(world, camera);

//        player = new Stage(viewport);
//        player.addActor(ball);

        player = new Player(viewport, this);
        player.addActor(new Ball(world));
        player.addActor(new Ball(world));
        player.addActor(new Ball(world));

        level = new Level(viewport, world);

        world.setContactListener(new WorldContactListener());

    }

    public void nextRow() {
        level.generateNextRow();
        level.moveOneRow();
        fireFlag = 0;
    }

    @Override
    public void render(float delta) {
        world.step(1f / 60f, 6, 2);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        debugRenderer.render(world, camera.combined);

        player.draw();
        player.act(delta);

        level.draw();
        level.update(delta);
        update(delta);
        Box2dManager.getInstance().destroyBody(world);
    }

    private void update(float delta) {
        if (fireFlag == 1) {
            if ((System.currentTimeMillis() - timeAtFire) > 500) {
                Ball b = (Ball) player.getActors().get(count);
                count++;
                b.fire(40, 40);
                if (count == player.getActors().size) {
                    fireFlag = 2;
                    count = 0;
                }
                timeAtFire = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(0, 0, 0);
        walls.setWallPositionByCamera(camera);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Keys.A) {
            System.out.println("cac");
            player.getActors().get(0).remove();
        }
        if (keycode == Keys.B) {
            System.out.println("cac");
            player.addActor(new Ball(world));
        }

        if (keycode == Keys.C) {
            level.generateNextRow();
        }
        if (keycode == Keys.D) {
            level.moveOneRow();
        }

        if (keycode == Keys.F) {
//            fireFlag = true;
            if(fireFlag != 0) return false;
            fireFlag = 1;
            player.resetWhenFireEventFinish();
        }
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.addActor(new Ball(world));
        System.out.println("stoutout");
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
