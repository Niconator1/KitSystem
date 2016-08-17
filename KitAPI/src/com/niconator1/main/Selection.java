package com.niconator1.main;

import org.bukkit.inventory.Inventory;

public class Selection {
	private Inventory i;
	private int page;
	public Selection(Inventory i,int page){
		this.i=i;
		this.page=page;
	}
	public int getPage(){
		return page;
	}
	public Inventory getInventory(){
		return i;
	}
}
