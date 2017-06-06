package com.phfl.game.artemis.components;

import com.artemis.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Aspires to be the world's most performant POJO AABB
 * 
 * @author Phil
 *
 */
public class Body extends Component {
    public final Vector2 center;
    public final Vector2 halfSize;

    public Body() {
        this.center = new Vector2();
        this.halfSize = new Vector2();
    }

    public Body(Vector2 centre, Vector2 halfSize) {
        this.halfSize = halfSize;
        this.center = centre;
    }

    public Body(int x, int y, int w, int h) {
        this.halfSize = new Vector2(w / 2f, h / 2f);
        this.center = new Vector2(x - halfSize.x, y - halfSize.y);
    }

    public static boolean collides(Body a, Body b) {
        if (Math.abs(a.center.x - b.center.x) < a.halfSize.x + b.halfSize.x) {
            if (Math.abs(a.center.y - b.center.y) < a.halfSize.y + b.halfSize.y) {
                return true;
            }
        }

        return false;
    }

    public static boolean inside(Body a, Vector2 b) {
        if (Math.abs(a.center.x - b.x) < a.halfSize.x) {
            if (Math.abs(a.center.y - b.y) < a.halfSize.y) {
                return true;
            }
        }
        return false;
    }

    public float x() {
        return center.x - halfSize.x;
    }

    public float x2() {
        return center.x + halfSize.x;
    }

    public float y() {
        return center.y - halfSize.y;
    }

    public int w() {
        return Math.round(halfSize.x * 2);
    }

    public int h() {
        return Math.round(halfSize.y * 2);
    }

}
