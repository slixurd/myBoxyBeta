package box.box;

import org.andengine.entity.modifier.EntityModifierList;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.view.animation.AlphaAnimation;

public class CubeSprite extends SmoothSprite{

	public CubeSprite(float pX, float pY, float pWidth, float pHeight,
			ITextureRegion pTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager) {
		super(pX, pY, pWidth, pHeight, pTextureRegion, pVertexBufferObjectManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onAreaTouched(TouchEvent Te,
			float pTouchAreaLocalX, float pTouchAreaLocalY) {
		// TODO Auto-generated method stub
		if(Te.isActionDown())
		{
			
		}
		return true;
	}
	
	

}
