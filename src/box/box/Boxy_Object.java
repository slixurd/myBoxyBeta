package box.box;

import org.andengine.extension.physics.box2d.PhysicsConnector;

import com.badlogic.gdx.physics.box2d.Body;


class Boxy_Object implements CommonFunction{
	public MyBoxyBetaActivity parent = null;    //ָ��MyBoxyActivity
	public Body BoxyBody = null;  //����
	public SmoothSprite BoxySprite = null;  //����
	public AnimatedSmoothSprite BoxyAnimatedSprite = null;  //AnimatedSprite
	
	public  PhysicsConnector BoxyConnector;
	public boolean isrubbish=false;
	public float x = 0f;      //����x����   ���Ͻ�!!!
	public float y = 0f;      //����Y����
	public int property_num = -1;
	/**
	 * 
	 * @param pX
	 * @param pY
	 * @param pWidth
	 * @param pHeight
	 * @param pTextureRegion
	 * @param pVertexBufferObjectManager
	 */
	public Boxy_Object(MyBoxyBetaActivity p)
	{
		parent = p;
	}
	
	public void doEffect()  //!!!���ع���
	{
		
	}
	public void destroy()//����������������
	{
		isrubbish=true;
	}
	public void doInArea()  //��������ʱ����
	{
		
	}
	
	public void doSwitch()
	{
		
	}
	//static value

	public float getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public float getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	
	//attribute
//	private BitmapTextureAtlas Object_TextureAtlas;
//	private SmoothSprite Object_Sprite;
//	private ITextureRegion Object_TextureRegion;
//	private FixtureDef Object_Fixture;
//	private Body Object_Body;
//	private float Object_radius;
//	private float Object_width;
//	private float Object_height;
//	
//	
//	abstract Body Create_RectangleObjectBody(float width,float height);
//	abstract Body Create_CircleObjectBody(float radius);
//	abstract void Initial_TextureAtlas(int Width,int Height);
//	abstract void Initial_TextureRegion(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTextureX, int pTextureY);
//	abstract void Initial_Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager);
//	abstract void Set_Sprite_Location(float x,float y);
//	abstract void Set_Sprite_Velocity(float x,float y);
	//Initial TextureAtlas-->Region-->Atlas.load-->Sprite-->createBoxBody-->attachchild
}


//class Boxy_Object  {
//	public MyBoxyBetaActivity parent;    //ָ��MyBoxyActivity
//	public Body BoxyBody;  //����
//	public SmoothSprite BoxySprite;  //����
//	public float x = 0f;      //����x����   ���Ͻ�!!!
//	public float y = 0f;      //����Y����
//	public int property_num = -1;
//	/**
//	 * 
//	 * @param pX
//	 * @param pY
//	 * @param pWidth
//	 * @param pHeight
//	 * @param pTextureRegion
//	 * @param pVertexBufferObjectManager
//	 */
//	public Boxy_Object(MyBoxyBetaActivity p)
//	{
//		parent = p;
//	}
//	
//	public void doEffect()  //!!!���ع���
//	{
//		
//	}
//	
//	public void doInArea()  //��������ʱ����
//	{
//		
//	}
//	
//	public void doSwitch()
//	{
//		if(this.property_num == 11|this.property_num == 12 |this.property_num == 13)
//		{
//			parent.mScene.detachChild(BoxySprite);
//			parent.mPhysicsWorld.destroyBody(BoxyBody);
//			//���ٸ���;���
//		}
//	}
//	
//	public void destroy()
//	{
//		
//	}
//	//static value
//	
//	
//	//attribute
////	private BitmapTextureAtlas Object_TextureAtlas;
////	private SmoothSprite Object_Sprite;
////	private ITextureRegion Object_TextureRegion;
////	private FixtureDef Object_Fixture;
////	private Body Object_Body;
////	private float Object_radius;
////	private float Object_width;
////	private float Object_height;
////	
////	
////	abstract Body Create_RectangleObjectBody(float width,float height);
////	abstract Body Create_CircleObjectBody(float radius);
////	abstract void Initial_TextureAtlas(int Width,int Height);
////	abstract void Initial_TextureRegion(BitmapTextureAtlas pBitmapTextureAtlas, Context pContext, String pAssetPath, int pTextureX, int pTextureY);
////	abstract void Initial_Sprite(float pX, float pY, float pWidth, float pHeight, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager);
////	abstract void Set_Sprite_Location(float x,float y);
////	abstract void Set_Sprite_Velocity(float x,float y);
//	//Initial TextureAtlas-->Region-->Atlas.load-->Sprite-->createBoxBody-->attachchild
//}
