package com.phfl.legacy.game.platformer;

import com.phfl.game.artemis.components.Body;
import com.phfl.legacy.game.LegacyGamePhysicsSystem;

public class PlatformerPhysicsSystem extends LegacyGamePhysicsSystem {
    public float friction;
    public float airResistance;
    public float gravity;

    public PlatformerPhysicsSystem(float gravity) {
        super(PlatformerPhysicsComponent.class);
        this.gravity = gravity;
        this.friction = 1f;
        this.airResistance = 1f;
    }

    protected void process(float dt, Object e) {
        // PlatformerPhysicsComponent physics = e.getComponent(PlatformerPhysicsComponent.class);
        // RenderableComponent avatar = e.getComponent(RenderableComponent.class);
        // processXAxis(dt, avatar.body, physics);
        // processYAxis(dt, avatar.body, physics);
    }

    private void processXAxis(float dt, Body body, PlatformerPhysicsComponent physics) {
        int tileSize = map.s;

        // update positions
        body.center.x += physics.vx;

        // check map collisions
        if (physics.vx != 0) {
            // Calculations
            int yMin = minYTile(body, tileSize); // left of hitbox
            int yMax = maxYTile(body, tileSize); // right of hitbox
            int xTile; // forwardmost tile column
            float newX; // new x, in case of collision. Also the left face of the
                        // entity
            if (physics.vx < 0) {
                // moving left
                xTile = minXTile(body, tileSize); // leftmost tile
                newX = ((xTile + 1) * tileSize); // nx set at the column to the right
                                                 // of this tile.
            } else {
                // moving right
                xTile = maxXTile(body, tileSize); // rightmost tile
                newX = ((xTile) * tileSize) - body.w(); // nx should be at the left
                                                        // face of fx, displaced by
                                                        // its width
            }

            // check for solids
            boolean tileIsSolid;
            for (int yTile = yMin; yTile <= yMax; yTile++) {
                // TODO: real solid check
                try {
                    tileIsSolid = map.tiles[xTile][yTile] != null;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    tileIsSolid = true;
                }

                if (tileIsSolid) {
                    // violating a solid tile. move out of it.
                    body.center.x = newX;
                    physics.vx = 0;
                }
            }
        }

        // update forces
        if (physics.groundedFrames > 0) {
            physics.vx *= 1 - friction;
        } else {
            physics.vx *= 1 - airResistance;
        }
    }

    private void processYAxis(float dt, Body body, PlatformerPhysicsComponent physics) {
        int tileSize = map.s;

        body.center.y += physics.vy;
        physics.vy -= gravity * dt;

        if (physics.groundedFrames > 0) {
            physics.groundedFrames--;
        }

        // check map collisions
        if (physics.vy != 0) {
            // moving down
            int yTile;
            float newY;
            int xMin = minXTile(body, tileSize); // left of hitbox
            int xMax = maxXTile(body, tileSize); // right of hitbox
            if (physics.vy < 0) {
                yTile = minYTile(body, tileSize); // bottom-most tile
                newY = ((yTile + 1) * tileSize);
            } else {
                yTile = maxYTile(body, tileSize); // top-most tile
                newY = ((yTile) * tileSize) - body.h();
            }

            // check for solids
            for (int x = xMin; x <= xMax; x++) {
                // TODO: real solid check
                if (map.tiles[x][yTile] != null) {
                    // violating a solid tile. move out of it.
                    body.center.y = newY;
                    if (physics.vy < 0) {
                        physics.groundedFrames = 2;
                    }
                    physics.vy = 0;
                }
            }
        }

    }

    private int maxXTile(Body body, int tileSize) {
        return (int) (body.x() + body.w() - 1) / tileSize;
    }

    private int minXTile(Body body, int tileSize) {
        if (body.x() < 0)
            return -1 * ((int) (-body.x()) / tileSize) - 1;
        return (int) (body.x()) / tileSize;
    }

    private int maxYTile(Body body, int tileSize) {
        return (int) (body.y() + body.h() - 1) / tileSize;
    }

    private int minYTile(Body body, int tileSize) {
        return (int) ((body.y()) / tileSize);
    }
}
