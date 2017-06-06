package com.phfl.legacy.game;

public abstract class LegacyGamePhysicsSystem {
	public LegacyGameMap map;

	public LegacyGamePhysicsSystem(Class<?> physicsBodyType) {
//		super(physicsBodyType);
	}

	protected void preProcess(float dt) {
		if (this.map == null) {
//			this.skipNextProcess();
		}
	}
}
