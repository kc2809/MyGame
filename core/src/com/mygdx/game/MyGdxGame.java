package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.asset.Assets;
import com.mygdx.game.screens.MainGameScreen;

public class MyGdxGame extends Game {

	@Override
	public void create () {
		Assets.instance.init(new AssetManager());
		setScreen(new MainGameScreen());
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
