package box.box;

public class Boxy_Board extends Boxy_Object{
	public Boxy_Board(MyBoxyBetaActivity p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	public int boardtype = 0;
	public float angle = 0;
	public float length = 0;

	
	public void destroy()//在世界中销毁物体
	{
		isrubbish=true;
		BoxyConnector = parent.mPhysicsWorld.getPhysicsConnectorManager().findPhysicsConnectorByShape(BoxySprite);
		parent.mPhysicsWorld.unregisterPhysicsConnector(BoxyConnector);
		
		
		parent.mPhysicsWorld.destroyBody(BoxyBody);
		parent.mScene.detachChild(BoxySprite);
		
		//System.gc();
		
	}

	@Override
	public void doEffect() {
		// TODO Auto-generated method stub
		//super.doEffect();
	}
	
	public float getangle()
	{
		return angle;
	}
	
	public void doSwitch()
	{
		this.destroy();
	}
	
	public float getlength()
	{
		return length;
	}
	
	
}
