package me.inplex.evo.graphics;

import java.awt.Point;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import me.inplex.evo.Game;
import me.inplex.evo.entity.Grass;
import me.inplex.evo.entity.Heart;
import me.inplex.evo.entity.RenderObject;
import me.inplex.evo.entity.Tree;
import me.inplex.evo.entity.Water;

public class Map {

	private Set<RenderObject> set;

	private int width, height;

	public static int loadedPercent = 0;

	public int waters = 0;
	public int trees = 0;
	public int hearts = 0;
	private Set<Point> placedTrees;
	private Set<Point> placedWaters;
	private Set<Point> placedHearts;

	public Map(int width, int height) throws IOException {
		this.set = new HashSet<RenderObject>();
		this.placedTrees = new HashSet<Point>();
		this.placedWaters = new HashSet<Point>();
		this.placedHearts = new HashSet<Point>();
		this.width = width;
		this.height = height;
	}

	public void loadMap() throws IOException {
		System.out.println("Loading Map . . ");
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				// GRASS
				set.add(new Grass(x * 20, y * 20));
				int r = new Random().nextInt(1000 - 1) + 1;
				if (placedTrees.contains(new Point((x + 1) * 20, (y + 0) * 20)) || placedTrees.contains(new Point((x + 1) * 20, (y + 1) * 20)) || placedTrees.contains(new Point((x - 1) * 20, (y + 0) * 20)) || placedTrees.contains(new Point((x - 1) * 20, (y + 1) * 20))) {
					r += 100;
				}
				if (r >= 925) {
					if (r == 1000) {
						// FOREST
						int r1 = new Random().nextInt(10 - 2) + 2;
						for (int iX = 0; iX < r1; iX++) {
							for (int iY = 0; iY < r1; iY++) {
								int r2 = new Random().nextInt(3 - 1) + 1;
								if (r2 == 1) {
									set.add(new Tree((x + iX) * 20, (y + iY) * 20));
									placedTrees.add(new Point((x + iX) * 20, (y + iY) * 20));
									trees++;
								}
							}
						}
					} else {
						// TREE
						set.add(new Tree(x * 20, y * 20));
						placedTrees.add(new Point(x * 20, y * 20));
						trees++;
					}
				}
				// WATER
				if (r <= 9 && (!(r <= 5)) && !placedTrees.contains(new Point(x * 20, y * 20))) {
					set.add(new Water(x * 20, y * 20));
					placedWaters.add(new Point(x * 20, y * 20));
					waters++;
					int r1 = new Random().nextInt(6 - 2) + 2;
					for (int iX = 0; iX < r1; iX++) {
						for (int iY = 0; iY < r1; iY++) {
							if (!placedTrees.contains(new Point((x + iX) * 20, (y + iY) * 20))) {
								int r2 = new Random().nextInt(3 - 1) + 1;
								if (r2 == 1) {
									set.add(new Water((x + iX) * 20, (y + iY) * 20));
									placedWaters.add(new Point((x + iX) * 20, (y + iY) * 20));
									waters++;
								}
							}
						}
					}
				}
				// HEART
				if (r <= 5) {
					set.add(new Heart(x * 20, y * 20));
					placedHearts.add(new Point(x * 20, y * 20));
					hearts++;
				}
				loadedPercent = ((int) ((double) x / width * 100));
			}
		}
		Game.game.render.setMap(set);
		System.out.println("Trees: " + trees + " | waters: " + waters + " | hearts: " + hearts);
		System.out.println("Loaded!");
		
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
