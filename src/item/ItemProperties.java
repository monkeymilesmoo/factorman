package src.item;

import java.util.HashMap;

public class ItemProperties {
	
	public int stackSize;
	public String itemType;
	public boolean placeable;
	public int invSize;
	public boolean burnsFuel;
	//Implement fuel types and such eventually
	
	public static HashMap<String, ItemProperties> itemPropertyMap = new HashMap<String, ItemProperties>();
	//TODO for now, I dont want to save values like this in every item instance to save space, so it gets searched from here


	public ItemProperties(int stackSize, String itemType, int invSize){
		
		this.stackSize = stackSize;
		this.itemType = itemType;
		this.invSize = invSize;
		if(itemType == "building" || itemType == "container" || itemType == "furnace" || itemType == "assembler"){
			placeable = true;
		} 
		if(itemType == "furnace" || itemType == "boiler"){
			burnsFuel = true;
		}

	}

	public static void InitializeItemProperties(){
		
		itemPropertyMap.put("iron-ore", new ItemProperties(50, "ore", 0));
		itemPropertyMap.put("assembling-machine-1", new ItemProperties(50, "assembler", 0));
		itemPropertyMap.put("stone-furnace", new ItemProperties(50, "furnace", 0));
		itemPropertyMap.put("steel-chest", new ItemProperties(50, "container", 48));



	}

}
