package com.phfl.game.artemis.systems;

import com.artemis.BaseSystem;
import com.phfl.game.GameAssets;
import com.phfl.game.asset.AnimationMap;

public class GameAssetSystem extends BaseSystem {
  String animationMapDir = "avatars";

  @Override
  protected void processSystem() {

  }

  public AnimationMap getAnimationMap(String avatarName) {
    return GameAssets.get(String.format("%s/%s", animationMapDir, avatarName), AnimationMap.class);
  }

}
