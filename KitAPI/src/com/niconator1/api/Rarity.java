package com.niconator1.api;

import org.bukkit.ChatColor;

public enum Rarity {
	WHITE, BLUE, RED, BLACK;

	public ChatColor getColor() {
		if (this == WHITE)
			return ChatColor.WHITE;
		else if (this == BLUE)
			return ChatColor.BLUE;
		else if (this == RED)
			return ChatColor.RED;
		return ChatColor.BLACK;
	}
}
