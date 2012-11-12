package box.box;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;



//public class Boxy_NormalBoard extends Boxy_Board{
public class Boxy_WoodBoard extends Boxy_Board {
	
	private static BitmapTextureAtlas WoodBoardTextureAtlas;
	private static ITiledTextureRegion WoodBoardTextureRegion;
	

	public final static FixtureDef WoodBoardFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.2f, 5f);  //固体参数
	
	
	
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param length  板长度
	 * @param angle   板角度 初始竖直
	 * @param pTextureRegion
	 * @param pVertexBufferObjectManager
	 * @param BoxyActivity
	 */
	

	public Boxy_WoodBoard(float pX, float pY, float length, float angle,
			MyBoxyBetaActivity BoxyActivity) {
		super(BoxyActivity);
		this.x=(float) (pX+(length/2)*Math.sin(angle));
		this.y=(float) (pY-(length/2)*(1-Math.cos(angle)));
		this.length = length;
		this.angle = angle;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		WoodBoardTextureAtlas= new BitmapTextureAtlas(parent.getTextureManager(), 512, 256,TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		WoodBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(WoodBoardTextureAtlas,parent,"wood_board_all.png",0,0,21,1);
		WoodBoardTextureAtlas.load();
		
		int index = (int)((length - 40)/8);
		
		WoodBoardTextureRegion.setCurrentTileIndex(index);
		BoxySprite = new SmoothSprite(x, y, 17, length,WoodBoardTextureRegion, parent.getVertexBufferObjectManager());
		BoxyBody = PhysicsFactory.createBoxBody(parent.mPhysicsWorld, x+8.5f, y+length/2, 15, length - 2f, angle,BodyType.StaticBody, WoodBoardFixtureDef);
		
		
		parent.mScene.attachChild(BoxySprite);
		parent.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(BoxySprite, BoxyBody,true,true));
		
		
		BoxySprite.setRotation(angle);

		
	}
}
