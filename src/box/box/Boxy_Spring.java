package box.box;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Boxy_Spring extends Boxy_Special_Object{

	private static BitmapTextureAtlas SpringTextureAtlas;  //弹簧贴图
	private static TiledTextureRegion SpringTextureRegion;  //弹簧纹理Region
	public IUpdateHandler springUpdate;
	
	public final static FixtureDef SpringFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.9f, 0f);  //固体参数
	
	public Boxy_Spring(Boxy_Board board,float percentage,final MyBoxyBetaActivity p)
	{
		super(board,p);
		this.x=parterner.getX();
		this.y=parterner.getY();
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		SpringTextureAtlas = new BitmapTextureAtlas(p.getTextureManager(), 64, 64,TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		SpringTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(SpringTextureAtlas, p, "spring.png", 0, 0,3,1);
		SpringTextureAtlas.load();
		BoxyAnimatedSprite =new AnimatedSmoothSprite(x, y+(parterner.getlength())*percentage/2 ,20, 30, SpringTextureRegion, p.getVertexBufferObjectManager());   //弹簧精灵的实现
		
		//BoxySprite.setRotationCenterX(parterner.BoxySprite.getRotationCenterX());
		//BoxySprite.setRotationCenterY(parterner.BoxySprite.getRotationCenterY());
		
		p.mScene.attachChild(BoxyAnimatedSprite);
		BoxyAnimatedSprite.setRotationCenter(10, 15);
		BoxyAnimatedSprite.setRotation(parterner.getangle());
		BoxyBody = PhysicsFactory.createBoxBody(p.mPhysicsWorld, BoxyAnimatedSprite, BodyType.StaticBody, SpringFixtureDef);  //弹簧刚体的实现
		
		p.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(BoxyAnimatedSprite,BoxyBody,true,true));
		
		springUpdate = new IUpdateHandler() {
			
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				if(p.ASprite.collidesWith(BoxyAnimatedSprite)||p.BSprite.collidesWith(BoxyAnimatedSprite))
				{
					BoxyAnimatedSprite.animate(50, 1);
				}
			}
		};
		p.mScene.registerUpdateHandler(springUpdate);
	}
}