package com.niconator1.api;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HorseTamer extends Kit {

	public HorseTamer(ArrayList<String> description,ItemStack icon, Rarity r, String aname, int acooldown, ItemStack aitem) {
		super("Horsetamer", description, "kits.horsetamer", icon, r, aname, acooldown, aitem);
	}

	@Override
	public void activateAbility(Player player) {
		player.setVelocity(new Vector(0, 2, 0));
	}

	@Override
	public void giveKit(Player player) {

	}
}
