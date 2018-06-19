package com.mygdx.game.util;

import com.badlogic.gdx.math.Vector2;

public class VectorUtil {

    public static Vector2 VECTOR2_ZERO = new Vector2(0,0);

    private VectorUtil() {

    }

    public static Vector2 reflectVector(final Vector2 vector, final Vector2 normal) {
        Vector2 z = normal.cpy().scl(-2 * vector.dot(normal));
        return vector.cpy().add(z);
    }

    public static Vector2 a(final Vector2 vector2){
        return vector2.scl(2.0f);
    }
}
