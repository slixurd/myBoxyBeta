package box.box;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsConnectorManager;

import android.provider.Settings.System;

public class Boxy_Level {

/*
 * 总存储模式
 * 总物品数 x，（物品信息）*x
 * 物品编码:
 * （绿方块，0，蓝方块1)，（普通11，木板12，镜子13)，（引力区21，斥力区22，镜源区23)，（特殊-开关31，特殊-弹簧32，特殊-锚点33)
 * 物品信息:
 * 方块类（物品编码，物品x，物品y，0，0)
 * 挡板类（物品编码，物品x，物品y，物品长度，角度)     
 * 区域类（物品编码，物品x，物品y，区域半径，核心强度)       //x，y为圆心位置
 * 区域类-镜源区（物品编码，区域长度，关联体下标（回溯量），0，0）
 * 特殊类-开关（物品编码，绑定物体下标，关联体对应物体的下标，0，0）
 * 特殊类-弹簧（物品编码，绑定物体下标，百分比,弹力指数，0） //弹力指数越高，速度增量越大
 * 特殊类-锚点（物品编码，关联体对应物体的下标，位置%，0，0）        //锚点的位置表示为关联物体的X%的地方
 */
/*
	5,
		11,180,700,120,90,
		11,175,580,120,0,
		11,295,580,120,0,
		11,180,500,120,0,
		22,240,250,100,5000,
		0,210,480,0,0,
		1,210,650,0,0,
 */
	private final static float[] level={
		4, 
		   11, 31,130,72,90, 
		   11, 356,710,72,90, 
		   0, 40,40,0,0, 
		   1, 365,620,0,0,   //0-1
		7, 
		   12, 425,166,144,30, 
		   13, 245,530,116,10, 
		   23, 60,-1,0,0, 
		   11, 31,130,72,90, 
		   11, 356,710,72,90, 
		   0, 40,40,0,0, 
		   1, 365,620,0,0,   //0-2
		6, 
		   22, 323,200,85,5000, 
		   22, 147,500,85,8000, 
		   11, 31,100,72,90, 
		   11, 371,660,72,90,
		   0, 40,40,0,0, 
		   1, 380,600,0,0,    //0-3
		11, 
		   13, 160,275,104,-60, 
		   23, 50,-1,0,0,
		   13, 304,275,104,60,
		   23, 50,-1,0,0, 
		   12, 232.5f,250,200,0, 
		   12, 232.5f,455,200,0,
		   21, 245,210,100,2000, 
		   11, 31,660,72,90, 
		   11, 371,660,72,90, 
		   0, 40,400,0,0, 
		   1, 380,400,0,0,     //0-4
		9, 
			11,180,450,64,-15,
			11,371,580,72,90,
			31,-2,-1,0,0,
			11, 340,200,64,-15,
			32,-1,0.5f,1,0,
			11, 31,300,72,90, 
			11, 371,660,72,90, 
			0, 40,240,0,0, 
			1, 380,600,0,0,  //0-5   
		7, 
			12, 240,150,200,0,
			12, 240,352,200,0,
			12, 150,352,200,90,
			11, 31,130,72,90, 
			11, 31,660,72,90,
			0, 40,40,0,0, 
			1, 40,600,0,0,   //AM-1
		10, 
			13, 232,100,200,0,
		    23, 100,-1,0,0,
		    13, 332,274,56,0,
		    23, 50,-1,0,0,
		    11, 234,302,200,0,
		    11, 314,332,152,0,
		    11, 31,130,72,90,
		    11, 371,560,72,90, 
		    0, 40,40,0,0, 
		    1, 380,400,0,0,   //AM-2
		15,
			12, 300,370,64,90,
			11, 420,210,104,10,
			32,-1,0.5f,1,0,
			11, 100,150,200,0,
			11, 100,352,200,0,
			13, 240,300,80,-15,
			23, 50,-1,0,0,
			13, 250,530,80,-15,
			23, 100,-1,0,0,
			13, 120,575,40,0,
			23, 15,-1,0,0,
			11, 31,130,72,90, 
			11, 31,660,72,90,
			0, 40,40,0,0, 
			1, 40,600,0,0,     //AM-3
		13,
			11, 371,580,72,90, 
			13, 270,250,104,60,
			23, 50,-1,0,0,
			13, 280,350,64,90,
			23, 50,-1,0,0,
			13, 250,430,72,120,
			23, 50,-1,0,0,
			13, 200,520,104,150,
			23, 50,-1,0,0,
			11, 31,130,72,90, 
			11, 371,660,72,90, 
			0, 40,40,0,0, 
			1, 380,600,0,0,  //AM-4  
		13,
			11,180,700,120,90,
			11,173,530,160,0,
			11,295,530,160,0,
			11,180,500,120,90,
			11,50,180,120,0,
			11,420,180,120,0,
			11,104,140,120,60,
			11,440,87,120,300,
			11,180,190,120,90,
			31,-1,-6,0,0,
			22,250,250,100,5000,
			0,215,400,0,0,
			1,215,650,0,0,	//AM-5
		10,
			11,400,280,200,0,
			11,50,100,120,90,
			11,50,700,120,90,
			22,180,380,150,6000,
			11,250,130,100,0,
			23,80,-1,0,0,
			11,250,500,100,0,
			23,80,-1,0,0,
			0,80,50,0,0,
			1,80,650,0,0,
		};

public int level_start_index=0;
public Boxy_Object[] object_array;
private static MyBoxyBetaActivity parent;



public Boxy_Level (int lv,MyBoxyBetaActivity g)
{
	level_start_index=seekLevel(lv);
	parent=g;

}

public int seekLevel(int lv)
{
	int i;
	int seeker;
	i=1;
	seeker=0;
	while(i<lv)
	{
		seeker=findnext(seeker);
		++i;
	}
	return seeker;
}

public int findnext(int num)
{
	int temp;
	temp=num;
	temp+=(int)(level[num]*5+1);
	return temp;
}

public void InitialObject()
{
	int lv_num=(int)level[level_start_index];
	object_array=new Boxy_Object[lv_num-2];
	int i;
	int seeker;
	for(i=0;i<lv_num-2;++i)
	{
		object_array[i]=null;
	}
	seeker=level_start_index+(lv_num-2)*5+1;
	parent.createACube(level[seeker+1], level[seeker+2]);
	parent.ACubeBody.setAwake(false);
	parent.createBCube(level[seeker+1+5], level[seeker+2+5]);
	parent.BCubeBody.setAwake(false);
	
	parent.GameOverModifierA.reset();
	parent.GameOverModifierB.reset();
	
	//parent.ASprite.registerEntityModifier(parent.GameOverModifierA);
	//parent.BSprite.registerEntityModifier(parent.GameOverModifierB);
	
	for(i=0;i<lv_num-2;++i)
	{
		seeker=level_start_index+i*5+1;
		switch((int)level[seeker])
		{
		case 11:
			object_array[i]=(Boxy_Object)new Boxy_NormalBoard(level[seeker+1], level[seeker+2], level[seeker+3], level[seeker+4],parent);
			break;
		case 12:
			object_array[i]=(Boxy_Object)new Boxy_WoodBoard(level[seeker+1], level[seeker+2], level[seeker+3], level[seeker+4],parent);
			break;
		case 13:
			object_array[i]=(Boxy_Object)new Boxy_MirrorBoard(level[seeker+1], level[seeker+2], level[seeker+3], level[seeker+4],parent);
			break;
		case 21:
			object_array[i]=(Boxy_Object)new Boxy_BlackHole(level[seeker+1],level[seeker+2],level[seeker+3],level[seeker+4],parent);
			object_array[i].doInArea();
			break;
		case 22:
			object_array[i]=(Boxy_Object)new Boxy_ExcludeHole(level[seeker+1],level[seeker+2],level[seeker+3],level[seeker+4],parent);
			break;
		case 23:
			//object_array[i]=(Boxy_Object)new Boxy_MirrorArea(level[seeker+(int)level[seeker+2]*5+1]-1/2*level[seeker+(int)level[seeker+2]*5+3],level[seeker+(int)level[seeker+2]*5+2],level[seeker+(int)level[seeker+2]*5+4],level[seeker+(int)level[seeker+2]*5+3],level[seeker+1],parent);
			object_array[i]=(Boxy_Object)new Boxy_MirrorArea(level[seeker+(int)level[seeker+2]*5+1],level[seeker+(int)level[seeker+2]*5+2],level[seeker+(int)level[seeker+2]*5+4],level[seeker+(int)level[seeker+2]*5+3],level[seeker+1],parent);
			object_array[i].doInArea();

			//final float centerX,final float centerY,final float angle,final float length,final float radias,MyBoxyBetaActivity GameActivitylevel
			break;
		case 31:
			object_array[i]=(Boxy_Object)new Boxy_Switch((Boxy_Board)object_array[i+(int)level[seeker+1]],object_array[i+(int)level[seeker+2]],parent);
			break;
		case 32:
			object_array[i]=(Boxy_Object)new Boxy_Spring((Boxy_Board)object_array[i+(int)level[seeker+1]],level[seeker+2],parent);
			break;
		case 33:
			;
			break;
		default:
			;//error
		}	
	}
	
	parent.ACubeBody.setAwake(true);
	parent.BCubeBody.setAwake(true);
	parent.Cube_touched_num = -1;
	parent.CoverHelp(parent.level_index-1);
}

public void DestroyLevel()
{
	int lv_num=(int)level[level_start_index];
	for(int i=0;i<lv_num-2;++i)
	{
		if(object_array[i]!=null)
		{
			if(!object_array[i].isrubbish)
				object_array[i].destroy();
			else
				object_array[i]=null;
		}
		java.lang.System.gc();
	}
//	parent.mPhysicsWorld.clearPhysicsConnectors();
//	parent.mPhysicsWorld.getAutoClearForces();
//	PhysicsConnectorManager pc= parent.mPhysicsWorld.getPhysicsConnectorManager();
	//parent.mPhysicsWorld.unregisterPhysicsConnector(pc.findPhysicsConnectorByShape(pShape));
//	parent.mScene.detachChild(parent.ASprite);
//	parent.mScene.detachSelf();
//	parent.mPhysicsWorld.destroyBody(parent.ACubeBody); 
	}
}
