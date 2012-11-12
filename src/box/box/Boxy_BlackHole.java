package box.box;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.badlogic.gdx.math.Vector2;



public class Boxy_BlackHole extends Boxy_Area{



	//public MyBoxyBetaActivity parent;
	private float ForceStrength;
	private static BitmapTextureAtlas gravitybBitmapTextureAtlas;
	private static TiledTextureRegion gravitytTiledTextureRegion;
	private AnimatedSmoothSprite gravityHole;

	 /**
	  * 
	  * @param centerX 引力中心坐标X
	  * @param centerY 引力中心坐标Y
	  * @param GameActivity 传入游戏类的activity,以公用需要的要素
	  * 基本包含final Scene scene,final Sprite ASprite,final Body ACubeBody,final Sprite BSprite,final Body BCubeBody
	  * 这里的5项,需要修改为PUBLIC...!!!!
	  */
	 public Boxy_BlackHole(final float centerX,final float centerY,final float areaR,final float ForceStrength,MyBoxyBetaActivity GameActivity){
		 super(GameActivity);
		 this.x=centerX;
		 this.y=centerY;
		 this.radias=areaR;
		 this.ForceStrength=ForceStrength;
			//==============blackhole==============//
		 BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		 	gravitybBitmapTextureAtlas=new BitmapTextureAtlas(parent.getTextureManager(), 512, 256);
		 	gravitytTiledTextureRegion=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(gravitybBitmapTextureAtlas, parent, "gravitate_area.png", 0 , 0,2,1);
		 	gravitybBitmapTextureAtlas.load();
			gravityHole=new AnimatedSmoothSprite(centerX-90, centerY-90, gravitytTiledTextureRegion, parent.getVertexBufferObjectManager());
			gravityHole.animate(3000);
//			gravityHole.setAlpha(0.5f);
			gravityHole.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			ParallelEntityModifier gravityModifier = new ParallelEntityModifier(
					new  LoopEntityModifier(
							new SequenceEntityModifier(
									new AlphaModifier(1.0f, 0f, 0.7f),new AlphaModifier(1.0f,0.7f,0.7f),new AlphaModifier(1f, 0.7f, 0f))),
											new LoopEntityModifier(
													new RotationModifier(3, 0, 360f)));
			gravityHole.registerEntityModifier(gravityModifier);
			parent.mScene.attachChild(gravityHole);
			if(isActive){
			parent.mScene.registerUpdateHandler(new IUpdateHandler() {
			public void reset() { }
			
			public void onUpdate(final float pSecondsElapsed) {
				if((parent.ASprite!=null&&parent.BSprite!=null&&!parent.ending)){
				float BSpriteX=parent.BSprite.getX()+30;
				float BSpriteY=parent.BSprite.getY()+30;
				float ASpriteX=parent.ASprite.getX()+30;
				float ASpriteY=parent.ASprite.getY()+30;
				
					//not working
			///	Vector2 Acenter= parent.ACubeBody.getWorldCenter();
			//		Vector2 Bcenter= parent.BCubeBody.getWorldCenter();



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
							Vector2 f=new Vector2((ForceStrength/dA)*Math.signum(centerX-ASpriteX),(ForceStrength/dA)*Math.signum(centerY-ASpriteY));

							parent.ACubeBody.applyForce(f,c);
						    
				}
				if(dB<areaR&&isActive){
					Vector2 cB=parent.BCubeBody.getWorldCenter();
					Vector2 fB=new Vector2(ForceStrength/dB*Math.signum(centerX-BSpriteX),ForceStrength/dB*Math.signum(centerY-BSpriteY));
					parent.BCubeBody.applyForce(fB,cB);
				}
			}}
	});
			}
			
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
		 */
	 
		@Override
		public void destroy() {
			// TODO Auto-generated method stub
			super.destroy();
			
			parent.mScene.detachChild(gravityHole);
			isActive=false;
			
		}
		//=========show只是隐藏,屏蔽力,不会Detach============//
		public void show(boolean pV){
			this.gravityHole.setVisible(pV);
			isActive=pV;
		}
		
		@Override
		public void doSwitch() {
			super.doSwitch();
			isActive=!isActive;
			this.gravityHole.setVisible(isActive);
		}
		
//		 public Boxy_BlackHole(final Scene scene,final Sprite ASprite,final Body ACubeBody,final Sprite BSprite,final Body BCubeBody,final float centerX,final float centerY,final float ForceStrength,final float areaR)
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
//							Vector2 f=new Vector2(ForceStrength/Math.abs(centerX-ASprite.getX()),ForceStrength/Math.abs(centerY-ASprite.getY()));
//						    ACubeBody.applyForce(f,c);
//						    
//				}
//				if(dB<areaR&&dB>10){
//					Vector2 cB=BCubeBody.getWorldCenter();
//					Vector2 fB=new Vector2(ForceStrength/Math.abs(centerX-BSprite.getX())*Math.signum(centerX-BSprite.getX()),ForceStrength/Math.abs(centerY-BSprite.getY())*Math.signum(centerY-BSprite.getY()));
//				    BCubeBody.applyForce(fB,cB);
//				}
//			}
//	});
//	}

}
