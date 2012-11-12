package box.box;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Boxy_ExcludeHole extends Boxy_Area{
	
	//public MyBoxyBetaActivity parent;
	public float ForceStrength=1000;
	final float areaR=110;
	private static BuildableBitmapTextureAtlas excludeBitmapTextureAtlas;
	private static TiledTextureRegion excludeTiledTextureRegion;
	public  AnimatedSmoothSprite excludeHole; 
//	private ITextureRegion scaleTextureRegion;
//	private BitmapTextureAtlas scaleAtlas;
	 
	 public Boxy_ExcludeHole(final float centerX,final float centerY,final float areaR,final float ForceStrength,MyBoxyBetaActivity GameActivity){
		 super(GameActivity);
		 this.x=centerX;
		 this.y=centerY;
		 this.radias=areaR;
		 this.ForceStrength=ForceStrength;
			//==============hole==============//
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
//		 scaleAtlas= new BitmapTextureAtlas(parent.getTextureManager(), 256, 256);
		 this.excludeBitmapTextureAtlas=new BuildableBitmapTextureAtlas(parent.getTextureManager(), 2048, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		 this.excludeTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.excludeBitmapTextureAtlas, parent, "exclude_area.png", 7, 1);
//		 this.scaleTextureRegion= BitmapTextureAtlasTextureRegionFactory.createFromAsset(scaleAtlas, parent, "exclude_area_cover.png", 0, 0);
//		 scaleAtlas.load();
		 try {
				this.excludeBitmapTextureAtlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 1));
				this.excludeBitmapTextureAtlas.load();
			} catch (TextureAtlasBuilderException e) {
				Debug.e(e);
			}
			excludeHole = new AnimatedSmoothSprite(centerX-90, centerY-90, this.excludeTiledTextureRegion	,parent.getVertexBufferObjectManager());
			excludeHole.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			excludeHole.setAlpha(0.7f);
			excludeHole.animate(800);
			ParallelEntityModifier gravityModifier = new ParallelEntityModifier(
					new  LoopEntityModifier(
							new SequenceEntityModifier(
									new AlphaModifier(1.0f, 0.3f, 0.7f),new AlphaModifier(2.0f,0.7f,0.7f),new AlphaModifier(1f, 0.7f, 0.3f))),
											new LoopEntityModifier(
													new RotationModifier(3, 360f,0f)));
		//	excludeHole.registerEntityModifier(gravityModifier);
//			SmoothSprite scaleSprite = new SmoothSprite(centerX, centerY, scaleTextureRegion, parent.getVertexBufferObjectManager());
			//scaleSprite.registerEntityModifier(gravityModifier);
			//parent.mScene.attachChild(scaleSprite);
			parent.mScene.attachChild(excludeHole);
		 
			parent.mScene.registerUpdateHandler(new IUpdateHandler() {
			public void reset() { }
			
			public void onUpdate(final float pSecondsElapsed) {
				if((parent.ASprite!=null&&parent.BSprite!=null&&!parent.ending)){
				float BSpriteX=parent.BSprite.getX()+30;
				float BSpriteY=parent.BSprite.getY()+30;
				float ASpriteX=parent.ASprite.getX()+30;
				float ASpriteY=parent.ASprite.getY()+30;
				

				float dA=(float) Math.sqrt((centerX-ASpriteX)*(centerX-ASpriteX)+((centerY-ASpriteY)*(centerY-ASpriteY)));
				float dB=(float) Math.sqrt((centerX-BSpriteX)*(centerX-BSpriteX)+((centerY-BSpriteY)*(centerY-BSpriteY)));
			
				
				if(dA<areaR&&isActive){
					/**
					 * 当精灵在引力的作用区域内(根据areaR和两点的距离判断)
					 * 获取刚体的中心
					 * 获取力的大小:力的大小由以下公式决定 ForceStrength/距离*方向
					 * 这样才可以达到无论在引力中心的上方还是下方都是引力的效果,反之亦然.
					 * 最后applyForce
					 */
							Vector2 c=parent.ACubeBody.getWorldCenter();
						//	Vector2 f=new Vector2(ForceStrength/Math.abs(centerX-ASpriteX),ForceStrength/Math.abs(centerY-ASpriteY));
							Vector2 f=new Vector2(-(ForceStrength/dA)*Math.signum(centerX-ASpriteX),-(ForceStrength/dA)*Math.signum(centerY-ASpriteY));

							parent.ACubeBody.applyForce(f,c);
						    
				}
				if(dB<areaR&&isActive){
					Vector2 cB=parent.BCubeBody.getWorldCenter();
					Vector2 fB=new Vector2(-ForceStrength/dB*Math.signum(centerX-BSpriteX),-ForceStrength/dB*Math.signum(centerY-BSpriteY));
					parent.BCubeBody.applyForce(fB,cB);
				}
			}}
	});
}
	 
	 
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			super.destroy();
			
			parent.mScene.detachChild(excludeHole );
			isActive=false;
			
		}
		//=========show只是隐藏,屏蔽力,不会Detach============//
		public void show(boolean pV){
			this.excludeHole.setVisible(pV);
			isActive=pV;
		}
		

		@Override
		public void doSwitch() {
			super.doSwitch();
			isActive=!isActive;
			this.excludeHole.setVisible(isActive);
		}
	 /**
		 * 这种方法参数过多...改写一次
		 * 给出构造函数各个参数含义
		 * @param scene 
		 * @param ASprite 
		 * @param ACubeBody
		 * @param BSprite
		 * @param BCubeBody
		 * @param centerX 引力的中心
		 * @param centerY 引力的中心
		 * @param ForceStrength 力的大小的决定数值,
		 * @param areaR 引力作用区域
//		 */
//		 public Boxy_ExcludeHole(final Scene scene,final Sprite ASprite,final Body ACubeBody,final Sprite BSprite,final Body BCubeBody,final float centerX,final float centerY,final float ForceStrength,final float areaR)
//		 {
//			 
//			scene.registerUpdateHandler(new IUpdateHandler() {
//			public void reset() { }
//
//			public void onUpdate(final float pSecondsElapsed) {
//				double dA=Math.sqrt((centerX-ASprite.getX())*(centerX-ASprite.getX())+((centerY-ASprite.getY())*(centerY-ASprite.getY())));
//				double dB=Math.sqrt((centerX-BSprite.getX())*(centerX-BSprite.getX())+((centerY-BSprite.getY())*(centerY-BSprite.getY())));
//				if(dA<areaR&&dA>10){
//					/**
//					 * 当精灵在引力的作用区域内(根据areaR和两点的距离判断)
//					 * 获取刚体的中心
//					 * 获取力的大小:力的大小由以下公式决定 ForceStrength/距离*方向
//					 * 这样才可以达到无论在引力中心的上方还是下方都是引力的效果,反之亦然.
//					 * 最后applyForce
//					 */
//							Vector2 c=ACubeBody.getWorldCenter();
//							Vector2 f=new Vector2(-ForceStrength/Math.abs(centerX-ASprite.getX()),-ForceStrength/Math.abs(centerY-ASprite.getY()));
//						    ACubeBody.applyForce(f,c);
//						    
//				}
//				if(dB<areaR&&dB>10){
//					Vector2 cB=BCubeBody.getWorldCenter();
//					Vector2 fB=new Vector2(-ForceStrength/Math.abs(centerX-BSprite.getX())*Math.signum(centerX-BSprite.getX()),-ForceStrength/Math.abs(centerY-BSprite.getY())*Math.signum(centerY-BSprite.getY()));
//				    BCubeBody.applyForce(fB,cB);
//				}
//			}
//	});
//}
}
