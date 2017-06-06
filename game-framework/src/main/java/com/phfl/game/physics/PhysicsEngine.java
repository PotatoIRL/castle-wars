package com.phfl.game.physics;

import com.badlogic.gdx.maps.tiled.TiledMap;

public abstract class PhysicsEngine {
    public TiledMap map;

    public abstract void process(int entityId);

    public boolean canProcess(int entityId) {
        return true;
    }
}
