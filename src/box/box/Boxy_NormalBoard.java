package box.box;

import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;



//public class Boxy_NormalBoard extends Boxy_Board{
public class Boxy_NormalBoard extends Boxy_Board {
	
	private static BitmapTextureAtlas NormalBoardTextureAtlas;
	private static ITiledTextureRegion NormalBoardTextureRegion;
	

	public final static FixtureDef NormalBoardFixtureDef = PhysicsFactory.createFixtureDef(1f, 0.5f, 0.5f);  //�������
	
	
	
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param length  �峤��
	 * @param angle   ��Ƕ� ��ʼ��ֱ
	 * @param pTextureRegion
	 * @param pVertexBufferObjectManager
	 * @param BoxyActivity
	 */
	

	public Boxy_NormalBoard(float pX, float pY, float length, float angle,
			MyBoxyBetaActivity BoxyActivity) {
		super(BoxyActivity);
		this.x=(float) (pX+(length/2)*Math.sin(angle));
		this.y=(float) (pY-(length/2)*(1-Math.cos(angle)));
		this.angle = angle;
		this.length = length;
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		NormalBoardTextureAtlas= new BitmapTextureAtlas(parent.getTextureManager(), 512, 256,TextureOptions.NEAREST);
		NormalBoardTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(NormalBoardTextureAtlas,parent,"normal_board_all.png",0,0,21,1);
		NormalBoardTextureAtlas.load();
		
		int index = (int)((length - 40)/8);
//		pX+=length/2;
//		pY+=8.5;
		NormalBoardTextureRegion.setCurrentTileIndex(index);
		BoxySprite = new SmoothSprite(x, y, 17, length,NormalBoardTextureRegion, parent.getVertexBufferObjectManager());
		BoxyBody = PhysicsFactory.createBoxBody(parent.mPhysicsWorld, x+8.5f, y+length/2, 15, length - 2f, angle,BodyType.StaticBody, NormalBoardFixtureDef);
		
		parent.mScene.attachChild(BoxySprite);
		if(BoxyBody != null)
		BoxySprite.setUserData(BoxyBody);   //���Բ���~
		parent.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(BoxySprite, BoxyBody,true,true));
		BoxySprite.setRotation(angle);

		
	}
}
