package box.box;

public class Boxy_Area extends Boxy_Object {


	public float radias;
	public float length;
	public boolean isActive;         //���Ƿ񱻼������ĳ��Ų�����ײ����

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
	public void destroy()//����������������
	{
		parent.mScene.detachChild(BoxySprite);
	}
	
	@Override
	public void doInArea() {
		super.doInArea();
		
	}
}
