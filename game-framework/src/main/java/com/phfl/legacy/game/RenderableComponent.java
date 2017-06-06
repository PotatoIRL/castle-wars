package com.phfl.legacy.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.phfl.game.artemis.components.Body;

public class RenderableComponent {
	// body
	public final Body body;
	
	// state data
	public String state;
	public float stateTime;

	// the image to be drawn this frame
	public TextureRegion renderable;
	public boolean flipped;

	public RenderableComponent() {
		this.state = null;
		this.stateTime = 0f;
		this.renderable = null;
		this.flipped = false;
		this.body = new Body(0,0,0,0);
	}
}
