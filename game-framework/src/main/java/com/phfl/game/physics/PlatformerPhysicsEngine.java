package com.phfl.game.physics;

import com.artemis.ComponentMapper;
import com.phfl.game.artemis.components.Body;
import com.phfl.game.artemis.components.Velocity;
import com.phfl.game.artemis.systems.TiledMapSystem;

public class PlatformerPhysicsEngine extends PhysicsEngine {
    TiledMapSystem mapSystem;

    ComponentMapper<Body> bodyMap;
    ComponentMapper<Velocity> velocityMap;

    @Override
    public void process(int entityId) {
        Velocity velocity = velocityMap.get(entityId);
        bodyMap.get(entityId).center.add(velocity.x, velocity.y);
    }

    private void processX(Body b, Velocity v) {
        // update positions
        b.center.x += v.x;

        // if object is moving, check for collisions.
        if (v.x != 0) {
            // Calculations
            int yMin = mapSystem.minYTile(b); // left of hitbox
            int yMax = mapSystem.maxYTile(b); // right of hitbox

        }
    }

}
