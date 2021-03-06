package com.mygdx.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.game.util.Constants;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

public class WorldRenderer implements Disposable {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private WorldController worldController;
    Box2DDebugRenderer debugRenderer;

    public WorldRenderer(WorldController worldController) {
        this.worldController = worldController;
        init();
        this.worldController.createBoundWalls(camera);
    }

    private void init() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
        camera.position.set(0, 0, 0);
        calculateViewport();
        debugRenderer = new Box2DDebugRenderer();
    }

    public void render() {
   //     worldController.cameraHelper.applyTo(camera);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
    //    worldController.sprite.draw(batch);
        batch.end();
        debugRenderer.render(worldController.world, batch.getProjectionMatrix());
    }

    public void resize(int width, int height) {
        camera.viewportHeight = (Constants.VIEWPORT_WIDTH / width) * height;
       // camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
        camera.update();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    private void calculateViewport(){
        camera.viewportHeight = (Constants.VIEWPORT_WIDTH / Gdx.graphics.getWidth()) *  Gdx.graphics.getHeight();
        camera.update();
    }
}
