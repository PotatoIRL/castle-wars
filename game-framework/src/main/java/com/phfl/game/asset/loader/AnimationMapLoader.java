package com.phfl.game.asset.loader;

import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.phfl.game.asset.Animation;
import com.phfl.game.asset.AnimationMap;

public class AnimationMapLoader
    extends SynchronousAssetLoader<AnimationMap, AssetLoaderParameters<AnimationMap>> {

  public AnimationMapLoader(FileHandleResolver resolver) {
    super(resolver);
  }

  @SuppressWarnings("unchecked")
  @Override
  public AnimationMap load(AssetManager assetManager, String fileName, FileHandle file,
      AssetLoaderParameters<AnimationMap> parameter) {
    AnimationMap animationMap = new AnimationMap();

    String yamlString = assetManager.get(manifestName(file), String.class);
    Texture texture = assetManager.get(textureName(file), Texture.class);
    texture.setFilter(TextureFilter.Linear, TextureFilter.Nearest);
    Yaml yaml = new Yaml();

    Map<String, Object> manifest = (Map<String, Object>) yaml.load(yamlString);

    List<Integer> size = (List<Integer>) manifest.get("size");
    int w = size.get(0);
    int h = size.get(1);

    Map<String, Object> animations = (Map<String, Object>) manifest.get("animations");
    for (String animationName : animations.keySet()) {
      Map<String, Object> animation = (Map<String, Object>) animations.get(animationName);
      List<Integer> origin = ((List<Integer>) animation.get("origin"));
      animationMap.put( // add to the animation map
          animationName, // {animationName} =>
          new Animation( // Animation(duration, keyFrames, playMode)
              Float.parseFloat( // duration as float
                  animation.get("duration").toString()),
              createKeyFrames( // create keyFrames
                  texture, // use base texture
                  w, h, // use wxh from size
                  origin.get(0), origin.get(1), // use origin as starting point
                  (int) animation.get("frames")), // number of frames as int
              PlayMode.valueOf( // parse play mode
                  animation.getOrDefault("play-mode", "NORMAL").toString().toUpperCase())));
    }
    return animationMap;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
      AssetLoaderParameters<AnimationMap> parameter) {
    Array<AssetDescriptor> dependencies = new Array<AssetDescriptor>();
    dependencies.add(new AssetDescriptor<>(textureName(file), Texture.class));
    dependencies.add(new AssetDescriptor<>(manifestName(file), String.class));
    return dependencies;
  }

  private String textureName(FileHandle file) {
    return String.format("%s/%s.png", file.path(), file.name());
  }

  private String manifestName(FileHandle file) {
    return String.format("%s/%s.yml", file.path(), file.name());
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
