package com.scyla.physics;

import static org.lwjgl.opengl.GL11.*;

import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.scyla.entity.Entity;
import com.scyla.entity.EntityType;
import com.scyla.io.LWJGL;
import com.scyla.io.ScreenMaster;
import com.scyla.shapes.Box;
import com.scyla.shapes.Plan;
import com.scyla.shapes.ShapeMaster;
import com.scyla.shapes.Sphere;
import com.scyla.utils.ColorRGBb;
import com.scyla.utils.Vector3;
import com.scyla.entity.Player;

public class PhysicMaster {
	//Physical properties
	private List<Entity> rigidBodyList = new LinkedList<Entity>();
	private boolean gravityOn = false;
	private Vector3 gravity;
	private Entity grabbedEntity = null;

	//Graphical properties
	private ShapeMaster shapeMaster = new ShapeMaster();

	//Physic
	public void setGravity(Vector3 gravity) {
		if (!gravityOn) {
			for (Entity entity : rigidBodyList) {
				entity.setGravity(gravity);
			}
			this.gravity = gravity;
			gravityOn = true;
		}
	}

	public void setGrabbedEntity(Entity grabbedEntity) {
		this.grabbedEntity = grabbedEntity;
	}

	public Entity getGrabbedEntity() {
		return grabbedEntity;
	}

	public Vector3 getGravity() {
		return gravity;
	}


	//Methods
	public void applyPhysic() {

		//First of all, we update the entities' position.
		for (Entity entity : rigidBodyList) {
			if (entity instanceof Player && ((Player)entity).flying())
				;
			else
				entity.applyForces();
		}

		//We update the speed of the grabbed entity accordingly to the player speed.		
		if (grabbedEntity != null){
			Player player = LWJGL.getPlayer();
			Vector3 grabbedEntitySpeed = grabbedEntity.getSpeed();
			grabbedEntitySpeed.x = player.getMotionSpeed().x;
			grabbedEntitySpeed.y = player.getMotionSpeed().z;
			grabbedEntitySpeed.y = player.getMotionSpeed().z;
		}


		//This boolean is created so that two still entities won't be checked if they collide.
		Entity a, b;
		boolean firstMoving = false;

		for (int i = 0; i < rigidBodyList.size() - 1; i++) {
			firstMoving = false;
			a = rigidBodyList.get(i);

			if (a.moving())
				firstMoving = true;

			for (int j = i + 1; j < rigidBodyList.size(); j++) {

				b = rigidBodyList.get(j);

				if (firstMoving || b.moving()) {
					if (colide(a, b)){
						dynamic(a, b);
						if(a.getSpeed().lengthSquare() < 1E-4)
							a.setSpeed(new Vector3(0,0,0));
						if(b.getSpeed().lengthSquare() < 1E-4)
							b.setSpeed(new Vector3(0,0,0));
					}
				}
			}
		}

	}

	public boolean colide(Entity a, Entity b) {
		//TODO: Take in consideration the static types of entity

		// Sphere vs Sphere
		if (a instanceof Sphere && b instanceof Sphere) {
			Vector3 n = a.getPosition().sub(b.getPosition());
			double R = ((Sphere)a).getRadius() + ((Sphere)b).getRadius();
			
			if(n.lengthSquare() < R*R){
				boolean aMov = a.moving(), bMov = b.moving();
				n.normalise();

				Vector3 vA = a.getSpeed();
				Vector3 vB = b.getSpeed();
				
				double a1 = vA.dot(n), a2 = vB.dot(n);
				double ma = a.getMass(), mb = b.getMass(), m = ma + mb;
				double P = 2*(a1 - a2) / m;

				//Vector3 vAnew = vA.sub(n.scale(b.getMass()*P));
				//Vector3 vBnew = vB.add(n.scale(a.getMass()*P));

				//System.out.println("Anew: " + vAnew + " Bnew: " + vBnew+ "  ");

				if(!a.isGrabbed())
					//a.setSpeed(vAnew);
					a.impulse(n.scale(-b.getMass()*P));
				if(!b.isGrabbed())
					//b.setSpeed(vBnew);
					b.impulse(n.scale(a.getMass()*P));
				return true;
			}

			return false;
		}



		// Plan VS Sphere
		else if (a instanceof Plan && b instanceof Sphere) {
			Vector3 posA = a.getPosition(), posB = b.getPosition();

			Vector3 nA = ((Plan) a).getNormal();
			Vector3 P = posA.sub(posB);

			double scalar = nA.x * P.x + nA.y * P.y + nA.z * P.z;

			if (Math.abs(scalar) < ((Sphere) b).getRadius()) {
				b.setY(a.getY() + ((Sphere) b).getRadius());
				return true;
			}
		}

		// Sphere VS Plan
		else if (a instanceof Sphere && b instanceof Plan) {
			Vector3 posA = a.getPosition(), posB = b.getPosition();

			Vector3 nB = ((Plan) b).getNormal();
			Vector3 P = posA.sub(posB);

			double scalar = nB.x * P.x + nB.y * P.y + nB.z * P.z;

			if (Math.abs(scalar) < ((Sphere) a).getRadius()) {
				a.setY(b.getY() + ((Sphere) a).getRadius());
				return true;
			}

			//Player VS Plan
		} else if (a instanceof Player && b instanceof Plan){
			if(a.getY() < b.getY() ){
				a.setY(b.getY());
				a.setDY(0);
				return true;
			}
		}
		return false;
	}



	public void dynamic(Entity a, Entity b) {
		/*if (a instanceof Sphere && b instanceof Sphere) {
			boolean aMov = a.moving(), bMov = b.moving();
			Vector3 n = a.getPosition().sub(b.getPosition()).normalise();

			Vector3 vA = a.getSpeed();
			Vector3 vB = b.getSpeed();
			double a1 = vA.dot(n), a2 = vB.dot(n);
			double ma = a.getMass(), mb = b.getMass(), m = ma + mb;
			double P = 2*(a1 - a2) / m;

			if( !aMov && bMov) { //A is static but B moves
				a.setSpeed(n.scale(vB.dot(n)*mb/m));
				b.setSpeed(vB.add(n.scale(-2*vB.dot(n)*ma/m)));
			} else if (aMov && bMov){
				b.setSpeed(n.scale(vA.dot(n)*ma/m));
				a.setSpeed(vA.add(n.scale(-2*vA.dot(n)*mb/m)));
			} else {

			Vector3 vAnew = vA.sub(n.scale(b.getMass()*P)).scale(1 - (a.getBounce() + b.getBounce())/2);
			Vector3 vBnew = vB.add(n.scale(a.getMass()*P)).scale(1 - (a.getBounce() + b.getBounce())/2);

			//System.out.println("Anew: " + vAnew + " Bnew: " + vBnew+ "  ");

			if(!a.isGrabbed())
				a.setSpeed(vAnew);
			if(!b.isGrabbed())
				b.setSpeed(vBnew);

			if (Math.abs(vAnew.length() - vA.length()) > 5 || Math.abs(vBnew.length() - vB.length()) > 5){
				System.out.println(a1 + " " + a2 + " " + P);
			}
			}	



		}
			else*/ if (a instanceof Plan && b instanceof Sphere) {
				Vector3 nA = ((Plan) a).getNormal();

				Vector3 speed = b.getSpeed();
				b.setY(a.getY() + ((Sphere) b).getRadius());

				b.setSpeed(new Vector3(0.98f * (speed.x - b.getBounce() * nA.x
						* speed.x), speed.y - b.getBounce() * nA.y * speed.y,
						0.98f * (speed.z - b.getBounce() * nA.z * speed.z)));

			}

			else if (a instanceof Sphere && b instanceof Plan) {
				Vector3 nB = ((Plan) b).getNormal();

				Vector3 speed = a.getSpeed();
				a.setY(b.getY() + ((Sphere) a).getRadius());

				a.setSpeed(new Vector3(0.98f * (speed.x - a.getBounce() * nB.x
						* speed.x), speed.y - a.getBounce() * nB.y * speed.y,
						0.98f * (speed.z - a.getBounce() * nB.z * speed.z)));
			}

	}

	public void grabOject() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		ScreenMaster.disableFeature();

		GL11.glColor3f(0f, 0f, 0f);

		LWJGL.renderScene();

		ByteBuffer red = BufferUtils.createByteBuffer(1);
		ByteBuffer green = BufferUtils.createByteBuffer(1);
		ByteBuffer blue = BufferUtils.createByteBuffer(1);

		glPixelTransferi(GL_RED_SCALE, 1);
		glPixelTransferi(GL_GREEN_SCALE, 1);
		glPixelTransferi(GL_BLUE_SCALE, 1);

		glPixelTransferi(GL_RED_BIAS, 0);
		glPixelTransferi(GL_GREEN_BIAS, 0);
		glPixelTransferi(GL_BLUE_BIAS, 0);

		glReadPixels(LWJGL.WIDTH / 2, LWJGL.HEIGHT / 2, 1, 1, GL_RED, GL_BYTE,
				red);
		glReadPixels(LWJGL.WIDTH / 2, LWJGL.HEIGHT / 2, 1, 1, GL_GREEN, GL_BYTE,
				green);
		glReadPixels(LWJGL.WIDTH / 2, LWJGL.HEIGHT / 2, 1, 1, GL_BLUE, GL_BYTE,
				blue);

		int r = red.get();
		int g = green.get();
		int b = blue.get();

		if (Keyboard.isKeyDown(Keyboard.KEY_V))
			System.out.println(r + " " + g + " " + b);

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		ScreenMaster.enableFeatures();

		for (Entity entity : this.rigidBodyList) {
			ColorRGBb indice = entity.getIndiceRGB();
			if (indice.r == r && indice.g == g
					&& indice.b == b) {
				if (entity.getEntityType() == EntityType.DYNAMIC_PICKABLE) {
					this.releaseEntity();
					grabbedEntity = entity;
					entity.grab();
					break;
				}
			}
		}
	}

	public void releaseEntity() {
		if (grabbedEntity != null) {
			grabbedEntity.release();
			if (gravity != null)
				grabbedEntity.setGravity(gravity);
		}

		grabbedEntity = null;
	}





	//Graphic
	public void drawEntities() {
		for (Entity entity : rigidBodyList) {
			entity.draw();
		}
	}




	//Entities
	public void addEntity(Entity entity) {
		rigidBodyList.add(entity);
	}

	public void removeEntity(Entity entity) {
		rigidBodyList.remove(entity);
	}

	public void addBox(Vector3 position, float width, float height,
			float length, boolean outside) {
		Box box = shapeMaster.createSquare(width, height, length, outside);

		box.setPosition(position);

		if (gravityOn) {
			box.setGravity(gravity);
		}

		rigidBodyList.add(box);
		System.out.println("Entity added in position: (" + position.x + ","
				+ position.y + "," + position.z + " )");
	}

	public void addSphere(Vector3 position, float radius, int slice, int stack) {

		Sphere sphere = shapeMaster.createSphere(radius, slice, stack);

		sphere.setPosition(position);

		if (gravityOn) {
			sphere.setGravity(gravity);
		}
		rigidBodyList.add(sphere);
		System.out.println("Entity added in position: (" + position.x + ","
				+ position.y + "," + position.z + ")");
	}

	public void addSphere(Vector3 position, float mass, float radius,
			int slice, int stack) {

		Sphere sphere = shapeMaster.createSphere(radius, slice, stack);

		sphere.setPosition(position);

		if (gravityOn) {
			sphere.setGravity(gravity);
		}

		sphere.setMass(mass);

		rigidBodyList.add(sphere);
		System.out.println("Entity added in position: (" + position.x + ","
				+ position.y + "," + position.z + ")");
	}

	public void addCylindre(Vector3 position, float baseRadius,
			float topRadius, float height, int slices, int stacks) {

		com.scyla.shapes.Cylinder cylinder = shapeMaster.createCylindre(
				baseRadius, topRadius, height, slices, stacks);

		cylinder.setPosition(position);

		if (gravityOn) {
			cylinder.setGravity(gravity);
		}

		rigidBodyList.add(cylinder);
	}

	public void addPlan(Vector3 position, float width, float length, boolean up) {
		Plan plan = shapeMaster.createPlan(width, length, up);

		plan.setPosition(position);
		plan.disableDynamic();

		if (gravityOn) {
			plan.setGravity(gravity);
		}

		rigidBodyList.add(plan);

	}

	public void reset() {
		rigidBodyList = new LinkedList<Entity>();
		gravityOn = false;
		gravity = null;
	}

}
