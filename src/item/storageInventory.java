package src.item;

import java.util.Arrays;
import java.util.Comparator;

import javax.lang.model.element.QualifiedNameable;

public class storageInventory {

	//For chests and players and such. Make a different class for furnaces and assemblers I think
	//Or maybe have there be a new inventory for each? im not sure
	//Maybe have a boolean for filtered or something not sure



	public Item[] invContents;
	public int invSize;
	private int remainingSlots;
	private int stackSize;
	private int aboutToUseSlots;

	public storageInventory(int invSize) {
		invContents = new Item[invSize];
		remainingSlots = invSize;
		this.invSize = invSize;

	}

	public Item addItemToInventory(Item insertingItem){
		//IMPORTANT: When using this, do "InsertingItem = foo.bar.addItemToInventory(); this will make sure to subtract inserted amount out"

		if (remainingSlots == 0){
			return insertingItem;
		}

		stackSize = ItemProperties.itemPropertyMap.get(insertingItem.itemID).stackSize;


		aboutToUseSlots = (insertingItem.quantity / stackSize);
		if((insertingItem.quantity % stackSize) != 0){
			aboutToUseSlots++;
		}
		

		for(int i = 0; i < invSize; i++){
			if(invContents[i] == null){
				if (aboutToUseSlots > remainingSlots){
					
					invContents[i] = insertingItem;
					invContents[i].quantity = remainingSlots * stackSize;
					insertingItem.quantity -= remainingSlots * stackSize;
					
					remainingSlots = 0;
					


				}else{
					
					invContents[i] = insertingItem;
					remainingSlots -= aboutToUseSlots;
					insertingItem = null;
					
				}

				//I think this works??
				Arrays.sort(invContents, new Comparator<Item>() {
					@Override
					public int compare(Item i1, Item i2) {
						if (i1 == null && i2 == null) {
							return 0; // Both are null, consider them equal
						}
						if (i1 == null) {
							return 1; // o1 is null, so it should come before o2
						}
						if (i2 == null) {
							return -1; // o2 is null, so it should come after o1
						}
						return i1.itemID.compareTo(i2.itemID);
					}
				});
		




				return insertingItem;
				
			}

			if (invContents[i].itemID == insertingItem.itemID){
						
				
				if (aboutToUseSlots >= remainingSlots){
					if ((invContents[i].quantity % stackSize) + insertingItem.quantity >= stackSize){
						remainingSlots -= aboutToUseSlots;
					}
					invContents[i].quantity += remainingSlots * stackSize;
					insertingItem.quantity -= remainingSlots * stackSize;
					remainingSlots = 0;
					return insertingItem;


				}else{
					if ((invContents[i].quantity % stackSize) + insertingItem.quantity >= stackSize){
						remainingSlots -= aboutToUseSlots;
					}
					invContents[i].quantity += insertingItem.quantity;
					
					return null;
				}
			}
			
		}
		



		//why would this be called????
		return insertingItem;
	}

	public Item removeItemFromInventory(Item removingItem){
		//IMPORTANT: When using this, do "RemovingItem = foo.bar.addItemToInventory(); this will make sure to subtract inserted amount out"
		//Probably do player.addItemToInventory(removeItemFromInventory(whateverItem)); or maybe reverse the order? im not sure yet


		stackSize = ItemProperties.itemPropertyMap.get(removingItem.itemID).stackSize;
		aboutToUseSlots = (removingItem.quantity / stackSize) + (removingItem.quantity % stackSize);
		//TODO free up this many slots

		
		for(int i = 0; i < invSize; i++){
			
		}


		return removingItem;
	}
}
