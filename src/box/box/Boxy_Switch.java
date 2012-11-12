package box.box;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.color.Color;
import org.andengine.util.debug.Debug;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Boxy_Switch extends Boxy_Special_Object{

	private static TiledTextureRegion SwitchTiledTextureRegion;  //����Region
	private static BitmapTextureAtlas SwitchTextureAtlas;
	public IUpdateHandler switchUpdate;
	
	public final static FixtureDef SwitchBoardFixtureDef = PhysicsFactory.createFixtureDef(1f, 1f, 0.5f);  //�������
	
	public boolean Is_Switched = false;
	
	
	public Boxy_Switch(Boxy_Board relatedBoard,final Boxy_Object relatedObject,final MyBoxyBetaActivity p)
	{
		super(relatedBoard,p);
		////����ĳ�ʼ��!!!!!!\\\\
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("pix/");
		SwitchTextureAtlas = new BitmapTextureAtlas(p.getTextureManager(), 64, 64,TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		
		SwitchTiledTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(SwitchTextureAtlas, parent, "switch.png", 0, 0,2,1);
		SwitchTextureAtlas.load();
		
		//����ĳ�ʼ���������ó�0,0  ������ݸ�����������������  ����
		BoxyAnimatedSprite =  new AnimatedSmoothSprite(parterner.getX()+18f , parterner.getY()+(parterner.getlength())/4-2f,10, 30, SwitchTiledTextureRegion,parent.getVertexBufferObjectManager());
//		BoxyAnimatedSprite.setRotationCenter(5, 15);
		//BoxySprite.setRotationCenterX(parterner.BoxySprite.getRotationCenterX());
		//BoxySprite.setRotationCenterY(parterner.BoxySprite.getRotationCenterY());
		p.mScene.attachChild(BoxyAnimatedSprite);
		BoxyAnimatedSprite.setRotation(parterner.getangle()+180);
		switchUpdate = new  IUpdateHandler() {
			
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			public void onUpdate(float arg0) {
				// TODO Auto-generated method stub
				if(Is_Switched == false)
				{
					if(p.BSprite.collidesWith(BoxyAnimatedSprite)|p.ASprite.collidesWith(BoxyAnimatedSprite))
					{
						//����~  ��һ�������ж�����
						///������Ҫ���Ķ���
						BoxyAnimatedSprite.setCurrentTileIndex(1);
						relatedObject.doSwitch();
						Is_Switched = true;
						Debug.d("switch");
					}
				}
				else
				p.mScene.unregisterUpdateHandler(switchUpdate);
			}
		};
		p.mScene.registerUpdateHandler(switchUpdate);
}
}
