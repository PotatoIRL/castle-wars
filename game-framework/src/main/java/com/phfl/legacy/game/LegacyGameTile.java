package com.phfl.legacy.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class LegacyGameTile {
  public static TextureRegion DOOR_TEXTURE = null;
  public static TextureRegion FLOOR_TEXTURE = null;
  public static TextureRegion WALL_TEXTURE = null;
  int x, y;
  public boolean solid;
  public TextureRegion texture;
  public int type;

  public LegacyGameTile(Object def, int x, int y, int size) {
    this.x = x;
    this.y = y;
    this.solid = false;

    if (def instanceof Cell) {
      Cell cell = (Cell) def;
      TiledMapTile tile = cell.getTile();
      this.texture = tile.getTextureRegion();
      this.type = tile.getId();
      if (this.type >= 1021 && this.type < 1840) {
        this.solid = true;
      }
    }
    if (def instanceof String) {
      String str = (String) def;
      if (str.contains("D")) {
        this.texture = DOOR_TEXTURE;
      }
      if (str.contains(".")) {
        this.texture = FLOOR_TEXTURE;
      }

      if (str.contains("W")) {
        this.texture = WALL_TEXTURE;
        this.solid = true;
      }
    }
  }

}
