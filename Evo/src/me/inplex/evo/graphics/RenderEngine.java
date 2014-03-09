package me.inplex.evo.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;

import me.inplex.evo.Game;
import me.inplex.evo.entity.Bullet;
import me.inplex.evo.entity.Grass;
import me.inplex.evo.entity.Heart;
import me.inplex.evo.entity.RenderObject;
import me.inplex.evo.entity.Tree;
import me.inplex.evo.entity.Water;
import me.inplex.evo.entity.Zombie;
import me.inplex.evo.net.Net;
import me.inplex.evo.net.OtherPlayer;

public class RenderEngine {

	private Set<RenderObject> renderObjects;
	private Set<RenderObject> map;
	private Graphics graphics;

	public RenderEngine() {
		renderObjects = new HashSet<RenderObject>();
	}

	public void render() {
		graphics.setColor(Color.BLACK);
		try {
			if (map != null) {
				for (RenderObject r : map) {
					if (r instanceof Grass) {
						r.render(graphics);
						r.update();
					}
				}
				for (RenderObject r : map) {
					if (r instanceof Water) {
						r.render(graphics);
						r.update();
					}
				}
				for (RenderObject r : map) {
					if (r instanceof Tree) {
						r.render(graphics);
						r.update();
					}
				}
				for (RenderObject r : map) {
					if (r instanceof Heart) {
						r.render(graphics);
						r.update();
					}
				}
			}
			for (RenderObject r : renderObjects) {
				if (r == null) {
					renderObjects.remove(r);
				}
				r.render(graphics);
				r.update();
			}
			for (RenderObject r : Zombie.getZombies()) {
				if (r == null) {
					Zombie.getZombies().remove(r);
				}
				r.render(graphics);
				r.update();
			}
			for (RenderObject r : Bullet.bullets) {
				if (r == null) {
					Bullet.bullets.remove(r);
				}
				r.render(graphics);
			}
			
			//
			for (OtherPlayer o : Net.otherPlayers) {
				o.render(graphics);
			}
			//
			
			renderHealth();
			renderKills();
			renderName();
		} catch (ConcurrentModificationException e) {
		}

	}

	private void renderHealth() {

		// PLAYER
		graphics.setColor(Color.GREEN);
		graphics.fillRect(Game.game.player.getX() - 2, Game.game.player.getY() - 10, Game.game.player.getHealth() / 4, 10);
		if (Game.game.player.isDead()) {
			graphics.setColor(Color.RED);
			graphics.drawString("DEAD", Game.game.player.getX(), Game.game.player.getY());
		}

		// ZOMBIES
		graphics.setColor(Color.RED);
		for (Zombie z : Zombie.getZombies()) {
			graphics.fillRect(z.getX() - 2, z.getY() - 10, z.getHealth() / 4, 10);
		}

	}

	private void renderName() {

		graphics.setColor(Color.BLUE);
		graphics.drawString(Game.name, Game.game.player.getX() - 5, Game.game.player.getY() + 30);

	}

	private void renderKills() {
		graphics.setColor(Color.ORANGE);
		graphics.drawString("Kills: " + String.valueOf(Game.game.player.getKills()), Game.game.player.getX() - 100, Game.game.player.getY() - 90);
	}

	public void removeFromMap(RenderObject r) {
		map.remove(r);
	}

	public void addRenderObject(RenderObject r) {
		renderObjects.add(r);
	}

	public void removeRenderObject(RenderObject r) {
		renderObjects.remove(r);
	}

	public Set<RenderObject> getRenderObjects() {
		return renderObjects;
	}

	public void setRenderObjects(Set<RenderObject> renderObjects) {
		this.renderObjects = renderObjects;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public void setGraphics(Graphics graphics) {
		this.graphics = graphics;
	}

	public Set<RenderObject> getMap() {
		return map;
	}

	public void setMap(Set<RenderObject> map) {
		this.map = map;
	}

}
