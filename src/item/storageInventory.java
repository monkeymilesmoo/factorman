package src.item;


public class storageInventory {

	//For chests and players and such. Make a different class for furnaces and assemblers I think



	public Item[] invContents;
	private int invSize;
	private int remainingSlots;
	private int stackSize;
	private int aboutToUseSlots;

	public storageInventory(int invSize) {
		invContents = new Item[invSize];
		this.invSize = invSize;

	}

	public Item addItemToInventory(Item insertingItem){
		//IMPORTANT: When using this, do "InsertingItem = foo.bar.addItemToInventory(); this will make sure to subtract inserted amount out"

		if (remainingSlots == 0){
			return insertingItem;
		}

		stackSize = ItemProperties.itemPropertyMap.get(insertingItem.itemID).stackSize;


		aboutToUseSlots = (insertingItem.quantity / stackSize) + (insertingItem.quantity % stackSize);
		

		for(int i = 0; i < invSize; i++){
			if (invContents[i].itemID == insertingItem.itemID){
						
				
				if (aboutToUseSlots > remainingSlots){
					


				}else{
					invContents[i].quantity += insertingItem.quantity;
					remainingSlots -= aboutToUseSlots;

					return null;
				}
			}
		}


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
