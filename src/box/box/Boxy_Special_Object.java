package box.box;

import com.badlogic.gdx.physics.box2d.Body;

public class Boxy_Special_Object extends Boxy_Object{
	public boolean used;
	public int object_type = -1;
	public Boxy_Board parterner;
	

	public Boxy_Special_Object(Boxy_Board b,MyBoxyBetaActivity p) {
		super(p);
		parterner=b;
		// TODO Auto-generated constructor stub
	}

	public void destroy()//在世界中销毁物体
	{
		isrubbish=true;
		if(BoxyBody != null)
		{
			parent.mPhysicsWorld.destroyBody(BoxyBody);
			BoxyBody = null;
		}
		if(BoxySprite != null)
		{
			parent.mScene.detachChild(BoxySprite);
			BoxySprite = null;
		}
		if(BoxyAnimatedSprite != null)
		{
			parent.mScene.detachChild(BoxyAnimatedSprite);
			BoxyAnimatedSprite = null;
		}
		System.gc();
	}
}
