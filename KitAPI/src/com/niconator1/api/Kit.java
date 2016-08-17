package com.niconator1.api;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.niconator1.main.KitPlugin;

public abstract class Kit {
	private String name;
	private String permission;
	private ItemStack icon;
	private String aname;
	private int acooldown;
	private ItemStack aitem;
	private Rarity r;

	public Kit(String name, String permission, ItemStack icon, Rarity r, String aname, int acooldown, ItemStack aitem) {
		this.name = name;
		this.permission = permission;
		this.icon = icon;
		this.aname = aname;
		this.acooldown = acooldown;
		this.aitem = aitem;
		this.r = r;
		addIconInformation();
	}

	private void addIconInformation() {
		ItemMeta im = icon.getItemMeta();
		im.setDisplayName(ChatColor.RESET + name);
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(ChatColor.GRAY + "Rarity: " + r.getColor() + r.name());
		im.setLore(lore);
		icon.setItemMeta(im);
	}

	public void handleRightclick(Player p) {
		if (hasPermission(p)) {
			if (isHoldingAbilityItem(p)) {
				if (isAbilityReady(p)) {
					KitPlugin.addCooldown(p, acooldown);
					activateAbility(p);
				} else {
					p.sendMessage(
							ChatColor.RED + "You need to wait " + p.getLevel() + " seconds before using this again.");
				}
			}
		}
	}

	public boolean isHoldingAbilityItem(Player p) {
		ItemStack is = p.getInventory().getItem(p.getInventory().getHeldItemSlot());
		if (is != null) {
			if (is.equals(aitem)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasPermission(Player p) {
		if (p.hasPermission(permission)) {
			return true;
		}
		return false;
	}

	public boolean isAbilityReady(Player p) {
		if (KitPlugin.isAbilityReady(p)) {
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public ItemStack getIcon() {
		return icon;
	}

	public String getAbilityName() {
		return aname;
	}

	public int getAbilityCooldown() {
		return acooldown;
	}

	public Rarity getRarity() {
		return r;
	}

	public abstract void activateAbility(Player p);

}
