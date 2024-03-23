package src.item;

import java.util.HashMap;

public class ItemProperties {
	
	int stackSize;
	//Implement fuel types and such eventually
	
	public static HashMap<String, ItemProperties> itemPropertyMap = new HashMap<String, ItemProperties>();

	public ItemProperties(int stackSize){
		
		this.stackSize = stackSize;


	}

	public static void InitializeItemProperties(){
		
		itemPropertyMap.put("iron-ore", new ItemProperties(50));



	}

}
