package com.phfl.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.phfl.game.artemis.components.Body;

public class GameUtils {

    public static int getTileSize(TiledMap map) {
        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(0);
        return Math.round(tileLayer.getTileWidth());
    }

    public static int maxXTile(Body body, int tileSize) {
        return (int) (body.x() + body.w() - 1) / tileSize;
    }

    public static int minXTile(Body body, int tileSize) {
        if (body.x() < 0)
            return -1 * ((int) (-body.x()) / tileSize) - 1;
        return (int) (body.x()) / tileSize;
    }

    public static int maxYTile(Body body, int tileSize) {
        return (int) (body.y() + body.h() - 1) / tileSize;
    }

    public static int minYTile(Body body, int tileSize) {
        return (int) ((body.y()) / tileSize);
    }

}
