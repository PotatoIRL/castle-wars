package com.phfl.game.cw.entities;

import com.phfl.game.GameWorld;
import com.phfl.game.artemis.components.Avatar;
import com.phfl.game.artemis.components.Body;
import com.phfl.game.input.GameController;

public class BaseHuman implements GameController {
  private int eid;
  private Body body;
  // private Velocity velocity;
  private Avatar avatar;

  public BaseHuman(GameWorld world) {
    // create entity
    this.eid = world.create();

    // add components
    body = world.createComponent(eid, Body.class);
    body.center.set(64, 64);
    body.halfSize.set(16, 16);

    // velocity = world.createComponent(eid, Velocity.class);

    avatar = world.createComponent(eid, Avatar.class);
    avatar.name = "base-human";

    // bind input
    world.addController(this);
  }

  @Override
  public void processController(float delta) {

  }
}
