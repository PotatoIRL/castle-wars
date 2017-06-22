package com.phfl.legacy.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.IntMap.Values;
import com.phfl.game.GameWorld;
import com.phfl.game.artemis.components.Body;

@Deprecated
public class LegacyGameRenderingSystem {

  public static boolean RENDER_DEBUG = true;

  public Camera camera;
  private SpriteBatch batch;
  private ShapeRenderer debugRenderer;

  public LegacyGameRenderingSystem(GameWorld world) {
    batch = new SpriteBatch();
    debugRenderer = new ShapeRenderer();
    camera = defaultCamera();
  }

  private Camera defaultCamera() {
    return new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
  }

  protected void dispose() {
    batch.dispose();
  }

  protected void preProcess(float dt) {
    // Clear screen
    // Gdx.gl.glClearColor(0, 0, 0, 1);
    Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    // Bind to camera
    if (camera != null) {
      batch.setProjectionMatrix(camera.combined);
    }

    // Prepare the thing
    batch.begin();
    // renderMap(world.physics.map);
  }

  protected void process(float dt, Object e) {
    renderEntity(dt, e);
  }

  protected void postProcess(float dt) {
    // Do the thing
    batch.end();
    // Debug hitboxes
    if (RENDER_DEBUG) {
      // renderDebug(world.physics.map, world.entities.values());
    }
  }

  @SuppressWarnings("unused")
  private void renderMap(LegacyGameMap map) {
    if (map == null)
      return;
    for (int x = 0; x < map.w; x++) {
      for (int y = 0; y < map.h; y++) {
        if (map.tiles[x][y] != null && map.tiles[x][y].texture != null) {
          batch.draw(map.tiles[x][y].texture, x * map.s, y * map.s);
        }
      }
    }
  }

  private void renderEntity(float dt, Object e) {
    RenderableComponent avatar = (RenderableComponent) e;
    TextureRegion renderable = avatar.renderable;

    if (renderable == null) {
      return;
    } else {
      Body aabb = avatar.body;
      batch.draw(renderable, (aabb.x() + aabb.w() / 2) - (renderable.getRegionWidth() / 2),
          (aabb.y() + aabb.h() / 2) - (renderable.getRegionHeight() / 2),
          (renderable.getRegionWidth() / 2), (renderable.getRegionHeight() / 2),
          renderable.getRegionWidth(), renderable.getRegionHeight(), (avatar.flipped ? -1 : 1), 1,
          0);
    }
  }

  @SuppressWarnings("unused")
  private void renderDebug(LegacyGameMap map, Values<Object> entities) {
    // Draw
    debugRenderer.setAutoShapeType(true);
    if (camera != null) {
      debugRenderer.setProjectionMatrix(camera.combined);
    }
    debugRenderer.begin();

    // draw map
    if (map != null) {
      debugRenderer.setColor(Color.WHITE);
      debugRenderer.set(ShapeType.Line);
      for (int x = 0; x < map.w; x++) {
        for (int y = 0; y < map.h; y++) {
          if (map.tiles[x][y] != null) {
            // debugRenderer.rect(x * world.physics.map.s, y * map.s, map.s, map.s);
          }
        }
      }
    }

    // draw entities
    debugRenderer.setColor(Color.BLUE);
    entities.reset();
    for (Object e : entities) {
      // if(e.hasComponent(RenderableComponent.class)) {
      // AABB aabb = e.getComponent(RenderableComponent.class).body;
      // debugRenderer.rect(aabb.x, aabb.y, aabb.w, aabb.h);
      // }
    }
    debugRenderer.end();
  }
}
