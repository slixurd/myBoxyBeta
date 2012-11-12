package box.box;


import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Boxy_MirrorArea extends Boxy_Area{



	//public MyBoxyBetaActivity parent;
	private static BitmapTextureAtlas mirrorBitmapTextureAtlas;
	private static ITextureRegion mirrorTextureRegion;
//	public static Sprite mirror;
	static final float NormalLength=200;
	
	public Boxy_MirrorArea(final float X,final float Y,final float angle,final float length,final float radias,MyBoxyBetaActivity GameActivity){
		super(GameActivity);
		//==============初始化父类元素======================//
		this.length=length;
		this.radias=radias;
		this.x=(float) ((X+(length/2)*Math.sin(angle))-radias+8.5);
		this.y=(float) (Y-(length/2)*(1-Math.cos(angle)));
		this.isActive=true;
		//======================载入元素==========================//
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		mirrorBitmapTextureAtlas=new BitmapTextureAtlas(parent.getTextureManager(), 256, 256, org.andengine.opengl.texture.TextureOptions.NEAREST);
		mirrorTextureRegion=BitmapTextureAtlasTextureRegionFactory.createFromAsset(mirrorBitmapTextureAtlas, parent, "mirror_area.png", 0, 0);
		mirrorTextureRegion.setTextureSize(2*radias, length);

		/*
		//=============测试左上角坐标===================//
		Rectangle rec= new Rectangle(x, y, 3, 3, parent.getVertexBufferObjectManager());
		parent.mScene.attachChild(rec);
		*/
		this.mirrorBitmapTextureAtlas.load();
		BoxySprite= new SmoothSprite(x, y, mirrorTextureRegion, parent.getVertexBufferObjectManager());
		BoxySprite.setSize(2*radias, length);
		BoxySprite.setAlpha(0.8f);
		BoxySprite.setRotation(angle);
		 parent.mScene.attachChild(BoxySprite);
		 
	}
	public Sprite getMirror(){
		return BoxySprite;
	}
	
	
	@Override
	public void doEffect() {
		super.doEffect();
	}
	
	@Override
	public void destroy() {
		super.destroy();
	}
	
	@Override
	public void doSwitch() {
		super.doSwitch();
		isActive=!isActive;
	if(!isActive){
		 parent.mScene.detachChild(BoxySprite);
	}
	if(isActive){
		 parent.mScene.attachChild(BoxySprite);
	}
	}

	//===================调用以后来实现更新线程==================//
	@Override
	public void doInArea() {
		super.doInArea();

     	parent.mScene.registerUpdateHandler(new IUpdateHandler() {
			
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			public void onUpdate(float arg0) {
				if(parent.ASprite!=null&&parent.BSprite!=null&&!parent.ending){
				if(!isActive){
					 parent.mScene.detachChild(BoxySprite);
				}

				if(BoxySprite.collidesWith(parent.ASprite)){
				float len=length;
				float t= radias;
				float x0=parent.ASprite.getX()+30;
				float y0=parent.ASprite.getY()+30;
				float cx= x+t;
				float cy=(float) (y+0.5*len);
				float Xfinal=2*cx-x0;
				float Yfinal=2*cy-y0;
				if(isActive){
					Vector2 Vbefore=new Vector2(parent.ACubeBody.getLinearVelocity()) ;
					parent.ACubeBody.setLinearVelocity(-0.5f*parent.ACubeBody.getLinearVelocity().x,0.5f*parent.ACubeBody.getLinearVelocity().y);
					parent.ACubeBody.setTransform((Xfinal)/32, (Yfinal)/32, 10f);
					isActive=false;
					
				}}
				
				
				if(BoxySprite.collidesWith(parent.BSprite)){
				float len=length;
				float t= radias;
				float x0=(float) (parent.BSprite.getX()+30);
				float y0=parent.BSprite.getY()+30;
				float cx= x+t;
				float cy=(float) (y+0.5*len);
				float Xfinal=2*cx-x0;
				float Yfinal=2*cy-y0;
				if(isActive){
					parent.BCubeBody.setLinearVelocity(-0.5f*parent.BCubeBody.getLinearVelocity().x,0.5f*parent.BCubeBody.getLinearVelocity().y);
					parent.BCubeBody.setTransform((Xfinal)/32, (Yfinal)/32, 10f);
					isActive=false;
				}}	
			}}
		});
	}
	
}
