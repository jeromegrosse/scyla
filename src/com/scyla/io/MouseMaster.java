package com.scyla.io;

import org.lwjgl.input.Mouse;

import com.scyla.entity.Player;
import com.scyla.physics.PhysicMaster;
import com.scyla.utils.Vector3;

public class MouseMaster {

	private static final double mouseSensibility = 0.5;
	private static LWJGL caller = null;
	private static PhysicMaster physicMaster = null;
	private static Player player = LWJGL.getPlayer();

	// Changed at each frame
	static int dx = 0, dy = 0;

	public MouseMaster(LWJGL room, PhysicMaster pm) {
		caller = room;
		physicMaster = pm;
	}

	public void computeMouse() {
		dx = Mouse.getDX();
		dy = Mouse.getDY();
		
		logicMouse();
	}

	private static void logicMouse() {


		if (Mouse.isButtonDown(0))
			Mouse.setGrabbed(true);

		if (Mouse.isGrabbed()) {
			player.setRX(player.getRX() - (float) (mouseSensibility * dy));
			player.setRY((float) ((player.getRY() + mouseSensibility * dx) % 360));

			if (physicMaster.getGrabbedEntity() != null) {
				Vector3 obj = physicMaster.getGrabbedEntity().getPosition();
				
				double cosx = Math.cos(Math.toRadians(-mouseSensibility * dx));
				double sinx = Math.sin(Math.toRadians(-mouseSensibility * dx));
				// double cosy = Math.cos(Math.toRadians(-Room.rotation.x));
				// double siny = Math.sin(Math.toRadians(-Room.rotation.x));
				
				float wx = (float) ((obj.z + player.getZ()) * sinx + (obj.x + player.getX())
						* cosx);
				
				float wz = (float) ((obj.z + player.getZ()) * cosx - (obj.x + player.getX())
						* sinx);

				Vector3 w = new Vector3(
						wx - player.getX(),
						obj.y,
						wz - player.getZ());

				physicMaster.getGrabbedEntity().setPosition(w);
				player.getRotationSpeed().x = wx;
				player.getRotationSpeed().z = wz;
			}

			if (player.getRX() > 90)
				player.setRX(90);
			else if (player.getRX() < -90)
				player.setRX(-90);

			if (Mouse.isButtonDown(0)){
				physicMaster.grabOject();
			}

			if (Mouse.isButtonDown(1))
				physicMaster.releaseEntity();
			
		}

	}

}
