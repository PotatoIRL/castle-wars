package com.phfl.game.asset.loader;

import java.io.IOException;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.phfl.pyxel.PyxelFile;

public class PyxelFileLoader
    extends SynchronousAssetLoader<PyxelFile, AssetLoaderParameters<PyxelFile>> {

  public PyxelFileLoader(FileHandleResolver resolver) {
    super(resolver);
  }

  @Override
  public PyxelFile load(AssetManager assetManager, String fileName, FileHandle file,
      AssetLoaderParameters<PyxelFile> parameter) {
    try {
      return new PyxelFile(file.read());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
      AssetLoaderParameters<PyxelFile> parameter) {
    return null;
  }
}
