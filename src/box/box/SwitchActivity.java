package box.box;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.util.debug.Debug;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class SwitchActivity extends SimpleBaseGameActivity  implements OnClickListener {
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	//=============coming soon====================//
	private BitmapTextureAtlas comingSoonBitmapTextureAtlas;
	private ITextureRegion comingSoonITextureRegion;
	private SmoothSprite comingSoon;
	private boolean CSmodifyFinish=true;
	//==============//
	private BitmapTextureAtlas selectBitmapTextureAtlas;
	private ITextureRegion selectITextureRegion;
	private SmoothSprite select;
	
	private BitmapTextureAtlas levelBitmapTextureAtlas;
	private ITextureRegion bGITextureRegion;
	private ITextureRegion[] numberITextureRegions;
	private ITextureRegion nullITextureRegion;
	private ButtonSprite[] tutorialButtonSprites;
	private ButtonSprite[] AMButtonSprites;
	private ButtonSprite[] PMButtonSprites;
	private SmoothSprite bgSprite;
	private ButtonSprite homeButtonSprite;
	private ITextureRegion homeITextureRegion;
	public int levelTransport;
	
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("level/");
		
		numberITextureRegions= new ITextureRegion[15];
		AMButtonSprites=new ButtonSprite[15];
		tutorialButtonSprites=new ButtonSprite[8];
		PMButtonSprites=new ButtonSprite[15];
		levelBitmapTextureAtlas=new BitmapTextureAtlas(getTextureManager(), 1024, 1024);
		for(int i=1;i<=15;i++){
			String pathName="level_"+i+".png";
			
			numberITextureRegions[i-1]=BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBitmapTextureAtlas, this, pathName, 0, i*50-50);
		}
		bGITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBitmapTextureAtlas, this, "select_level.png", 500, 0);
		selectITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBitmapTextureAtlas, this, "selected.png", 0	, 750);
		nullITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBitmapTextureAtlas, this, "level_null.png", 50, 0);
		homeITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(levelBitmapTextureAtlas, this, "button.png", 0, 850);
		levelBitmapTextureAtlas.load();
		
		
		selectBitmapTextureAtlas=new BitmapTextureAtlas(getTextureManager(), 64, 64);
		selectITextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(selectBitmapTextureAtlas, this, "selected.png", 0, 0);
		selectBitmapTextureAtlas.load();
		
	}

	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		bgSprite=new SmoothSprite(0, 0, bGITextureRegion, getVertexBufferObjectManager());
		scene.attachChild(bgSprite);
		org.andengine.entity.sprite.ButtonSprite.OnClickListener[] tutorialClick;
		tutorialClick= new org.andengine.entity.sprite.ButtonSprite.OnClickListener[5];
		//======================因为无法传递给内部类..所以手动写数组吧...=============//
		tutorialClick[0]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",1 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		tutorialClick[1]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",2 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		tutorialClick[2]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",3 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		tutorialClick[3]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",4 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		tutorialClick[4]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",5 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		
		//=========================AMCLICK==========================//
		org.andengine.entity.sprite.ButtonSprite.OnClickListener[] AMClick;
		AMClick= new org.andengine.entity.sprite.ButtonSprite.OnClickListener[15];
		AMClick[0]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",6 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[1]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",7 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[2]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",8 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[3]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",9 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[4]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",10 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[5]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",11 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[6]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",12 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[7]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",13 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[8]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",14);
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		AMClick[9]=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				Intent intent=new Intent();
				intent.setClass(SwitchActivity.this, MyBoxyBetaActivity.class);
				 Bundle bundle = new Bundle();  
				 bundle.putInt("level",15 );
				intent.putExtras(bundle);
				startActivity(intent);				
			}
		};
		
		
		
		select=new SmoothSprite(0, 0, selectITextureRegion, getVertexBufferObjectManager());
		select.setVisible(false);
		scene.attachChild(select);

		//修改,记得...增加以后....
		
		for(int i =1;i<=8;i++){
				if(i<=4)
					tutorialButtonSprites[i-1]=new ButtonSprite(180+(i-1)*80, 100, numberITextureRegions[i-1], this.getVertexBufferObjectManager(),tutorialClick[i-1]){

						@Override
						public boolean onAreaTouched(
								TouchEvent pSceneTouchEvent,
								float pTouchAreaLocalX, float pTouchAreaLocalY) {
							switch(pSceneTouchEvent.getMotionEvent().getAction())
							{
							case TouchEvent.ACTION_DOWN:
								select.setPosition(this.mX, this.mY);
								select.setVisible(true);								
								break;
							case TouchEvent.ACTION_UP:
								select.setVisible(false);
								break;
							}
							
							
							return super
									.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
						}
					
				};
				else if(i==5)
					tutorialButtonSprites[i-1]=new ButtonSprite(180+(i-5)*80, 180, numberITextureRegions[i-1], this.getVertexBufferObjectManager(),tutorialClick[i-1]){

					@Override
					public boolean onAreaTouched(
							TouchEvent pSceneTouchEvent,
							float pTouchAreaLocalX, float pTouchAreaLocalY) {
						switch(pSceneTouchEvent.getMotionEvent().getAction())
						{
						case TouchEvent.ACTION_DOWN:
							select.setPosition(this.mX, this.mY);
							select.setVisible(true);								
							break;
						case TouchEvent.ACTION_UP:
							select.setVisible(false);
							break;
						}
						
						
						return super
								.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
					}
				
			};
				else if(i>5)
					tutorialButtonSprites[i-1]=new ButtonSprite(180+(i-5)*80, 180, nullITextureRegion, getVertexBufferObjectManager());
				scene.attachChild(tutorialButtonSprites[i-1]);
				scene.registerTouchArea(tutorialButtonSprites[i-1]);
		}
		for(int i=1;i<=15;i++){
			if(i<=5)
				AMButtonSprites[i-1]=new ButtonSprite(20+(i-1)*80, 260, numberITextureRegions[i-1], getVertexBufferObjectManager(),AMClick[i-1]){

				@Override
				public boolean onAreaTouched(
						TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getMotionEvent().getAction())
					{
					case TouchEvent.ACTION_DOWN:
						select.setPosition(this.mX, this.mY);
						select.setVisible(true);								
						break;
					case TouchEvent.ACTION_UP:
						select.setVisible(false);
						break;
					}
					
					
					return super
							.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			
		};
			else if(i>5&&i<=10)
				AMButtonSprites[i-1]=new ButtonSprite(20+(i-6)*80, 340, numberITextureRegions[i-1], getVertexBufferObjectManager(),AMClick[i-1]){

				@Override
				public boolean onAreaTouched(
						TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					switch(pSceneTouchEvent.getMotionEvent().getAction())
					{
					case TouchEvent.ACTION_DOWN:
						select.setPosition(this.mX, this.mY);
						select.setVisible(true);								
						break;
					case TouchEvent.ACTION_UP:
						select.setVisible(false);
						break;
					}
					
					
					return super
							.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
				}
			
		};
			else if(i>=11)
				AMButtonSprites[i-1]=new ButtonSprite(20+(i-11)*80, 420, numberITextureRegions[i-1], getVertexBufferObjectManager(),AMClick[i-1]);
			scene.attachChild(AMButtonSprites[i-1]);
			scene.registerTouchArea(AMButtonSprites[i-1]);

		}
		for(int i=1;i<=15;i++){	
		if(i<=5)
			PMButtonSprites[i-1]=new ButtonSprite(100+(i-1)*80, 500, numberITextureRegions[i-1], getVertexBufferObjectManager());
		else if(i>5&&i<=10)
			PMButtonSprites[i-1]=new ButtonSprite(100+(i-6)*80, 580, numberITextureRegions[i-1], getVertexBufferObjectManager());
		else if(i>=11)
			PMButtonSprites[i-1]=new ButtonSprite(100+(i-11)*80, 660, numberITextureRegions[i-1], getVertexBufferObjectManager());
		scene.attachChild(PMButtonSprites[i-1]);
		scene.registerTouchArea(PMButtonSprites[i-1]);


		}
			org.andengine.entity.sprite.ButtonSprite.OnClickListener homeClick=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {			
				public void onClick(ButtonSprite arg0, float arg1, float arg2) {
					SwitchActivity.this.runOnUiThread(new Runnable() {
						public void run() {
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Debug.d("wow");
							finish();
							
							}
						}); 
					}};
		homeButtonSprite=new ButtonSprite(340, 740,homeITextureRegion	, this.getVertexBufferObjectManager(),homeClick);
		scene.attachChild(homeButtonSprite);
		scene.registerTouchArea(homeButtonSprite);
//		scene.setOnSceneTouchListener(this);
		return scene;
	}
	public void onClick(final ButtonSprite buttonSprite, float arg1, float arg2) {
			runOnUiThread(new Runnable() {
			public void run() {
				if(buttonSprite==PMButtonSprites[1] )
						Toast.makeText(getApplicationContext(), "start", 1).show();
				else 
					Toast.makeText(getApplicationContext(), "stt", 1).show();
			}
		});
	}
	@Override
	public void startActivity(Intent intent) {
		
					super.startActivity(intent);
		
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}