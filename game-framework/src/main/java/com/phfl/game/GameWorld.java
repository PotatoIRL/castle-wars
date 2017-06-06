package com.phfl.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Component;
import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.phfl.game.artemis.systems.GameAssetSystem;
import com.phfl.game.artemis.systems.GameControllerSystem;
import com.phfl.game.artemis.systems.GamePhysicsSystem;
import com.phfl.game.artemis.systems.GameRenderingSystem;
import com.phfl.game.artemis.systems.TiledMapSystem;
import com.phfl.game.input.GameController;
import com.phfl.game.physics.PhysicsEngine;

public class GameWorld extends World {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameWorld.class);

    private static float MAX_STEP_TIME = 1f;
    private static boolean MANUAL_STEP = false;

    private float fps;
    private float timeStep;
    private float timeAccumulator;

    TiledMapSystem mapSystem;


    public GameWorld(float fps, Camera camera, PhysicsEngine physics) {
        super(new WorldConfigurationBuilder()
                .with(
                        new TiledMapSystem(),
                        new GameControllerSystem(),
                        new GameAssetSystem(),
                        new GamePhysicsSystem(physics),
                        new GameRenderingSystem(camera))
                .build());
        setFps(fps);
        inject(this);
        LOGGER.info("constructed");
    }

    private void setFps(float fps) {
        this.fps = fps;
        this.timeStep = 1f / fps;
    }

    public synchronized void step() {
        float dt = Gdx.graphics.getRawDeltaTime();
        if (MANUAL_STEP) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
                processFrame(timeStep);
            }
            return;
        }
        timeAccumulator += Math.min(dt, MAX_STEP_TIME);
        while (timeAccumulator >= timeStep) {
            processFrame(timeStep);
            timeAccumulator -= timeStep;
        }
    }

    private void processFrame(float frameTime) {
        setDelta(frameTime);
        process();
    }

    public float getFps() {
        return fps;
    }

    public TiledMap getMap() {
        return mapSystem.map;
    }

    public void loadMap(String mapName) {
        mapSystem.map = GameAssets.load(mapName, TiledMap.class);
    }

    public World entityWorld() {
        return this;
    }

    public void addController(GameController controller) {
        getSystem(GameControllerSystem.class).addController(controller);
    }

    public <T extends Component> T createComponent(int entityID, Class<T> type) {
        return getMapper(type).create(entityID);
    }

}
