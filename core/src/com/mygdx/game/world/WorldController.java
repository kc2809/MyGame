package com.mygdx.game.world;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.core.GameObjectBox2d;
import com.mygdx.game.object.Player;
import com.mygdx.game.object.Walls;
import com.mygdx.game.util.CameraHelper;

import java.util.ArrayList;
import java.util.List;

public class WorldController extends InputAdapter {
    public CameraHelper cameraHelper;
    //  Sprite sprite;
    World world;
    List<GameObjectBox2d> objects = new ArrayList<>();

    Player player;
    Walls walls;

    public WorldController() {
        world = new World(new Vector2(0,0),true);
        cameraHelper = new CameraHelper();
    }

    public void init(OrthographicCamera camera) {
        player = new Player(Assets.instance.bunny.head, world);
        walls = new Walls(world, camera);

        objects.add(player);
        world.setContactListener(new WorldContactListener());
    }

    public void update(float deltaTime) {
        world.step(1f/60f, 6, 2);
        handleDebugInput(deltaTime);
        objects.forEach(GameObjectBox2d::update);
        cameraHelper.update(deltaTime);
    }

    //handle camera
    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != ApplicationType.Desktop) return;

        // Camera Controls (move)
        float camMoveSpeed = 5 * deltaTime;
        float camMoveSpeedAccelerationFactor = 5;
        if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *=
                camMoveSpeedAccelerationFactor;
        if (Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed,
                0);
        if (Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0,
                -camMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.BACKSPACE))
            cameraHelper.setPosition(0, 0);
    }

    private void moveCamera(float x, float y) {
        x += cameraHelper.getPosition().x;
        y += cameraHelper.getPosition().y;
        cameraHelper.setPosition(x, y);
    }

    public void draw(Batch batch) {
        objects.forEach(object -> object.draw(batch));
    }

}
