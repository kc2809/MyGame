package com.mygdx.game.effect;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.asset.Assets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EffectManager extends Actor {

    List<ParticleEffect> effects;

    public EffectManager() {
        this.effects = new ArrayList<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        effects.forEach(effect -> effect.draw(batch));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effects.forEach(particleEffect -> particleEffect.update(delta));
    }

    public void setEffectAtPosition(float x, float y) {
        Optional<ParticleEffect> optinalEffect = effects.stream().filter(ParticleEffect::isComplete).findAny();
        if (!optinalEffect.isPresent()) {
            effects.add(newParticleEffectAtPosition(x, y));
        }
        optinalEffect.ifPresent(particleEffect -> {
            particleEffect.setPosition(x, y);
            particleEffect.start();
        });
    }

    private ParticleEffect newParticleEffectAtPosition(float x, float y) {
        ParticleEffect pe = new ParticleEffect();
        pe.load(Assets.instance.effectFile, Assets.instance.imagesDir);
        pe.getEmitters().first().setPosition(x, y);
        pe.scaleEffect(1.0f / 100f);
        pe.start();
        return pe;
    }

}
