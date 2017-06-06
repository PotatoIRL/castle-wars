package com.phfl.game.artemis.systems;

import com.artemis.BaseSystem;
import com.badlogic.gdx.utils.Array;
import com.phfl.game.input.GameController;

public class GameControllerSystem extends BaseSystem {
    Array<GameController> controllers = new Array<GameController>();

    @Override
    protected void processSystem() {
        for (GameController controller : controllers) {
            controller.processController(world.delta);
        }
    }

    public void addController(GameController controller) {
        controllers.add(controller);
    }

}
