package com.phfl.game.cw;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.phfl.game.input.GameController;

public class CameraController implements GameController {
  private static final float PAN_SPEED = 500;
  private static final Vector3 cameraVelocityDirection = new Vector3();

  private Camera camera;

  public CameraController(Camera camera) {
    this.camera = camera;
  }

  public static void resolveCameraMovement() {
    cameraVelocityDirection.set(Vector3.Zero);
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
      cameraVelocityDirection.add(Vector3.X.cpy().scl(-1));
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
      cameraVelocityDirection.add(Vector3.X);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
      cameraVelocityDirection.add(Vector3.Y.cpy().scl(-1));
    }
    if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
      cameraVelocityDirection.add(Vector3.Y);
    }
    cameraVelocityDirection.nor();
  }

  @Override
  public void processController(float delta) {
    resolveCameraMovement();
    cameraVelocityDirection.scl(delta * PAN_SPEED);
    camera.translate(cameraVelocityDirection);
    camera.update();
  }
}
