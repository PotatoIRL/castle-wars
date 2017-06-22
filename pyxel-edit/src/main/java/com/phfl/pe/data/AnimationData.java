package com.phfl.pe.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimationData {
  public String name;
  public int frameDuration;
  public int length;
  public int baseTile;
  public int[] frameDurationMultipliers;
}
