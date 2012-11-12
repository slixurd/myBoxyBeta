package box.box;

import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.EntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.ButtonSprite.OnClickListener;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;

import android.provider.ContactsContract.RawContacts.Entity;
import android.widget.Toast;

public class Boxy_GameFinish implements OnClickListener{
private static MyBoxyBetaActivity parent;
private static SmoothSprite glass;
private static SmoothSprite goldButton;
private  static SmoothSprite silverButton;
private static SmoothSprite copperButton;
private static ButtonSprite retryButton;
private static ButtonSprite returnButton;
private static ButtonSprite nextButton;
private static SmoothSprite coverSmoothSprite;
private static BitmapTextureAtlas finishButtonBitmapTextureAtlas;
private static ITextureRegion glassITextureRegion;
private static ITextureRegion goldButtonITextureRegion;
private static ITextureRegion silverButtonITextureRegion;
private static ITextureRegion copperButtonITextureRegion;
private static ITextureRegion nextButtonITextureRegion;
private static ITextureRegion menuButtonITextureRegion;
private static ITextureRegion coverITextureRegion;
private static BitmapTextureAtlas coverAtlas;
private boolean isOK=true;
public Boxy_GameFinish(float px,float py,MyBoxyBetaActivity p){
		parent = p;
		//============各种资源的载入===================//
		finishButtonBitmapTextureAtlas = new BitmapTextureAtlas(parent.getTextureManager(), 512, 512);
		goldButtonITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "golden_star_game.png", 0, 330);
		silverButtonITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "silver_star_game.png", 100, 330);
		copperButtonITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "tong_star_game.png", 200, 330);
		menuButtonITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "menu_game.png", 0, 421);
		nextButtonITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "next_game.png", 255, 421);
		coverAtlas=new BitmapTextureAtlas(parent.getTextureManager(), 512,1024);
		coverITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(coverAtlas, parent, "win_cover_game.png", 0, 0);
		coverAtlas.load();
		finishButtonBitmapTextureAtlas.load();
		//===================各种延迟,配合前面碰撞的动画===========================//
		SequenceEntityModifier delayGlass= new SequenceEntityModifier(new AlphaModifier(0.002f, 0f, 1f));
		SequenceEntityModifier time0Delay= new SequenceEntityModifier(new AlphaModifier(0.001f,1f,0f),new AlphaModifier(0.5f, 0.5f, 1f));
		SequenceEntityModifier time1Delay= new SequenceEntityModifier(new AlphaModifier(0.001f,0f,0f),new AlphaModifier(0.5f, 0.5f, 1f));
		SequenceEntityModifier time2Delay= new SequenceEntityModifier(new AlphaModifier(0.001f,0f,0f),new AlphaModifier(0.5f, 0.5f, 1f));
		SequenceEntityModifier time3Delay= new SequenceEntityModifier(new DelayModifier(1f),new AlphaModifier(0.5f, 0.5f, 1f));
		SequenceEntityModifier time4Delay= new SequenceEntityModifier(new DelayModifier(1f),new AlphaModifier(0.5f, 0.5f, 1f));
		
		//============玻璃===================================================//
		glassITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(finishButtonBitmapTextureAtlas, parent, "broken_game.png", 0, 0);
		glass= new SmoothSprite(px-120, py-150, glassITextureRegion, parent.getVertexBufferObjectManager());
		glass.setRotation((float) (Math.random()*360-180));
		parent.mScene.attachChild(glass);
		glass.registerEntityModifier(delayGlass);
		
		//=============三个星星==================//
		 goldButton= new SmoothSprite(70, 250, goldButtonITextureRegion, parent.getVertexBufferObjectManager());
		 silverButton = new SmoothSprite(210, 250, silverButtonITextureRegion, parent.getVertexBufferObjectManager());
		 copperButton= new SmoothSprite(350, 250, copperButtonITextureRegion, parent.getVertexBufferObjectManager());
		goldButton.setRotation((float) (Math.random()*50-25));
		silverButton.setRotation((float) (Math.random()*50-25));
		copperButton.setRotation((float) (Math.random()*50-25));

		
		parent.mScene.attachChild(copperButton);
		parent.mScene.attachChild(silverButton);
		parent.mScene.attachChild(goldButton);
		goldButton.registerEntityModifier(time0Delay);
		silverButton.registerEntityModifier(time1Delay);
		copperButton.registerEntityModifier(time2Delay);
		//==========cover===========//
		coverSmoothSprite=new SmoothSprite(0, 0, coverITextureRegion, parent.getVertexBufferObjectManager());
		
		//=======================三个按钮==================//
//		OnClickListener retryClick= new OnClickListener() {
//			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
//				parent.runOnUiThread(new Runnable() {
//					public void run() {
//
//					}
//				});
//			}
//		};
		OnClickListener returnClick = new OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				parent.runOnUiThread(new Runnable() {
					public void run() {
							parent.finish();
					}
				});
			}
		};
		
		OnClickListener nextClick = new OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				parent.runOnUpdateThread(new Runnable() {
					public void run() {
						if(isOK == true){
							parent.releaseCube();
							parent.level.DestroyLevel();
							
							parent.level_index++;
						Debug.d("onclick");
						parent.level=new Boxy_Level(parent.level_index,parent);
						Debug.d("onclick2");
						DestroyFinish();
						Debug.d("onclick3");
						
						
//						parent.level = new Boxy_Level(parent.level_index, parent);
						parent.level.InitialObject();
						Debug.d("onclick4");
						parent.setFinish=true;
//						Boxy_refresh re=new Boxy_refresh(parent);
						isOK=false;
						parent.ending=false;
						parent.refresh.refreshButton.setVisible(true);
						
						Debug.d("onclick4.5");
						
						}
					}
				});
			}
		};
//		 retryButton = new ButtonSprite(70, 400, goldButtonITextureRegion, parent.getVertexBufferObjectManager(), retryClick);
		 returnButton = new ButtonSprite(120, 400, menuButtonITextureRegion, parent.getVertexBufferObjectManager(), returnClick);
		 returnButton.setAlpha(0);
		 nextButton = new ButtonSprite(120, 520, nextButtonITextureRegion, parent.getVertexBufferObjectManager(), nextClick);
		 nextButton.setAlpha(0);
		 
		 parent.mScene.attachChild(coverSmoothSprite);
		parent.mScene.attachChild(nextButton);
		parent.mScene.attachChild(returnButton);
		returnButton.registerEntityModifier(time3Delay);
		time3Delay.reset();
		
		nextButton.registerEntityModifier(time4Delay);
//		parent.mScene.attachChild(retryButton);
		parent.mScene.registerTouchArea(nextButton);
		parent.mScene.registerTouchArea(returnButton);
//		parent.mScene.registerTouchArea(retryButton);
		parent.refresh.refreshButton.setVisible(false);
}

public void onClick(ButtonSprite arg0, float arg1, float arg2) {
	// TODO Auto-generated method stub
	
}
public void DestroyFinish(){
	parent.runOnUiThread(new Runnable() {
		
		public void run() {
			parent.mScene.unregisterTouchArea(nextButton);
			parent.mScene.unregisterTouchArea(returnButton);
//			parent.mScene.unregisterTouchArea(retryButton);
			parent.mScene.detachChild(nextButton);
			parent.mScene.detachChild(returnButton);
//			parent.mScene.detachChild(retryButton);
			parent.mScene.detachChild(copperButton);
			parent.mScene.detachChild(silverButton);
			parent.mScene.detachChild(goldButton);
			parent.mScene.detachChild(glass);
			parent.mScene.detachChild(coverSmoothSprite);

		}
	});

}

}
