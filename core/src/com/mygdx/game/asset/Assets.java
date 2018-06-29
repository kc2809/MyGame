package com.mygdx.game.asset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.game.util.Constants;
import com.sun.media.jfxmediaimpl.MediaDisposer.Disposable;

public class Assets implements Disposable, AssetErrorListener {
    private static final float FONT_SCREEN_WIDTH_FRACTION = 1.0f / 20.f;

    public static final String TAG = Assets.class.getName();
    public static final Assets instance = new Assets();
    public AssetBunny bunny;
    public AssetRock rock;
    private AssetManager assetManager;
    public Texture circle;
    public Texture square;
    public Texture money;

    public FileHandle effectFile;
    public FileHandle imagesDir;

    public TextureAtlas ballsAtlas;

    public BitmapFont font;

    private Assets() {
    }

    public void init(AssetManager assetManager) {
        this.assetManager = assetManager;
        assetManager.setErrorListener(this);
        assetManager.load(Constants.TEXTURE_ATLAS_OBJECT, TextureAtlas.class);
//        assetManager.load(Constants.TEXTURE_ATLAS_NUMBER, TextureAtlas.class);
        assetManager.finishLoading();
        Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames() + " - " + assetManager.getAssetNames().size);
        assetManager.getAssetNames();

        for (String a : assetManager.getAssetNames())
            Gdx.app.debug(TAG, "asset: " + a);

        TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECT);

//        TextureAtlas atlasNumber = assetManager.get(Constants.TEXTURE_ATLAS_NUMBER);

        //enable texture filtering for pixel smoothing
//        for (Texture t : atlas.getTextures()) {
//            t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
//        }


        // create game resource object
        bunny = new AssetBunny(atlas);
        rock = new AssetRock(atlas);
        circle = createCircleTexture();
        square = createSquareTexture();
        money = createMoneyTexture();

        //File handler
        effectFile = Gdx.files.internal("square.party");
        imagesDir = Gdx.files.internal("");

        ballsAtlas = new TextureAtlas(Gdx.files.internal("circleeffect.atlas"));

        font = createFont();
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

    public class AssetNumber {
        public final AtlasRegion number;

        public AssetNumber(TextureAtlas textureAtlas, int num) {
            this.number = textureAtlas.findRegion("num" + num);
        }
    }


    private Texture createCircleTexture() {
        Pixmap pixmap = new Pixmap(250, 250, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        Texture circle = new Texture(pixmap);
        pixmap.dispose();
        return circle;
    }

    private Texture createSquareTexture() {
        Pixmap pixmap = new Pixmap(1000, 1000, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fillRectangle(0, 0, 1000, 1000);
        Texture square = new Texture(pixmap);
        pixmap.dispose();
        return square;
    }

    private Texture createMoneyTexture() {
        Pixmap pixmap = new Pixmap(500, 500, Format.RGBA8888);
        pixmap.setColor(Color.GREEN);
        pixmap.drawCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() / 2);
        pixmap.setColor(Color.BLACK);
        pixmap.fillCircle(pixmap.getWidth() / 2, pixmap.getHeight() / 2, pixmap.getWidth() * 3 / 8);
        Texture money = new Texture(pixmap);
        pixmap.dispose();
        return money;
    }


    private BitmapFont createFont() {
        BitmapFont font;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("CaviarDreams.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = (int) (FONT_SCREEN_WIDTH_FRACTION * Gdx.graphics.getWidth());
        parameter.color = Color.RED;
        font = generator.generateFont(parameter);
        font.setUseIntegerPositions(false);
        font.getData().setScale(Constants.VIEWPORT_WIDTH / Gdx.graphics.getWidth());

        generator.dispose();
        return font;
    }
}
