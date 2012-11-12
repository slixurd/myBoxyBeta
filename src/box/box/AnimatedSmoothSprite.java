package box.box;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class AnimatedSmoothSprite  extends AnimatedSprite{

	
	public boolean can_be_moved = true;
	
	// TODO Auto-generated constructor stub
public AnimatedSmoothSprite(float pX, float pY, float pWidth, float pHeight,
		TiledTextureRegion pTextureRegion,
		VertexBufferObjectManager pVertexBufferObjectManager) {
	super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
	// TODO Auto-generated constructor stub
}
public AnimatedSmoothSprite(float pX, float pY, TiledTextureRegion pTextureRegion,
		VertexBufferObjectManager pVertexBufferObjectManager) {
	super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
	// TODO Auto-generated constructor stub
}

@Override
protected void preDraw(GLState pGLState, Camera pCamera) {
	// TODO Auto-generated method stub
	super.preDraw(pGLState, pCamera);
    pGLState.enableDither();
    pGLState.enableCulling();
    this.getTextureRegion().getTexture().bind(pGLState);

    this.mSpriteVertexBufferObject.bind(pGLState, this.mShaderProgram);
}
}
