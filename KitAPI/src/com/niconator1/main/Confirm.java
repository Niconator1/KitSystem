package com.niconator1.main;

import org.bukkit.inventory.Inventory;

import com.niconator1.api.Kit;

public class Confirm {
	private Inventory i;
	private Kit kit;

	public Confirm(Inventory i, Kit kit) {
		this.i = i;
		this.kit = kit;
	}

	public Kit getKit() {
		return kit;
	}

	public Inventory getInventory() {
		return i;
	}
}
