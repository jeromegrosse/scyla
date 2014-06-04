package com.scyla.io;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.scyla.entity.Player;
import com.scyla.physics.PhysicMaster;
import com.scyla.utils.Vector3;

public class KeyboardMaster {

	static double speed;
	static PhysicMaster physicMaster;
	static LWJGL caller;
	Player player = null;

	public KeyboardMaster(double s, LWJGL call, PhysicMaster pm) {
		speed = s;
		caller = call;
		physicMaster = pm;
		player = LWJGL.getPlayer();
	}

	public void computeKeyboard() {
		logicKeyboard();
	}

	private void logicKeyboard() {

		player.getMotionSpeed().x = 0;
		player.getMotionSpeed().y = 0;
		player.getMotionSpeed().z = 0;

		if (Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			double addx = speed * Math.sin(Math.toRadians(player.getRY()));
			double addz = speed * Math.cos(Math.toRadians(player.getRY()));

			player.translateX(-addx);
			player.translateZ(addz);
			player.getMotionSpeed().x = -addx;
			player.getMotionSpeed().z = addz;

			if (physicMaster.getGrabbedEntity() != null) {
				physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(
						addx, 0, -addz));
			}

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			double addx = speed * Math.sin(Math.toRadians(player.getRY()));
			double addz = speed * Math.cos(Math.toRadians(player.getRY()));

			player.translateX(addx);
			player.translateZ(-addz);

			player.getMotionSpeed().x = addx;
			player.getMotionSpeed().z = -addz;

			if (physicMaster.getGrabbedEntity() != null) {
				physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(
						-addx, 0, addz));
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			double addx = speed * Math.cos(Math.toRadians(player.getRY()));
			double addz = speed * Math.sin(Math.toRadians(player.getRY()));

			player.translateX(addx);
			player.translateZ(addz);

			player.getMotionSpeed().x = addx;
			player.getMotionSpeed().z = addz;

			if (physicMaster.getGrabbedEntity() != null) {
				physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(
						-addx, 0, -addz));
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			double addx = speed * Math.cos(Math.toRadians(player.getRY()));
			double addz = speed * Math.sin(Math.toRadians(player.getRY()));

			player.translateX(-addx);
			player.translateZ(-addz);

			player.getMotionSpeed().x = -addx;
			player.getMotionSpeed().z = -addz;

			if (physicMaster.getGrabbedEntity() != null) {
				physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(
						addx, 0, addz));
			}

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			Mouse.setGrabbed(false);

		while (Keyboard.next()) {

			if (Keyboard.isKeyDown(Keyboard.KEY_E))
				physicMaster.addBox(new Vector3(Math.random() * 200,
						Math.random() * 200,
						Math.random() * 200), 10, 10, 10, true);
			if (Keyboard.isKeyDown(Keyboard.KEY_P)) {
				physicMaster.addPlan(new Vector3(-1000, -200, -1000), 2000,
						2000, true);
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_F)) {
				LWJGL.getPlayer().flyingOn();
			}

			if (Keyboard.isKeyDown(Keyboard.KEY_G)) {
				LWJGL.getPlayer().flyingOff();
			}
			
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_R))
			physicMaster.addSphere(new Vector3(Math.random() * 200,
					Math.random() * 200, Math.random() * 200),
					5, 20, 20);

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (player.flying()) {
				player.translateY(speed);

				if (physicMaster.getGrabbedEntity() != null) {
					physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(0,
							speed, 0));
				}
			} else {
				player.setDY(player.getDY() + 2f);
				System.out.println(player.getSpeed());
			}
		}
		

		if (Keyboard.isKeyDown(Keyboard.KEY_C))
			physicMaster.addSphere(new Vector3(Math.random() * 200,
					Math.random() * 200, Math.random() * 200),
					5f, 10, 20, 20);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			physicMaster.reset();
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_T)) {
			double r = Math.random();
			if (r < 0.7d) {
				if (r < 0.35d)
					physicMaster.addSphere(new Vector3(
							Math.random() * 200,
							Math.random() * 200,
							Math.random() * 200), 3f, 10, 20, 20);
				else
					physicMaster.addSphere(new Vector3(
							Math.random() * 200,
							Math.random() * 200,
							Math.random() * 200), 5f, 10, 20, 20);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			physicMaster.setGravity(new Vector3(0f, -0.05f, 0));

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			player.translateY(-speed);

			if (physicMaster.getGrabbedEntity() != null) {
				physicMaster.getGrabbedEntity().relativeTeleport(new Vector3(0,
						-speed, 0));
			}

		}

	}

}
