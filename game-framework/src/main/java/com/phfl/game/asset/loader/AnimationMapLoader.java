package com.phfl.game.asset.loader;

import java.util.Map;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.phfl.game.asset.Animation;
import com.phfl.game.asset.AnimationMap;
import com.phfl.pyxel.PyxelFile;
import com.phfl.pyxel.model.AnimationData;
import com.phfl.pyxel.model.CanvasData;

public class AnimationMapLoader
    extends SynchronousAssetLoader<AnimationMap, AssetLoaderParameters<AnimationMap>> {

  public AnimationMapLoader(FileHandleResolver resolver) {
    super(resolver);
  }

  @Override
  public AnimationMap load(AssetManager assetManager, String fileName, FileHandle file,
      AssetLoaderParameters<AnimationMap> parameter) {

    String pyxelFilePath = pyxelName(file);
    PyxelFile pyxelFile = assetManager.get(pyxelFilePath);

    byte[] imageData = pyxelFile.image;
    Texture texture = new Texture(new Pixmap(imageData, 0, imageData.length));

    Map<String, AnimationData> animations = pyxelFile.document.animations;
    AnimationMap animationMap = new AnimationMap();
    for (AnimationData data : animations.values()) {
      Animation animation = new Animation(data.frameDuration / 1000f,
          createKeyFrames(pyxelFile, texture, data), PlayMode.LOOP);
      animationMap.put(data.name, animation);
    }
    return animationMap;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
      AssetLoaderParameters<AnimationMap> parameter) {
    Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
    dependencies.add(new AssetDescriptor<>(pyxelName(file), PyxelFile.class));
    return dependencies;
  }

  private String pyxelName(FileHandle file) {
    return String.format("%s.pyxel", file.path());
  }

  private Array<? extends TextureRegion> createKeyFrames(PyxelFile pyxelFile, Texture texture,
      AnimationData data) {
    CanvasData canvas = pyxelFile.document.canvas;

    int w = canvas.tileWidth;
    int h = canvas.tileHeight;

    int rw = canvas.width / canvas.tileWidth;

    int c = data.baseTile % rw;
    int r = data.baseTile / rw;

    int ox = c * w;
    int oy = r * h;

    return createKeyFrames(texture, w, h, ox, oy, data.length);
  }

  private Array<TextureRegion> createKeyFrames(Texture texture, int w, int h, int ox, int oy,
      int frames) {
    Array<TextureRegion> out = new Array<TextureRegion>();
    TextureRegion tr;
    for (int i = 0; i < frames; i++) {
      // TODO: support wrapping
      int x = ox + i * w;
      int y = oy;
      tr = new TextureRegion(texture, x, y, w, h);
      fixBleeding(tr);
      out.add(tr);
    }
    return out;
  }

  private static void fixBleeding(TextureRegion region) {
    float fix = 0.01f;

    float x = region.getRegionX();
    float y = region.getRegionY();
    float width = region.getRegionWidth();
    float height = region.getRegionHeight();
    float invTexWidth = 1f / region.getTexture().getWidth();
    float invTexHeight = 1f / region.getTexture().getHeight();
    region.setRegion((x + fix) * invTexWidth, (y + fix) * invTexHeight,
        (x + width - fix) * invTexWidth, (y + height - fix) * invTexHeight); // Trims
                                                                             // region
  }
}
