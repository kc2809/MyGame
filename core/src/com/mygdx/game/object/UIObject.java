package com.mygdx.game.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.asset.Assets;

public class UIObject {

    Stage stage;

    BitmapFont font;
    Label scoreLabel;
    Label moneyLabel;

    StringBuilder stringBuilder;
    int score = 0;

    StringBuilder moneyBuilder;
    int money = 0;

    public UIObject() {

        stringBuilder = new StringBuilder();
        moneyBuilder = new StringBuilder();

        stage = new Stage();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("DroidSerif-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 50;
        parameter.color = Color.WHITE;
        font = generator.generateFont(parameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = font;

        scoreLabel = new Label("xin chao -0", labelStyle);
        scoreLabel.setSize(Gdx.graphics.getWidth() * 4 / 11, Gdx.graphics.getHeight() / 12);
        scoreLabel.setPosition(Gdx.graphics.getWidth() / 2 - scoreLabel.getWidth() / 2, Gdx.graphics.getHeight() * 11 / 12);
        scoreLabel.setAlignment(Align.center);
//        label.setWrap(true);


        moneyLabel = new Label("1150", labelStyle);
        moneyLabel.setSize(Gdx.graphics.getWidth() / 11, Gdx.graphics.getHeight() / 12);
        moneyLabel.setPosition(Gdx.graphics.getWidth() * 9 / 11 - moneyLabel.getWidth() / 2, Gdx.graphics.getHeight() * 11 / 12);
        moneyLabel.setAlignment(Align.center);

        generator.dispose();

        Image img = new Image(Assets.instance.money);
        img.setSize(Gdx.graphics.getWidth() / 22, Gdx.graphics.getHeight() / 24);
        img.setPosition(Gdx.graphics.getWidth() * 10 / 11, Gdx.graphics.getHeight() * 11 / 12 + img.getHeight() / 2);

        stage.addActor(scoreLabel);
        stage.addActor(moneyLabel);
        stage.addActor(img);

        setScore(0);
        setMoney(0);

//        stage.addActor(new ItemBall());
    }

    public void draw() {
        stage.draw();
    }


    private void setScore(int score) {
        stringBuilder.setLength(0);
        stringBuilder.append(score);
        scoreLabel.setText(stringBuilder);
    }

    private void setMoney(int money) {
        moneyBuilder.setLength(0);
        moneyBuilder.append(money);
        moneyLabel.setText(moneyBuilder);
    }

    public void increaseScore() {
        setScore(++score);
    }

    public void increateMoney() {
        setMoney(++money);
    }
}
