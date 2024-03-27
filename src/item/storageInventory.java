package src.item;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;


public class storageInventory {

	//For chests and players and such. Make a different class for furnaces and assemblers I think
	//Or maybe have there be a new inventory for each? im not sure
	//Maybe have a boolean for filtered or something not sure



	public Item[] invContents;
	public HashMap<String, Item> invItemsQuantity = new HashMap<String, Item>();
	public int invSize;
	public int endIndex;
	private int stackSize;
	private int amountInserting;
	private String itemID;

	public storageInventory(int invSize) {
		invContents = new Item[invSize];
		endIndex = 0;
		this.invSize = invSize;

	}

	public Item addItemToInventory(Item insertingItem){
		//IMPORTANT: When using this, do "InsertingItem = foo.bar.addItemToInventory(); this will make sure to subtract inserted amount out"


		stackSize = ItemProperties.itemPropertyMap.get(insertingItem.itemID).stackSize;
		itemID = insertingItem.itemID;


		amountInserting = Math.min(insertingItem.quantity, (invSize - endIndex) * stackSize);

		if(amountInserting == 0){
			if(invItemsQuantity.get(itemID).quantity % stackSize != 0){
				amountInserting = Math.min(stackSize - invItemsQuantity.get(itemID).quantity % stackSize, insertingItem.quantity);
			}
		}
		insertingItem.quantity = insertingItem.quantity - amountInserting;
		if(insertingItem.quantity == 0){
			insertingItem = null;
		}


		if(invItemsQuantity.get(itemID) != null){
			invItemsQuantity.get(itemID).quantity += amountInserting;
		}else{
			invItemsQuantity.put(itemID, new Item(itemID, amountInserting));
		}


		for(int i = 0; i < invSize; i++){
			if(amountInserting == 0){
				break;
			}

			if(invContents[i] == null){
				if(amountInserting > stackSize){
					invContents[i] = new Item(itemID, stackSize);
					amountInserting -= stackSize;
					endIndex++;
				}else{
					invContents[i] = new Item(itemID, amountInserting);
					amountInserting = 0;
					endIndex++;
				}
			}else if(invContents[i].itemID == itemID && invContents[i].quantity != stackSize){
				if(amountInserting + invContents[i].quantity > stackSize){
					amountInserting -= stackSize - invContents[i].quantity;
					invContents[i].quantity = stackSize;
				}else{
					invContents[i].quantity += amountInserting;
					amountInserting = 0;
				}
			}

		}

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



		// stackSize = ItemProperties.itemPropertyMap.get(insertingItem.itemID).stackSize;


		// aboutToUseSlots = (insertingItem.quantity / stackSize);
		// if(insertingItem.quantity <  stackSize){
		// 	aboutToUseSlots++;
		// }
		

		// for(int i = 0; i < invSize; i++){
		// 	if(invContents[i] == null){
		// 		if (remainingSlots == 0){
		// 			return insertingItem;
		// 		}
		// 		if (aboutToUseSlots > remainingSlots){
					
		// 			invContents[i] = insertingItem;
		// 			invContents[i].quantity = remainingSlots * stackSize;
		// 			insertingItem.quantity -= remainingSlots * stackSize;
					
		// 			remainingSlots = 0;
					


		// 		}else{
					
		// 			invContents[i] = insertingItem;
		// 			remainingSlots -= aboutToUseSlots;
		// 			insertingItem = null;
					
		// 		}

		// 		//I think this works??
		// 		Arrays.sort(invContents, new Comparator<Item>() {
		// 			@Override
		// 			public int compare(Item i1, Item i2) {
		// 				if (i1 == null && i2 == null) {
		// 					return 0; // Both are null, consider them equal
		// 				}
		// 				if (i1 == null) {
		// 					return 1; // o1 is null, so it should come before o2
		// 				}
		// 				if (i2 == null) {
		// 					return -1; // o2 is null, so it should come after o1
		// 				}
		// 				return i1.itemID.compareTo(i2.itemID);
		// 			}
		// 		});
		




		// 		return insertingItem;
				
		// 	}

		// 	if (invContents[i].itemID == insertingItem.itemID){
						
				
		// 		if (remainingSlots == 0 && (invContents[i].quantity % stackSize) == 0){
		// 			return insertingItem;
		// 		}

		// 		if (((invContents[i].quantity % stackSize) + insertingItem.quantity) / stackSize >= remainingSlots && ((invContents[i].quantity % stackSize) + insertingItem.quantity) >= stackSize){
					

		// 			invContents[i].quantity += (Math.max(remainingSlots, 1) * stackSize) - (invContents[i].quantity % stackSize);

		// 			insertingItem.quantity -= remainingSlots * stackSize;
		// 			remainingSlots = 0;
		// 			return insertingItem;


		// 		}else{
		// 			remainingSlots -= ((invContents[i].quantity % stackSize) + insertingItem.quantity) / stackSize;
		// 			invContents[i].quantity += insertingItem.quantity;
					
		// 			return null;
		// 		}
		// 	}
			
		// }
		



		//why would this be called????
		return insertingItem;
	}

	public Item removeItemFromInventory(Item removingItem){
		//IMPORTANT: When using this, do "RemovingItem = foo.bar.addItemToInventory(); this will make sure to subtract inserted amount out"
		//Probably do player.addItemToInventory(removeItemFromInventory(whateverItem)); or maybe reverse the order? im not sure yet


		stackSize = ItemProperties.itemPropertyMap.get(removingItem.itemID).stackSize;
		//TODO actually do this

		
		for(int i = 0; i < invSize; i++){
			
		}


		return removingItem;
	}
}
