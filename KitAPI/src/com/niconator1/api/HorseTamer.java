package com.niconator1.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HorseTamer extends Kit {
	public HorseTamer(String name, String permission, ItemStack icon, Rarity r, String aname, int acooldown,
			ItemStack aitem) {
		super(name, permission, icon, r, aname, acooldown, aitem);
	}

	@Override
	public void activateAbility(Player player) {
		player.setVelocity(new Vector(0,2,0));
	}
}
