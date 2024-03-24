package src.item;

import java.util.HashMap;

public class ItemProperties {
	
	public int stackSize;
	//Implement fuel types and such eventually
	
	public static HashMap<String, ItemProperties> itemPropertyMap = new HashMap<String, ItemProperties>();
	//TODO for now, I dont want to save values like this in every item instance to save space, so it gets searched from here


	public ItemProperties(int stackSize){
		
		this.stackSize = stackSize;


	}

	public static void InitializeItemProperties(){
		
		itemPropertyMap.put("iron-ore", new ItemProperties(50));
		itemPropertyMap.put("assembling-machine-1", new ItemProperties(50));



	}

}
