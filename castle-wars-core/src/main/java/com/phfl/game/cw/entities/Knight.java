package com.phfl.game.cw.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.phfl.game.GameWorld;
import com.phfl.game.artemis.components.Avatar;
import com.phfl.game.artemis.components.Body;
import com.phfl.game.artemis.components.Velocity;
import com.phfl.game.input.GameController;

public class Knight implements GameController {
    // properties
    private static final float MOVE_SPEED = 120;

    // entity information
    private final int eid;
    private final Body body;
    private final Avatar avatar;
    private final Velocity velocity;

    public Knight(GameWorld world) {
        // create entity
        this.eid = world.create();

        // add components
        body = world.createComponent(eid, Body.class);
        body.center.set(64, 64);
        body.halfSize.set(16, 16);

        velocity = world.createComponent(eid, Velocity.class);

        avatar = world.createComponent(eid, Avatar.class);
        avatar.name = "knight";

        // bind input
        world.addController(this);
    }

    @Override
    public void processController(float delta) {
        resolveMovement(delta);
        resolveState(delta);
    }

    private void resolveState(float delta) {
        String lastState = avatar.stateName;
        avatar.stateTime += delta;

        // determine animation state
        if (velocity.len2() == 0) {
            avatar.stateName = "stand";
        } else {
            avatar.stateName = "walk";
        }

        // check flipped
        if (velocity.x < 0) {
            avatar.flipped = true;
        }
        if (velocity.x > 0) {
            avatar.flipped = false;
        }

        // check for new state
        if (!avatar.stateName.equals(lastState)) {
            avatar.stateTime = 0;
        }

    }

    private void resolveMovement(float delta) {
        velocity.set(0, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            velocity.sub(Velocity.X);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            velocity.add(Velocity.X);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            velocity.sub(Velocity.Y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity.add(Velocity.Y);
        }
        velocity.setLength(delta * MOVE_SPEED);
        // body.center.add(velocity.x, velocity.y);
    }
}
