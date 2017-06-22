package com.phfl.game.artemis.systems;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.phfl.game.GameWorld;
import com.phfl.game.artemis.components.Avatar;
import com.phfl.game.artemis.components.Body;
import com.phfl.game.asset.Animation;
import com.phfl.game.asset.AnimationMap;

public class GameRenderingSystem extends IteratingSystem {
  private static final Logger LOGGER = LoggerFactory.getLogger(GameRenderingSystem.class);

  GameAssetSystem assetSystem;

  ComponentMapper<Avatar> avatarMap;
  ComponentMapper<Body> bodyMap;

  private SpriteBatch batch;
  private Camera camera;

  public GameRenderingSystem(Camera camera) {
    super(Aspect.all(Avatar.class, Body.class));
    this.batch = new SpriteBatch();
    this.camera = camera;
  }

  @Override
  protected void begin() {
    // 1. clear screen
    Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

    // 2. begin batch
    batch.setProjectionMatrix(camera.combined);
    batch.begin();

    // 3. render map first
    renderMap(((GameWorld) world).getMap());
  }

  @Override
  protected void process(int entityId) {
    // 4. render each entity
    renderEntity(entityId);
  }

  @Override
  protected void end() {
    // 5. end batch
    batch.end();
  }

  private void renderEntity(int entityId) {
    try {
      Avatar avatar = avatarMap.get(entityId);
      Body body = bodyMap.get(entityId);

      AnimationMap animations = assetSystem.getAnimationMap(avatar.name);
      Animation animation = animations.get(avatar.stateName);
      TextureRegion image = animation.getKeyFrame(avatar.stateTime);

      boolean flip = avatar.flipped;
      if (body != null) {
        if (image != null) {
          batch.draw(image, flip ? body.x2() : body.x(), body.y(), flip ? -body.w() : body.w(),
              body.h());
        }
      }
    } catch (Exception e) {
      LOGGER.error("Failed to render entity#{}: {}", entityId, e.getMessage());
      e.printStackTrace();
    }
  }

  private void renderMap(TiledMap map) {
    for (Iterator<MapLayer> iter = map.getLayers().iterator(); iter.hasNext();) {
      MapLayer layer = iter.next();
      if (layer instanceof TiledMapTileLayer) {
        renderTiledMapTileLayer((TiledMapTileLayer) layer);
      }
    }
  }

  private void renderTiledMapTileLayer(TiledMapTileLayer layer) {
    int w = layer.getWidth();
    int h = layer.getHeight();
    float th = layer.getTileHeight();
    float tw = layer.getTileWidth();

    for (int x = 0; x < w; x++) {
      for (int y = 0; y < h; y++) {
        float tx = x * tw;
        float ty = y * th;
        renderCell(layer.getCell(x, y), tx, ty, tw, th);
      }
    }
  }

  private void renderCell(Cell cell, float x, float y, float w, float h) {
    TiledMapTile tile = cell.getTile();
    TextureRegion tr = tile.getTextureRegion();
    batch.draw(tr, x + tile.getOffsetX(), y + tile.getOffsetY(), w, h);
  }

}
