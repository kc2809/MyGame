package com.mygdx.game.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.util.VectorUtil;

public class Trajectory {
    final float DENTAT = 0.5f;
    float[] vertices;
    int count;
    ShapeRenderer renderer;
    Viewport viewport;

    boolean isVisible = true;

    public Trajectory(Viewport viewport, int count) {
        this.count = count;
        vertices = new float[count];
        renderer = new ShapeRenderer();
        this.viewport = viewport;
    }

    public void projected(Vector2 start, Vector2 end) {
        Vector2 velocity = end.cpy().sub(start).nor();
        vertices[0] = start.x;
        vertices[1] = start.y;
        Vector2 normal = new Vector2(0, 0);
        boolean isReflect = false;
        for (int i = 2; i < count; i += 2) {
            vertices[i] = vertices[i - 2] + velocity.x * DENTAT;
            vertices[i + 1] = vertices[i - 1] + velocity.y * DENTAT;

            if (vertices[i] > viewport.getCamera().viewportWidth / 2) {
                normal = new Vector2(-1, 0);
                isReflect = true;
            }
            if (vertices[i] < -viewport.getCamera().viewportWidth / 2) {
                normal = new Vector2(1, 0);
                isReflect = true;
            }
            if (vertices[i + 1] > viewport.getCamera().viewportHeight / 2) {
                normal = new Vector2(0, -1);
                isReflect = true;
            }
            if (vertices[i + 1] < -viewport.getCamera().viewportHeight / 2) {
                normal = new Vector2(0, 1);
                isReflect = true;
            }
            if (isReflect) {
                velocity = VectorUtil.reflectVector(velocity, normal);
                isReflect = false;
            }
            vertices[i] = vertices[i - 2] + velocity.x * DENTAT;
            vertices[i + 1] = vertices[i - 1] + velocity.y * DENTAT;
        }
    }

    public void draw() {
        if (!isVisible) return;
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.setColor(Color.WHITE);
        renderer.begin(ShapeType.Line);
        renderer.polyline(vertices);

        renderer.end();
    }

    public void setVisible() {
        isVisible = true;
    }

    public void setInvisible() {
        isVisible = false;
    }
}
