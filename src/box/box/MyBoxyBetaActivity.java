package box.box;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnAreaTouchListener;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.debug.Debug;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class MyBoxyBetaActivity extends SimpleBaseGameActivity implements IAccelerationListener, IOnAreaTouchListener, IOnSceneTouchListener{

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	public long delayTime = 2000;
	private final int helpNUM=4;
	public Boxy_Level level;
	public int level_index=1;
	public boolean started = true;
	public boolean setFinish=true;
	public boolean ending=false;
	public int Cube_touched_num = -1;
	
	private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1f, 0.5f, 0.5f);
	private static final FixtureDef CUBE_DEF = PhysicsFactory.createFixtureDef(1f, 0.1f, 0.1f);
	public boolean Cube_detached = false;
	public Vector2 velocity;
	
	public Boxy_GameFinish BGF;
	
	//public Boxy_NormalBoard test = new Boxy_NormalBoard(20, 50, 56, 45, pTextureRegion, pVertexBufferObjectManager, BoxyActivity);
	
	//BitmapTextureAtlas
	private BitmapTextureAtlas CubeTextureAtlas;
	private BitmapTextureAtlas BackGroundTextureAtlas;
	private BitmapTextureAtlas LevelTeachingAtlas;
	
	//button
	public static Boxy_refresh refresh;
	
	//
	public float center0X;
	public float center0Y;
	public float center1X;
	public float center1Y;
	public int direction0 = 0;   //0 水平推射  -1弹射  1 推射.
	public int direction1 = 0;
	public float XRate = 0.15f;
	public float YRate = 0.15f;
	
	public float dy0 = 0;
	public float dx0 = 0;
	public float dx1 = 0;
	public float dy1 = 0;
	public long dt0 = 0;
	public long dt1 = 0;
	
	//flags
	private boolean allow_shoot = true;
	
	//Scene
	public Scene mScene;
	
	//SmoothSprite
	public SmoothSprite ASprite;
	public SmoothSprite BSprite;
	private SmoothSprite []tempSprite = new SmoothSprite[2];
	private AnimatedSmoothSprite LevelTeachSprite;
	
	//
	public PhysicsConnector tempConnector;
	
	//TextureRegions
	private ITextureRegion ACubeTextureRegion;
	private ITextureRegion BCubeTextureRegion;
	private ITextureRegion BackGroundTextureRegion;
	private TiledTextureRegion LevelTeachingRegion;
	public TiledTextureRegion NormalBoardTextureRegion;
	
	//PhysicsWorld
	public PhysicsWorld mPhysicsWorld;
	
	//Body
	
	private Body mGroundBody;
	public Body ACubeBody;
	public Body BCubeBody;
	//private Body BoardBody;
	
	
	//EnityModifier
	public static ParallelEntityModifier GameOverModifierA;
	public static ParallelEntityModifier GameOverModifierB;
	public static ParallelEntityModifier GameOverModifier;
	public static LoopEntityModifier shakeModifierA;
	public static LoopEntityModifier shakeModifierB;
	
	
	private MouseJoint mMouseJointActive;
	private static BitmapTextureAtlas[] CoverHelpAtlas;
	private static ITextureRegion[] CoverHelpRegion;
	private static SmoothSprite[] CoverHelp;
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		Bundle bundle = this.getIntent().getExtras();  
		int level=bundle.getInt("level");
		level_index= level;

		EngineOptions mEngineOptions =  new EngineOptions(true, ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
//		mEngineOptions.getTouchOptions().setNeedsMultiTouch(true);  //多点触控
		return mEngineOptions;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		
		this.CubeTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 128, 64,TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		this.BackGroundTextureAtlas = new BitmapTextureAtlas(getTextureManager(), 512, 1024);
		this.LevelTeachingAtlas = new BitmapTextureAtlas(getTextureManager(), 512, 4096,TextureOptions.REPEATING_BILINEAR);
		
		
		this.BackGroundTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(BackGroundTextureAtlas, this, "background_game.png", 0, 0);
		this.ACubeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(CubeTextureAtlas, this, "box_a.png", 1, 0);
		this.BCubeTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(CubeTextureAtlas, this, "box_b.png", 62, 0);
		this.LevelTeachingRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(LevelTeachingAtlas, this, "LevelTeaching.png", 0, 0, 1, 5);
		//===============关卡Region的初始化=======================//

		this.BackGroundTextureAtlas.load();
		this.CubeTextureAtlas.load();
		this.LevelTeachingAtlas.load();
		
		GameOverModifierA = new ParallelEntityModifier(
				new SequenceEntityModifier(
				new AlphaModifier(20, 1, 0),
				new AlphaModifier(20, 0, 1)),
				new SequenceEntityModifier(
				new RotationModifier(50, 0, 360 * 30),
				new ScaleModifier(20, 1, 0.1f),
				new DelayModifier(10),
				new ParallelEntityModifier(new ScaleModifier(10, 0.1f, 10),new RotationModifier(10, 0, 520)),
				new ParallelEntityModifier(new AlphaModifier(0.5f, 1f, 0f),
				new ScaleModifier(0.5f, 10, 0f),new DelayModifier(1.5f))
				)
					{
					@Override
					protected void onModifierFinished(IEntity pItem) {
						// TODO Auto-generated method stub
						super.onModifierFinished(pItem);
					}
					
				});
		
		GameOverModifierB = new ParallelEntityModifier(
				new SequenceEntityModifier(
						new AlphaModifier(20, 1, 0),
						new AlphaModifier(20, 0, 1)),
						new SequenceEntityModifier(
						new RotationModifier(50, 0, 360 * 30),
						new ScaleModifier(20, 1, 0.1f),
						new DelayModifier(10),
						new ParallelEntityModifier(new ScaleModifier(10, 0.1f, 10),new RotationModifier(10, 0, 520)),
						new ParallelEntityModifier(new AlphaModifier(0.5f, 1f, 0f),
						new ScaleModifier(0.5f, 10, 0f),new DelayModifier(1.5f))
						)
				{
					@Override
					protected void onModifierFinished(IEntity pItem) {
						// TODO Auto-generated method stub
						super.onModifierFinished(pItem);
						if(setFinish){
							 BGF= new Boxy_GameFinish((ASprite.getX()+BSprite.getX()-30)/2, (ASprite.getY()+BSprite.getY()-30)/2, MyBoxyBetaActivity.this);
							setFinish=false;
							//记得在initial的函数里面增加setFinish的初始化函数,设置为真...否则就没有动画了
						}
					}
				});
		
		shakeModifierA = new LoopEntityModifier( new SequenceEntityModifier(
				new RotationModifier(0.03f, 0, 10),
				new RotationModifier(0.06f, 10, -10),
				new RotationModifier(0.03f, -10, 0)));
		shakeModifierB = new LoopEntityModifier( new SequenceEntityModifier(
				new RotationModifier(0.03f, 0, 10),
				new RotationModifier(0.06f, 10, -10),
				new RotationModifier(0.03f, -10, 0)));
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub
		
		this.mScene = new Scene();
		
		//Listener...
		this.mScene.setOnAreaTouchListener(this);
		this.mScene.setOnSceneTouchListener(this);
		//=============前五关的帮助界面的新建================//
//		BitmapTextureAtlas[] CoverHelpAtlas;
		CoverHelpAtlas=new BitmapTextureAtlas[helpNUM];
		for(int i=0;i<helpNUM;i++){
			CoverHelpAtlas[i]=new BitmapTextureAtlas(getTextureManager(), 480, 800);
			CoverHelpAtlas[i].load();
		}
//		ITextureRegion[] CoverHelpRegion;
		CoverHelpRegion=new ITextureRegion[helpNUM];
		for(int i=0;i<helpNUM;i++){
			String pathName="cover_level0_"+i+".png";
			CoverHelpRegion[i]=BitmapTextureAtlasTextureRegionFactory.createFromAsset(CoverHelpAtlas[i], this, pathName, 0, 0);
		}
//		SmoothSprite[] CoverHelp;
		CoverHelp=new SmoothSprite[helpNUM];
		for(int i=0;i<helpNUM;i++){
			CoverHelp[i]=new SmoothSprite(0, 0, CoverHelpRegion[i], this.getVertexBufferObjectManager());
			mScene.attachChild(CoverHelp[i]);
		//	CoverHelp[i].setVisible(false);
		}
		

		this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_EARTH*1.5f), false)
		{

			

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				super.onUpdate(pSecondsElapsed);
				//if(!ending)
				if(ASprite!=null&&BSprite!=null)
				if(ASprite.collidesWith(BSprite))
				{
					refresh.refreshButton.setVisible(false);
					ACubeBody.setAwake(false);
					BCubeBody.setAwake(false);
					
						BSprite.registerEntityModifier(GameOverModifierB);
						ASprite.registerEntityModifier(GameOverModifierA);
					
					ending=true;
				}
			}
			
		};
		
		this.mGroundBody = this.mPhysicsWorld.createBody(new BodyDef());
		
		//EntityModifier
//		GameOverModifier = new SequenceEntityModifier(new AlphaModifier(2, 1, 0),
//				new AlphaModifier(2, 0, 1),
//				new ScaleModifier(2, 1, 0.5f),
//				new DelayModifier(0.5f),
//				new ParallelEntityModifier(
//						new ScaleModifier(3, 0.5f, 5),
//						new RotationByModifier(3, 90)
//				),
//				new ParallelEntityModifier(
//						new ScaleModifier(3, 5, 1),
//						new RotationModifier(3, 180, 0)
//				));
		
		
		
//		final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
//		
//		final Rectangle ground = new Rectangle(0, CAMERA_HEIGHT - 0.5f, CAMERA_WIDTH, 0.5f, vertexBufferObjectManager);
//		final Rectangle roof = new Rectangle(0, 0, CAMERA_WIDTH, 0.5f, vertexBufferObjectManager);
//		final Rectangle left = new Rectangle(0, 0, 0.5f, CAMERA_HEIGHT, vertexBufferObjectManager);
//		final Rectangle right = new Rectangle(CAMERA_WIDTH - 0.5f, 0, 0.5f, CAMERA_HEIGHT, vertexBufferObjectManager);
//
//		final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.0f, 0.5f);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyType.StaticBody, wallFixtureDef);
//		PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyType.StaticBody, wallFixtureDef);
		
		
		//boardt0est.NormalBoardSprite.setColor(Color.RED);

//		this.mScene.attachChild(ground);
//		this.mScene.attachChild(roof);
//		this.mScene.attachChild(left);
//		this.mScene.attachChild(right);
		
		tempSprite[0] = null;
		tempSprite[1] = null;
		
		final SmoothSprite  BackGroundSprite = new SmoothSprite(0,0,480,800,this.BackGroundTextureRegion,this.getVertexBufferObjectManager());
		SpriteBackground  SpriteBG = new SpriteBackground(BackGroundSprite);
		
		//====教学关卡啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊==========//
		
		LevelTeachingRegion.setCurrentTileIndex(0);
		LevelTeachSprite = new AnimatedSmoothSprite(0, 0, LevelTeachingRegion,this.getVertexBufferObjectManager());
		//setting the background
		this.mScene.setBackground(SpriteBG);
		//this.mScene.attachChild(BackGroundSprite);
		//this.mScene.attachChild(LevelTeachSprite);
//		this.createACube(30, 30);
//		this.createBCube(200,20);
//		
//		IUpdateHandler iu= new IUpdateHandler() {
//			private Boxy_GameFinish BGF;
//
//			public void reset() { }
//
//			public void onUpdate(final float pSecondsElapsed) {
//				
//				//GameOver 条件
//				if(ASprite.collidesWith(BSprite))
//				{
//					mPhysicsWorld.destroyBody(ACubeBody);
//					mPhysicsWorld.destroyBody(BCubeBody);
//					ASprite.registerEntityModifier(GameOverModifierA);
//					BSprite.registerEntityModifier(GameOverModifierB);
//					if(setFinish){
//						 BGF= new Boxy_GameFinish(ASprite.getX(), ASprite.getY(), MyBoxyBetaActivity.this);
//						setFinish=false;
//						//记得在initial的函数里面增加setFinish的初始化函数,设置为真...否则就没有动画了
//					}
//					ending=true;
//				}
//			}
//		};
		
		this.mScene.registerUpdateHandler(mPhysicsWorld);
		
		
		
		level = new Boxy_Level(level_index, this);
		level.InitialObject();
		//Boxy_BlackHole blbl = new Boxy_BlackHole(100,200, 300, 2000, this);
		refresh=new Boxy_refresh(this);


		
		return this.mScene;
	}
    /** Called when the activity is first created. */

	//Create Function
	public void createACube(float x,float y)
	{
		ASprite = new SmoothSprite(x, y, 45, 45, this.ACubeTextureRegion, this.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(pSceneTouchEvent.isActionUp()&& is_shaking==true)
				{
					ASprite.unregisterEntityModifier(shakeModifierA);
					shakeModifierA.reset();
					is_shaking=false;
					has_touched=false;
					if(tempSprite[0] ==ASprite)
						tempSprite[0] = null;
				if(tempSprite[1] == ASprite)
						tempSprite[1] = null;
					Cube_touched_num--;
					return true;
				}
				// TODO Auto-generated method stub
				if(has_touched == false)
				{
						if(this.can_be_moved )
						{
							Cube_touched_num ++;
							if(Cube_touched_num >= 2)
								Cube_touched_num = 1;
								tempSprite[0] = ASprite;
								Debug.d(" "+ Cube_touched_num);
								this.has_touched = true;
						}
						else tempSprite[0] = null;
				}
				return true;
			}
		};
		this.mScene.attachChild(ASprite);
		this.ACubeBody = PhysicsFactory.createBoxBody(mPhysicsWorld, x+22.5f, y+22.5f, 44, 44, BodyType.DynamicBody, CUBE_DEF);
		this.ASprite.setUserData(ACubeBody);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(ASprite, ACubeBody,true,true));
		ACubeBody.setBullet(true);
		this.mScene.registerTouchArea(ASprite);
		Cube_detached = false;
		}
	
	public void createBCube(float x,float y)
	{
		BSprite = new SmoothSprite(x, y, 45, 45, this.BCubeTextureRegion, this.getVertexBufferObjectManager())
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				// TODO Auto-generated method stub
				Debug.d(" " + is_shaking);
				if(pSceneTouchEvent.isActionUp()&& is_shaking==true)
				{
					Debug.d("come on!!!");
					BSprite.unregisterEntityModifier(shakeModifierA);
					shakeModifierA.reset();
					is_shaking=false;
					has_touched=false;
					if(tempSprite[0] ==BSprite)
						tempSprite[0] = null;
				if(tempSprite[1] == BSprite)
						tempSprite[1] = null;
					Cube_touched_num--;
					return true;
				}
				if(has_touched == false)
				{
						if(this.can_be_moved )
						{
							Cube_touched_num ++;
							if(Cube_touched_num >= 2)
								Cube_touched_num = 1;
								tempSprite[0] = BSprite;
								Debug.d(" "+ Cube_touched_num);
								this.has_touched = true;
						}
						else tempSprite[0] = null;
				}
				return true;
			}
		};
		this.mScene.attachChild(BSprite);
		this.BCubeBody = PhysicsFactory.createBoxBody(mPhysicsWorld, x+22.5f, y+22.5f, 44, 44, BodyType.DynamicBody, CUBE_DEF);
		this.BSprite.setUserData(BCubeBody);
		this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(BSprite, BCubeBody,true,true));
		BCubeBody.setBullet(true);
		this.mScene.registerTouchArea(BSprite);
		Cube_detached = false;
	}

	
	
	
	public void onAccelerationChanged(final AccelerationData pAccelerationData) {
//		final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
//		this.mPhysicsWorld.setGravity(gravity);
//		Vector2Pool.recycle(gravity);
	}

	public void onAccelerationAccuracyChanged(AccelerationData arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onResumeGame() {
		super.onResumeGame();

//		this.enableAccelerationSensor(this);
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();

		this.disableAccelerationSensor();
	}

	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final ITouchArea pTouchArea, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		if(pSceneTouchEvent.isActionDown()) {
			
			/*
			 * If we have a active MouseJoint, we are just moving it around
			 * instead of creating a second one.
			 */
			if(this.mMouseJointActive == null) {
				
			}
			return true;
		}
		return false;
	}
	
	public void releaseCube()
	{
				// TODO Auto-generated method stub
				tempConnector = mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(ASprite);
				
				mPhysicsWorld.unregisterPhysicsConnector(tempConnector);
				Debug.d("WTF!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				mPhysicsWorld.destroyBody(tempConnector.getBody());
				ASprite.unregisterEntityModifier(GameOverModifierA);
				Debug.d("dabeiju");
				mScene.unregisterTouchArea(ASprite);
				mScene.detachChild(ASprite);
				
				tempConnector = mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(BSprite);
				
				mPhysicsWorld.unregisterPhysicsConnector(tempConnector);
				BSprite.unregisterEntityModifier(GameOverModifierB);
				mPhysicsWorld.destroyBody(tempConnector.getBody());
				
				mScene.unregisterTouchArea(BSprite);
				mScene.detachChild(BSprite);
				
				ASprite = null;
				ACubeBody = null;
				
				BSprite= null;
				BCubeBody = null;
				
}
			
	
	public boolean onSceneTouchEvent(final Scene pScene, final TouchEvent pSceneTouchEvent) {
		if(this.mPhysicsWorld != null) {
				switch(pSceneTouchEvent.getAction())
				{
				case TouchEvent.ACTION_UP:
					if(tempSprite[0] != null)
					{
						 center0X = tempSprite[0].getX()+30;
						 center0Y = tempSprite[0].getY()+30;
					}
					
					if(tempSprite[1] != null)
					{
						 center1X = tempSprite[1].getX()+30;
						 center1Y = tempSprite[1].getY()+30;
//						 dx1 = pSceneTouchEvent.getX() - center0X;
//						 dy1 = pSceneTouchEvent.getY() - center0Y;
					}
					
					dt0 =Math.abs (pSceneTouchEvent.getMotionEvent().getEventTime()-pSceneTouchEvent.getMotionEvent().getDownTime());
					
					if(pSceneTouchEvent.getPointerID() == pSceneTouchEvent.getMotionEvent().findPointerIndex(0))
						{
						if(tempSprite[0]==null)
							return true;
						
						if(tempSprite[0].is_shaking && tempSprite[0] != null)
						{
							tempSprite[0].unregisterEntityModifier(shakeModifierA);
							shakeModifierA.reset();
						}
						
						dx0 = (pSceneTouchEvent.getX() - center0X);
						dy0 =( pSceneTouchEvent.getY() - center0Y);
						
							if(tempSprite[0] == null)
								return true;
							if(dy0<0)
							{
								if(dt0<500)
									direction0=1;
								else
									direction0=2;
							}
							else
							{
								if((dy0 > 19&&dx0>32)||(dy0>19&&dx0<-32))
								{
									if(dt0<350)
										direction0 = 0;
									else
										direction0=-1;
								}
								else
								{
									if(dt0<350)
										direction0 =2;
									else
										direction0=-1;
								}
							}
						if(direction0 == -1)
							pop_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 1)
							pull_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 0)
							zeroShoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 2)
						{
							tempSprite[0].has_touched = false;
							tempSprite[0] = null;
							Cube_touched_num--;
						}
						return true;
						}
				case MotionEvent.ACTION_POINTER_1_UP:
					if(tempSprite[0] != null)
					{
						 center0X = tempSprite[0].getX()+30;
						 center0Y = tempSprite[0].getY()+30;
					}
					
					if(tempSprite[1] != null)
					{
						 center1X = tempSprite[1].getX()+30;
						 center1Y = tempSprite[1].getY()+30;
//						 dx1 = pSceneTouchEvent.getX() - center0X;
//						 dy1 = pSceneTouchEvent.getY() - center0Y;
					}
					
					dt0 =Math.abs (pSceneTouchEvent.getMotionEvent().getEventTime()-pSceneTouchEvent.getMotionEvent().getDownTime());
					
					if(pSceneTouchEvent.getPointerID() == pSceneTouchEvent.getMotionEvent().findPointerIndex(0))
						{
						if(tempSprite[0]==null)
							return true;
						
						if(tempSprite[0].is_shaking && tempSprite[0] != null)
						{
							tempSprite[0].unregisterEntityModifier(shakeModifierA);
							shakeModifierA.reset();
						}
						
						dx0 = (pSceneTouchEvent.getX() - center0X);
						dy0 =( pSceneTouchEvent.getY() - center0Y);
						
							if(tempSprite[0] == null)
								return true;
							if(dy0<0)
							{
								if(dt0<500)
									direction0=1;
								else
									direction0=2;
							}
							else
							{
								if((dy0 > 19&&dx0>32)||(dy0>19&&dx0<-32))
								{
									if(dt0<350)
										direction0 = 0;
									else
										direction0=-1;
								}
								else
								{
									if(dt0<350)
										direction0 =2;
									else
										direction0=-1;
								}
							}
						if(direction0 == -1)
							pop_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 1)
							pull_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 0)
							zeroShoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
						if(direction0 == 2)
						{
							tempSprite[0].has_touched = false;
							tempSprite[0] = null;
							Cube_touched_num--;
						}
						return true;
						}
					return true;
					
//					if(pSceneTouchEvent.getPointerID() != pSceneTouchEvent.getMotionEvent().findPointerIndex(0))
//					{
//						
//						if(tempSprite[1].is_shaking && tempSprite[1] != null)
//						{
//							tempSprite[1].unregisterEntityModifier(shakeModifierB);
//							shakeModifierB.reset();
//						}
//					
//					dx1 = (pSceneTouchEvent.getX() - center0X);
//					dy1 =( pSceneTouchEvent.getY() - center0Y);
//					
//					
//						if(tempSprite[1] == null)
//							return true;
//						if(dy1<0)
//						{
//							if(dt0<500)
//								direction1=1;
//							else
//								direction1=2;
//						}
//						else
//						{
//							if((dy1 > 19&&dx1>32)||(dy1>19&&dx1<-32))
//							{
//								if(dt0<350)
//									direction1 = 0;
//								else
//									direction1=-1;
//							}
//							else
//							{
//								if(dt0<350)
//									direction1 =2;
//								else
//									direction1=-1;
//							}
//						}
//					if(direction1 == -1)
//						pop_shoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//					if(direction1 == 1)
//						pull_shoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//					if(direction1 == 0)
//						zeroShoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//					if(direction1 == 2)
//					{
//						tempSprite[1].has_touched = false;
//						tempSprite[1] = null;
//						Cube_touched_num--;
//					}
//					return true;
//					}
					
				case TouchEvent.ACTION_MOVE:
					if(tempSprite[0]!=null)
					center0Y = tempSprite[0].getY()+30;
					dy0 =( pSceneTouchEvent.getY() - center0Y);
					dt0 =Math.abs (pSceneTouchEvent.getMotionEvent().getEventTime()-pSceneTouchEvent.getMotionEvent().getDownTime());
					if(pSceneTouchEvent.getPointerID() == pSceneTouchEvent.getMotionEvent().findPointerIndex(0))
					{
						if(tempSprite[0] == null||!tempSprite[0].can_be_moved)
							return true;
						if(dt0>800 && !tempSprite[0].is_shaking&&tempSprite[0]!=null&&dy0>5)
						{
							if(tempSprite[0] == ASprite)
								tempSprite[0].registerEntityModifier(shakeModifierA);
							if(tempSprite[0] == BSprite)
								tempSprite[0].registerEntityModifier(shakeModifierA);
								tempSprite[0].is_shaking = true;
								Debug.d("la");
						}
						else
						{
							if(dy0<5&&tempSprite[0].is_shaking)
								{
								if(tempSprite[0] == ASprite)
								{
									tempSprite[0].unregisterEntityModifier(shakeModifierA);
									shakeModifierA.reset();
								}
								if(tempSprite[0] == BSprite)
								{
									tempSprite[0].unregisterEntityModifier(shakeModifierA);
									shakeModifierB.reset();
								}
								tempSprite[0].is_shaking=false;
								
								Debug.d("ya");
								}
						}
					}
					
//					else
//					{
//						if(!tempSprite[0].can_be_moved)
//							return true;
//						Debug.d("ActionDown" + dt0);
//						if(dt0>800 && !tempSprite[1].is_shaking&&tempSprite[1]!=null)
//						{
//							if(tempSprite[1] == ASprite)
//								tempSprite[1].registerEntityModifier(shakeModifierA);
//							if(tempSprite[1] == BSprite)
//								tempSprite[1].registerEntityModifier(shakeModifierB);
//							tempSprite[1].is_shaking = true;
//						}
//					}
						return true;
						
//				case MotionEvent.ACTION_POINTER_UP:
//					if(tempSprite[0] != null)
//					{
//						 center0X = tempSprite[0].getX()+30;
//						 center0Y = tempSprite[0].getY()+30;
//					}
//					
//					if(tempSprite[1] != null)
//					{
//						 center1X = tempSprite[1].getX()+30;
//						 center1Y = tempSprite[1].getY()+30;
////						 dx1 = pSceneTouchEvent.getX() - center0X;
////						 dy1 = pSceneTouchEvent.getY() - center0Y;
//					}
//					
//					dt0 =Math.abs (pSceneTouchEvent.getMotionEvent().getEventTime()-pSceneTouchEvent.getMotionEvent().getDownTime());
//						if(tempSprite[0]==null)
//							return true;
//						
//						if(tempSprite[0].is_shaking && tempSprite[0] != null)
//						{
//							tempSprite[0].unregisterEntityModifier(shakeModifierA);
//							shakeModifierA.reset();
//						}
//						
//						dx0 = (pSceneTouchEvent.getX() - center0X);
//						dy0 =( pSceneTouchEvent.getY() - center0Y);
//						
//							if(tempSprite[0] == null)
//								return true;
//							if(dy0<0)
//							{
//								if(dt0<500)
//									direction0=1;
//								else
//									direction0=2;
//							}
//							else
//							{
//								if((dy0 > 19&&dx0>32)||(dy0>19&&dx0<-32))
//								{
//									if(dt0<350)
//										direction0 = 0;
//									else
//										direction0=-1;
//								}
//								else
//								{
//									if(dt0<350)
//										direction0 =2;
//									else
//										direction0=-1;
//								}
//							}
//						if(direction0 == -1)
//							pop_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
//						if(direction0 == 1)
//							pull_shoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
//						if(direction0 == 0)
//							zeroShoot(tempSprite[0], pSceneTouchEvent, dx0, dy0);
//						if(direction0 == 2)
//						{
//							tempSprite[0].has_touched = false;
//							tempSprite[0] = null;
//							Cube_touched_num--;
//						}
//					return true;
//				case MotionEvent.ACTION_POINTER_2_UP:
//					if(tempSprite[0] != null)
//					{
//						 center0X = tempSprite[0].getX()+30;
//						 center0Y = tempSprite[0].getY()+30;
//					}
//					
//					if(tempSprite[1] != null)
//					{
//						 center1X = tempSprite[1].getX()+30;
//						 center1Y = tempSprite[1].getY()+30;
////						 dx1 = pSceneTouchEvent.getX() - center0X;
////						 dy1 = pSceneTouchEvent.getY() - center0Y;
//					}
//					
//					dt0 =Math.abs (pSceneTouchEvent.getMotionEvent().getEventTime()-pSceneTouchEvent.getMotionEvent().getDownTime());
//						if(tempSprite[0]==null)
//							return true;
//						
//					if(tempSprite[1].is_shaking && tempSprite[1] != null)
//					{
//						tempSprite[1].unregisterEntityModifier(shakeModifierB);
//						shakeModifierB.reset();
//					}
//				
//				dx1 = (pSceneTouchEvent.getX() - center0X);
//				dy1 =( pSceneTouchEvent.getY() - center0Y);
//				
//				
//					if(tempSprite[1] == null)
//						return true;
//					if(dy1<0)
//					{
//						if(dt0<500)
//							direction1=1;
//						else
//							direction1=2;
//					}
//					else
//					{
//						if((dy1 > 19&&dx1>32)||(dy1>19&&dx1<-32))
//						{
//							if(dt0<350)
//								direction1 = 0;
//							else
//								direction1=-1;
//						}
//						else
//						{
//							if(dt0<350)
//								direction1 =2;
//							else
//								direction1=-1;
//						}
//					}
//				if(direction1 == -1)
//					pop_shoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//				if(direction1 == 1)
//					pull_shoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//				if(direction1 == 0)
//					zeroShoot(tempSprite[1], pSceneTouchEvent, dx1, dy1);
//				if(direction1 == 2)
//				{
//					tempSprite[1].has_touched = false;
//					tempSprite[1] = null;
//					Cube_touched_num--;
//				}
//				return true;
				}
			}
		return false;
	}


	@Override
	public synchronized void onGameCreated() {
		// TODO Auto-generated method stub
		super.onGameCreated();
		this.mEngine.enableVibrator(this);
	}
	
	public void CoverHelp(int CoverNum){
		if(CoverNum>=0||CoverNum<CoverNum)
		for(int i=0;i<helpNUM;i++){
			if(i==CoverNum)
				CoverHelp[i].setVisible(true);
			else
				CoverHelp[i].setVisible(false);
		}else
			for(int i=0;i<helpNUM;i++){
					CoverHelp[i].setVisible(false);
			}
	}
	
	public void pop_shoot(SmoothSprite temp,TouchEvent te ,float DX,float DY)
	{
		if(temp.can_be_moved)
		{
			final Body faceBody = (Body) temp.getUserData();
			velocity = Vector2Pool.obtain(-(DX*XRate ), -(DY*YRate));
			faceBody.setLinearVelocity(velocity);
			Vector2Pool.recycle(velocity);
			temp.can_be_moved = false;
			temp = null;
			Cube_touched_num--;
		}
	}
	
	public void pull_shoot(SmoothSprite temp,TouchEvent te, float DX,float DY)
	{
		if(temp.can_be_moved)
		{
			final Body faceBody = (Body) temp.getUserData();
			velocity = Vector2Pool.obtain((DX*XRate*0.55f), (DY*YRate*0.55f));
			faceBody.setLinearVelocity(velocity);
			Vector2Pool.recycle(velocity);
			temp.can_be_moved = false;
			temp = null;
			Cube_touched_num--;
		}
	}
	
	public void zeroShoot(SmoothSprite temp,TouchEvent te,float DX,float DY)
	{
		if(temp.can_be_moved)
		{
			final Body faceBody = (Body) temp.getUserData();
			velocity = Vector2Pool.obtain(DX*XRate, 0);
			faceBody.setLinearVelocity(velocity);
			Vector2Pool.recycle(velocity);
			temp.can_be_moved = false;
			temp = null;
			Cube_touched_num--;
		}
	}
}
