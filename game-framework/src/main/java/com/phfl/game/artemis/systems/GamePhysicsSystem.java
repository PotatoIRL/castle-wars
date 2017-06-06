package com.phfl.game.artemis.systems;

import com.artemis.Aspect;
import com.artemis.systems.IteratingSystem;
import com.phfl.game.GameWorld;
import com.phfl.game.artemis.components.Body;
import com.phfl.game.artemis.components.Velocity;
import com.phfl.game.physics.PhysicsEngine;

public class GamePhysicsSystem extends IteratingSystem {

    private PhysicsEngine engine;

    public GamePhysicsSystem(PhysicsEngine engine) {
        super(Aspect.all(Body.class, Velocity.class));
        this.engine = engine;
    }

    @Override
    protected void initialize() {
        world.inject(engine);
    }

    @Override
    protected void begin() {
        engine.map = ((GameWorld) world).getMap();
    }

    @Override
    protected void process(int entityId) {
        if (engine.canProcess(entityId)) {
            engine.process(entityId);
        }
    }

}

