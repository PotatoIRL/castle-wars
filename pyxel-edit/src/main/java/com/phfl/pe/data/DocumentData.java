package com.phfl.pe.data;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DocumentData {
  public Map<String, AnimationData> animations;
  public TilesetData tileset;
  public String name;
}
