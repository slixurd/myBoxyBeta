package box.box;

public class Boxy_Area extends Boxy_Object {


	public float radias;
	public float length;
	public boolean isActive;         //场是否被激活，激活的场才参与碰撞检验

	public Boxy_Area(MyBoxyBetaActivity p) {
		super(p);
		isActive=true;
	}
	public void setActive(boolean sw){
		isActive=sw;
	}
	
	
	@Override
	public void doEffect() {
		super.doEffect();
		
	}

	@Override
	public void destroy()//在世界中销毁物体
	{
		parent.mScene.detachChild(BoxySprite);
	}
	
	@Override
	public void doInArea() {
		super.doInArea();
		
	}
}
