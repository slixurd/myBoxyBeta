package box.box;

import java.io.IOException;
import java.io.InputStream;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.EntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
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
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class MyGameBetaActivity extends SimpleBaseGameActivity  implements OnClickListener ,IOnSceneTouchListener{
	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private ITexture mTexture;
	private ITextureRegion mFaceTextureRegion;
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private ITextureRegion buttonTextureRegion;
	private ITextureRegion lightTextureRegion;
	private ButtonSprite gameStart;
	private ButtonSprite helpButton;
	private ButtonSprite settingButton;
	private ButtonSprite quitButton;
	//=============coming soon====================//
	private BitmapTextureAtlas comingSoonBitmapTextureAtlas;
	private ITextureRegion comingSoonITextureRegion;
	private SmoothSprite comingSoon;
	private boolean CSmodifyFinish=true;
	
	public EngineOptions onCreateEngineOptions() {
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	public void onCreateResources() {
		try {
			this.mTexture = new BitmapTexture(this.getTextureManager(), new IInputStreamOpener() {
				public InputStream open() throws IOException {
					return getAssets().open("pix/main.png");
				}
			});

			this.mTexture.load();
			this.mFaceTextureRegion = TextureRegionFactory.extractFromTexture(this.mTexture);
		} catch (IOException e) {
			Debug.e(e);
		}
		mBitmapTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 512, 128);
		buttonTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "pix/button.png", 0, 0);
		lightTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, this, "pix/button_light.png", 256, 0);
		this.mBitmapTextureAtlas.load();
		
		comingSoonBitmapTextureAtlas= new BitmapTextureAtlas(getTextureManager(), 512, 512);
		comingSoonITextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(comingSoonBitmapTextureAtlas, this, "pix/comingSoon_main.png", 0, 0);
		comingSoonBitmapTextureAtlas.load();
		
	}

	public Scene onCreateScene() {
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		final SmoothSprite bg = new SmoothSprite(0, 0, this.mFaceTextureRegion, this.getVertexBufferObjectManager());
		scene.attachChild(bg);
		//=============setting按钮==================//
		comingSoon= new SmoothSprite(0, -500, comingSoonITextureRegion, getVertexBufferObjectManager());
		final ParallelEntityModifier CSmodifer = new ParallelEntityModifier(
			new SequenceEntityModifier(
				new MoveYModifier(1.3f, -500f, 200f),new MoveYModifier(0.2f,200f,170f),
					new MoveYModifier(0.1f, 170f, 180f),new MoveYModifier(0.1f, 180f, 170f),
						new DelayModifier(1f),new MoveXModifier(0.5f, 0, -480f)),new SequenceEntityModifier(
								new AlphaModifier(1.0f, 0.3f, 0.9f),new AlphaModifier(1.7f, 0.9f, 0.9f),new AlphaModifier(0.5f, 0.9f, 0.3f)));
		
		
		//===========下面先建立4个按钮的监听器===================//
		org.andengine.entity.sprite.ButtonSprite.OnClickListener newGame= new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {	
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				MyGameBetaActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						gameStart.setCurrentTileIndex(0);
						Intent intent= new Intent();
						intent.setClass(MyGameBetaActivity.this, SwitchActivity.class);
						startActivity(intent);
					}
				});
			}
		};
		 
		org.andengine.entity.sprite.ButtonSprite.OnClickListener help=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {			
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				MyGameBetaActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						helpButton.setCurrentTileIndex(0);
						if(CSmodifyFinish){
							scene.attachChild(comingSoon);
							comingSoon.registerEntityModifier(CSmodifer);
							CSmodifyFinish=false;
							}
					}
				});
			}
		};
		
		org.andengine.entity.sprite.ButtonSprite.OnClickListener setting=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {			
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				MyGameBetaActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						settingButton.setCurrentTileIndex(0);
						
					}
				});
			}
		};

		final org.andengine.entity.sprite.ButtonSprite.OnClickListener quit=new org.andengine.entity.sprite.ButtonSprite.OnClickListener() {
			public void onClick(ButtonSprite arg0, float arg1, float arg2) {
				MyGameBetaActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						quitButton.setCurrentTileIndex(0);
						finish();
					}
				});
			}
			
		};


		//========下面建立按钮=========================//
		gameStart= new ButtonSprite(117, 243, buttonTextureRegion, lightTextureRegion, buttonTextureRegion,getVertexBufferObjectManager(),newGame)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getMotionEvent().getAction())
				{
				case TouchEvent.ACTION_UP:
					this.setCurrentTileIndex(0);
					break;
				case TouchEvent.ACTION_DOWN:
					this.setCurrentTileIndex(1);
					break;
				}
				// TODO Auto-generated method stub
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		helpButton= new ButtonSprite(117, 415, buttonTextureRegion, lightTextureRegion, getVertexBufferObjectManager(), help)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getMotionEvent().getAction())
				{
				case TouchEvent.ACTION_UP:
					this.setCurrentTileIndex(0);
					break;
				case TouchEvent.ACTION_DOWN:
					this.setCurrentTileIndex(1);
					break;
				}
				// TODO Auto-generated method stub
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		settingButton= new ButtonSprite(117, 325, buttonTextureRegion, lightTextureRegion, getVertexBufferObjectManager(), setting)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getMotionEvent().getAction())
				{
				case TouchEvent.ACTION_UP:
					this.setCurrentTileIndex(0);
					break;
				case TouchEvent.ACTION_DOWN:
					this.setCurrentTileIndex(1);
					break;
				}
				// TODO Auto-generated method stub
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		quitButton = new ButtonSprite(117, 500, buttonTextureRegion, lightTextureRegion, getVertexBufferObjectManager(), quit)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				switch(pSceneTouchEvent.getMotionEvent().getAction())
				{
				case TouchEvent.ACTION_UP:
					this.setCurrentTileIndex(0);
					break;
				case TouchEvent.ACTION_DOWN:
					this.setCurrentTileIndex(1);
					break;
				case TouchEvent.ACTION_OUTSIDE:
					this.setCurrentTileIndex(0);
				break;
				}
				// TODO Auto-generated method stub
				return super
						.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		scene.attachChild(gameStart);
		scene.attachChild(helpButton);
		scene.attachChild(settingButton);
		scene.attachChild(quitButton);
		scene.registerTouchArea(quitButton);
		scene.registerTouchArea(settingButton);
		scene.registerTouchArea(helpButton);
		scene.registerTouchArea(gameStart);
		
		scene.setOnSceneTouchListener(this);
		return scene;
	}
	public void onClick(final ButtonSprite buttonSprite, float arg1, float arg2) {
			runOnUiThread(new Runnable() {
			public void run() {
				if(buttonSprite==gameStart )
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


	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		// TODO Auto-generated method stub
		switch(arg1.getAction()){
		case TouchEvent.ACTION_UP:
			gameStart.setCurrentTileIndex(0);
			helpButton.setCurrentTileIndex(0);
			settingButton.setCurrentTileIndex(0);
			quitButton.setCurrentTileIndex(0);
			Debug.d("lalalalalal" + gameStart.getCurrentTileIndex());
		}
		return false;
	}
	
	
}