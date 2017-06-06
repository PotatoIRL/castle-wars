package com.phfl.legacy.game.platformer;

public class PlatformerPhysicsComponent {
	public float vx;
	public float vy;
	public int groundedFrames;
	
	public PlatformerPhysicsComponent() {
		this.groundedFrames = 0;
		this.vx = this.vy = 0;
	}
}
