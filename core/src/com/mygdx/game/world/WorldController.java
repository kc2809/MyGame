package com.mygdx.game.world;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.mygdx.game.util.CameraHelper;

public class WorldController extends InputAdapter {

    public CameraHelper cameraHelper;
    Sprite sprite;

    public WorldController() {
        init();
    }

    private void init() {
        Texture texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        sprite.setSize(1, 1);
        sprite.setPosition(0, -2.5f);
       sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
        cameraHelper = new CameraHelper();
  //      cameraHelper.setTarget(sprite);
    }

    public void update(float deltaTime) {
        handleDebugInput(deltaTime);
        cameraHelper.update(deltaTime);
    }

    private void handleDebugInput(float deltaTime) {
        if (Gdx.app.getType() != ApplicationType.Desktop) return;
// Selected Sprite Controls
        float sprMoveSpeed = 5 * deltaTime;
        if (Gdx.input.isKeyPressed(Keys.A)) moveSelectedSprite(
                -sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.D))
            moveSelectedSprite(sprMoveSpeed, 0);
        if (Gdx.input.isKeyPressed(Keys.W)) moveSelectedSprite(0,
                sprMoveSpeed);
        if (Gdx.input.isKeyPressed(Keys.S)) moveSelectedSprite(0,
                -sprMoveSpeed);

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

    private void moveSelectedSprite(float v, float i) {
        sprite.translate(v, i);
    }

}
