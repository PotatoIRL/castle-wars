package com.phfl.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;
import com.phfl.game.asset.AnimationMap;
import com.phfl.game.asset.loader.AnimationMapLoader;
import com.phfl.game.asset.loader.StringLoader;

public class GameAssets {
    static {
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GameAssets.class);
    private static AssetManager manager = null;

    public static void contextualize(AssetManager newManager) {
        if (manager == null) {
            if (newManager == null) {
                manager = defaultAssetManager();
            } else {
                manager = newManager;
            }
        } else {
            if (manager != newManager) {
                throw new RuntimeException("Cannot have multiple GameAsset contexts");
            }
        }
    }

    public static void contextualize() {
        GameAssets.contextualize(manager);
    }

    public static <T> T load(String filePath, Class<T> fileType) {
        contextualize();
        if (manager.isLoaded(filePath)) {
            LOGGER.warn("[{}] is already loaded", filePath);
        } else {
            manager.load(filePath, fileType);
            manager.finishLoading();
        }
        return manager.get(filePath, fileType);
    }

    public static <T> T get(String filePath, Class<T> fileType) {
        contextualize();
        if (!manager.isLoaded(filePath)) {
            LOGGER.warn("[get] triggered [load] for filePath=[{}], fileType=[{}]", filePath,
                    fileType);
            return load(filePath, fileType);
        }
        return manager.get(filePath, fileType);
    }

    public static void fixBleeding(TextureRegion[][] region) {
        for (TextureRegion[] array : region) {
            fixBleeding(array);
        }
    }

    public static void fixBleeding(TextureRegion[] array) {
        for (TextureRegion texture : array) {
            fixBleeding(texture);
        }
    }

    public static void fixBleeding(Array<TextureRegion> array) {
        for (TextureRegion texture : array) {
            fixBleeding(texture);
        }
    }

    public static void fixBleeding(TextureRegion region) {
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

    private static AssetManager defaultAssetManager() {
        AssetManager defaultManager = new AssetManager();
        FileHandleResolver resolver = defaultManager.getFileHandleResolver();
        defaultManager.setLoader(AnimationMap.class, new AnimationMapLoader(resolver));
        defaultManager.setLoader(String.class, new StringLoader(resolver));
        defaultManager.setLoader(TiledMap.class, new TmxMapLoader(resolver));
        return defaultManager;
    }

}
