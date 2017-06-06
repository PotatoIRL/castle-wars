package com.phfl.game.artemis.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.phfl.game.artemis.components.Body;

public class TiledMapSystem extends BaseSystem {

    public TiledMap map;
    private int tileSize = 0;

    @Override
    protected boolean checkProcessing() {
        // do not process anything
        return false;
    }

    @Override
    protected void processSystem() {}

    public int minYTile(Body b) {
        return (int) ((b.y()) / tileSize());
    }

    public int maxYTile(Body b) {
        return (int) (b.y() + b.h() - 1) / tileSize();
    }

    public int tileSize() {
        return this.tileSize;
    }


}
