package com.phfl.game.cw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.phfl.game.GameWorld;
import com.phfl.game.cw.entities.BaseHuman;
import com.phfl.game.physics.PhysicsEngine;
import com.phfl.game.physics.PlatformerPhysicsEngine;

public class CWApp extends ApplicationAdapter {
  private static final Logger LOGGER = LoggerFactory.getLogger(CWApp.class);

  private static final float FPS = 60;
  private GameWorld world;
  private OrthographicCamera gameCamera;

  @Override
  public void resize(int width, int height) {
    gameCamera.setToOrtho(false);
    gameCamera.update();
  }

  @Override
  public void create() {
    LOGGER.info("create");

    // create world
    world = new GameWorld(FPS, gameCamera = defaultCamera(), defaultPhysicsImplementation());

    // bind input
    world.addController(new CameraController(gameCamera));

    // load map
    world.loadMap("test-map.tmx");

    // load knight
    new BaseHuman(world);
  }

  @Override
  public void render() {
    world.step();
  }

  private PhysicsEngine defaultPhysicsImplementation() {
    return new PlatformerPhysicsEngine();
  }

  private OrthographicCamera defaultCamera() {
    OrthographicCamera cam = new OrthographicCamera();
    cam.setToOrtho(false);
    return cam;
  }

}
