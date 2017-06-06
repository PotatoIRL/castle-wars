package com.phfl.game.artemis.systems;

import com.artemis.BaseSystem;
import com.phfl.game.GameAssets;
import com.phfl.game.asset.AnimationMap;

public class GameAssetSystem extends BaseSystem {

    @Override
    protected void processSystem() {

    }

    public AnimationMap getAvatarAnimationMap(String avatarName) {
        return GameAssets.get(String.format("avatars/%s", avatarName), AnimationMap.class);
    }

}
