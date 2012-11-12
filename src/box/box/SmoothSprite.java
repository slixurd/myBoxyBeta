package box.box;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SmoothSprite extends Sprite{

	public boolean can_be_moved = true;
	public boolean has_touched = false;
	public boolean is_shaking = false;
		
		// TODO Auto-generated constructor stub
	public SmoothSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		this.can_be_moved = true;
		// TODO Auto-generated constructor stub
	}
	
	public SmoothSprite(float pX, float pY,	ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
		this.can_be_moved = true;
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
