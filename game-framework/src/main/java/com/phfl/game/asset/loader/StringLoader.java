package com.phfl.game.asset.loader;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

public class StringLoader extends SynchronousAssetLoader<String, AssetLoaderParameters<String>>{
	public StringLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public String load(AssetManager assetManager, String fileName, FileHandle file,
			AssetLoaderParameters<String> parameter) {
		return file.readString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file,
			AssetLoaderParameters<String> parameter) {
		return null;
	}

}
