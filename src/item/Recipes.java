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
	static int resultCount = 1; //Defaults to making 1 at once
	static String category; //categories include: assembling(default), smelting, & more to come
	//assembling can be done by hand and shows up in inventory crafting grid
	static float craftingTime = 0.5F; //default crafting time in seconds

	//COPY THIS PER RECIPE:
	//**Split where options are optional**
	// public static RECIPENAME extends Recipes{
	// 	static{
	// 		enabled = true;
	//		ingredients.add(new Ingredient("ingredientString", ingredientQuantity));
	//		result = "";
	//
	// 		category = "";
	//		craftingTime = time;
	// 	}
	// }



	public static class ironPlate extends Recipes{
		static{
			enabled = true;
			ingredients.add(new Ingredient("iron-ore", 1));
			result = "iron-plate";
			category = "smelting";
			craftingTime = 3.2F;
		}
	}

	public static class ironGear extends Recipes{
		static{
			enabled = true;
			ingredients.add(new Ingredient("iron-plate", 2));
			result = "iron-gear";
		}
	}

}
