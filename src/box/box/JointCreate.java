package box.box;

import org.andengine.entity.shape.IAreaShape;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.extension.physics.box2d.util.constants.PhysicsConstants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

public class JointCreate {

	public MouseJointDef mouseJointDef;
	public Body mGroundBody;
	
	public JointCreate(final IAreaShape pFace, final float x, final float y,MyBoxyBetaActivity parent) {
		final Body body = (Body) pFace.getUserData();
		 mouseJointDef = new MouseJointDef();
		 
		 this.mGroundBody = parent.mPhysicsWorld.createBody(new BodyDef());

		final Vector2 localPoint = Vector2Pool.obtain((x - pFace.getWidth() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, (y - pFace.getHeight() * 0.5f) / PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
		this.mGroundBody.setTransform(localPoint, 0);

		mouseJointDef.bodyA = this.mGroundBody;
		mouseJointDef.bodyB = body;
		mouseJointDef.dampingRatio = 0.95f;
		mouseJointDef.frequencyHz = 30;
		mouseJointDef.maxForce = (200.0f * body.getMass());
		mouseJointDef.collideConnected = true;

		mouseJointDef.target.set(body.getWorldPoint(localPoint));
		Vector2Pool.recycle(localPoint);

	}
}
