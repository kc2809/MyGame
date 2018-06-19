package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.mygdx.game.util.Constants;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AssetBunny bunny;
    public AssetRock rock;
    private AssetManager assetManager;
    public Texture circle;
    public Texture square;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECT, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames() + " - " + assetManager.getAssetNames().size);
        assetManager.getAssetNames();

        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECT);

        //enable texture filtering for pixel smoothing
        for (Texture t : atlas.getTextures()) {
            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }

        // create game resource object
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        circle = createCircleTexture();
        square = createSquareTexture();
    }

    @Override
    public void error(AssetDescriptor asset, Throwable throwable) {
        Gdx.app.error(TAG, "Couldn't load asset '"
                + asset.fileName + "'", (Exception) throwable);
    }

    @Override
    public void dispose() {
        assetManager.dispose();
    }

    public class AssetBunny {
        public final AtlasRegion head;

        public AssetBunny(TextureAtlas textureAtlas) {
            this.head = textureAtlas.findRegion("bunny_head");
        }
    }

    public class AssetRock {
        public final AtlasRegion edge;
        public final AtlasRegion middle;

        public AssetRock(TextureAtlas textureAtlas) {
            this.edge = textureAtlas.findRegion("rock_edge");
            this.middle = textureAtlas.findRegion("rock_middle");
        }
    }

    private Texture createCircleTexture() {
        Pixmap pixmap = new Pixmap(100, 100, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        Texture circle = new Texture(pixmap);
        pixmap.dispose();
        return circle;
    }

    private Texture createSquareTexture() {
        Pixmap pixmap = new Pixmap(200, 200, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 200, 200);
        Texture square = new Texture(pixmap);
        pixmap.dispose();
        return square;
    }
}
