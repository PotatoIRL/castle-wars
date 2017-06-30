package com.phfl.pyxel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TilesetData {
  public int tilesWide;
  public boolean fixedWidth;
  public int numTiles;
  public int tileWidth;
  public int tileHeight;
}
