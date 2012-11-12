package box.box;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

public class Boxy_refresh extends Boxy_Object implements OnClickListener{
	private static BitmapTextureAtlas mBitmapTextureAtlas;
	private static ITextureRegion buttonTextureRegion;
	private static ITextureRegion lightTextureRegion;
	public static ButtonSprite refreshButton;
	private static boolean ispressed=false;
	public Boxy_refresh(MyBoxyBetaActivity p) {
		super(p);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		mBitmapTextureAtlas = new BitmapTextureAtlas(parent.getTextureManager(), 64, 128,TextureOptions.DEFAULT);
		buttonTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, parent, "refresh_game.png", 0, 0);
		lightTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, parent, "refresh_pressed_game.png", 0, 64);
		this.mBitmapTextureAtlas.load();
		
		
		refreshButton= new ButtonSprite(400, 10, buttonTextureRegion, lightTextureRegion, parent.getVertexBufferObjectManager(),this);
		parent.mScene.attachChild(refreshButton);
		parent.mScene.registerTouchArea(refreshButton);
	}
	public void onClick(ButtonSprite arg0, float arg1, float arg2) {
	//	if(!ispressed)
				refreshScene();
	}
	public void refreshScene(){
		parent.mScene.registerUpdateHandler(new IUpdateHandler() {
			
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		parent.runOnUpdateThread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				Debug.d("beiju");
				parent.releaseCube();
				parent.level.DestroyLevel();	
				parent.level=new Boxy_Level(parent.level_index,parent);
				parent.level.InitialObject();
				ispressed=false;	
				
			}
		});
	}
}
