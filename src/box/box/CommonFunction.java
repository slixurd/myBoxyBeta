package box.box;

public interface CommonFunction {
	public abstract float getX();   
	public abstract float getY();
	public abstract void doEffect() ; //!!!开关关联
	public abstract void destroy();//在世界中销毁物体
	public abstract void doInArea() ; //进入区域时调用
	public abstract void doSwitch();
}
