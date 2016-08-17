package com.niconator1.main;

import org.bukkit.entity.Player;

public class Cooldown {
	private int ticks;
	private int remainingticks;
	private Player p;

	public Cooldown(Player p, int ticks) {
		this.p = p;
		this.ticks = ticks;
		this.remainingticks = ticks;
	}

	public Player getPlayer() {
		return p;
	}

	public int getRemainingTicks() {
		return remainingticks;
	}

	public void setRemainingTicks(int remainingticks) {
		this.remainingticks = remainingticks;
	}

	public float getTicks() {
		return ticks;
	}
}
