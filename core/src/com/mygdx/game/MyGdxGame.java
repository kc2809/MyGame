package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.world.WorldController;
import com.mygdx.game.world.WorldRenderer;

public class MyGdxGame extends ApplicationAdapter {
	WorldController worldController;
	WorldRenderer worldRenderer;
	private boolean paused;

	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
		worldController = new WorldController();
		worldRenderer = new WorldRenderer(worldController);
		paused = false;

	}

	@Override
	public void render () {
	//	if (!paused)
			worldController.update(Gdx.graphics.getDeltaTime());
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		worldRenderer.render();

	}

	@Override
	public void resize(int width, int height) {
		worldRenderer.resize(width, height);
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
	}

	@Override
	public void dispose () {
		worldRenderer.dispose();
	}
}
