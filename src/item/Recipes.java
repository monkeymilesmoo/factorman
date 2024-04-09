package src.item;

import java.util.ArrayList;

public abstract class Recipes {

	public static class Ingredient{

		public String Item;
		public int quantity;

		public Ingredient(String Item, int quantity){
			this.Item = Item;
			this.quantity = quantity;
		}
	} 
	
	static boolean enabled;
	static ArrayList<Ingredient>ingredients = new ArrayList<Ingredient>();
	static String result;
	static int resultCount = 1;
	static String category;

	//COPY THIS PER RECIPE:
	// public static RECIPENAME extends Recipes{
	// 	static{
	// 		enabled = true;
	// 	}
	// }


	//categories include: assembling(default), smelting, & more to come
	//assembling can be done by hand and shows up in inventory crafting grid

	public static class ironPlate extends Recipes{
		static{
			enabled = true;
			ingredients.add(new Ingredient("iron-ore", 1));
			result = "iron-plate";
			category = "smelting";
		}
	}

}
