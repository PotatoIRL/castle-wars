package com.phfl.pyxel.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CanvasData {
  public int tileWidth;
  public int width;
  public int tileHeight;
  public int height;
  public int numLayers;
}
