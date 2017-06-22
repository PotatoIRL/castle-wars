package com.phfl.legacy.game;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector3;
import com.phfl.game.artemis.components.Body;

public class LegacyGameMap {
  public int w;
  public int h;
  public LegacyGameTile[][] tiles;
  public int s;
  public Body bounds;

  public LegacyGameMap(Object[][] tileInfo, int tileSize) {
    s = tileSize;
    w = tileInfo.length;
    h = tileInfo[0].length;
    tiles = new LegacyGameTile[w][h];
    for (int x = 0; x < w; x++)
      for (int y = 0; y < h; y++)
        if (null != tileInfo[x][y])
          tiles[x][y] = new LegacyGameTile(tileInfo[x][y], x, y, s);

    this.bounds = new Body(0, 0, w * s, h * s);
  }

  public GridPoint2 getCoordinate(Vector3 worldPoint) {
    return getCoordinate(worldPoint.x, worldPoint.y);
  }

  private GridPoint2 getCoordinate(float x, float y) {
    return new GridPoint2((int) Math.floor(x / s), (int) Math.floor(y / s));
  }

  public boolean isFree(GridPoint2 p) {
    if (!this.contains(p) || null == tiles[p.x][p.y]) {
      return false;
    }
    return !tiles[p.x][p.y].solid;
  }

  private boolean contains(GridPoint2 p) {
    if (p.x < 0 || p.x >= w)
      return false;
    if (p.y < 0 || p.y >= h)
      return false;
    return true;
  }

  public boolean isFree(GridPoint2 p1, GridPoint2 p2) {
    if (!this.contains(p1) || !this.contains(p2)) {
      return false;
    }
    int xMin = Math.min(p1.x, p2.x);
    int xMax = Math.max(p1.x, p2.x);
    int yMin = Math.min(p1.y, p2.y);
    int yMax = Math.max(p1.y, p2.y);

    for (int x = xMin; x <= xMax; x++) {
      for (int y = yMin; y <= yMax; y++) {
        if (!tiles[x][y].solid) {
          return false;
        }
      }
    }

    return true;
  }

  // - Implement "minXTile, etc..." as functions in this class
  // - Give map an AABB in game world. Map transitions therefore become easier
  // - Position tiles as they will be in the game world
  // - Support for hexes (Possibly create a new HexagonalGameMap class?)
}
