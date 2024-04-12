package src.item;

import java.util.HashMap;

public class ItemProperties {
	
	public int stackSize = 50;
	public String itemType;
	public boolean placeable;
	public int invSize;
	public boolean burnsFuel;
	//Implement fuel types and such eventually
	public double propertyOne, propertyTwo; //can be belt speed, crafting speed, storage size, wire reach, etc.
	//Im sure there will be buildings with more properties so in that case make a third
	
	public static HashMap<String, ItemProperties> itemPropertyMap = new HashMap<String, ItemProperties>();


	public ItemProperties(int stackSize, String itemType, double propertyOne, double propertyTwo){
		
		this.stackSize = stackSize;
		this.itemType = itemType;
		this.propertyOne = propertyOne;
		this.propertyTwo = propertyTwo;
		if(itemType == "building" || itemType == "container" || itemType == "furnace" || itemType == "assembler"){
			placeable = true;
		} 
		if(itemType == "furnace" || itemType == "boiler"){
			burnsFuel = true;
		}

	}

	public static void InitializeItemProperties(){
		
		//Ores
		putProperty("iron-ore", 50, "ore", 0, 0);
		putProperty("copper-ore", 50, "ore", 0, 0);
		putProperty("coal", 50, "ore", 0, 0);
		putProperty("stone", 50, "ore", 0, 0);

		//// SORTED BY ITEM GROUPS

		// first thing after - is propertyone

		//Logistics

		//Containers
		putProperty("wooden-chest", 50, "container", 16, 0);
		putProperty("iron-chest", 50, "container", 32, 0);
		putProperty("steel-chest", 50, "container", 48, 0);
		putProperty("storage-tank", 50, "storage-tank", 25000, 0);

		//Belts - belt speed in items/sec
		putProperty("transport-belt", 50, "belt", 15, 0); 
		putProperty("fast-transport-belt", 50, "belt", 30, 0); 
		putProperty("express-transport-belt", 50, "belt", 45, 0); 
		//Underground belts
		putProperty("underground-belt", 50, "underground-belt", 15, 0);
		putProperty("fast-underground-belt", 50, "underground-belt", 30, 0);
		putProperty("express-underground-belt", 50, "underground-belt", 45, 0);
		//Splitters
		putProperty("splitter", 50, "splitter", 15, 0);
		putProperty("fast-splitter", 50, "splitter", 30, 0);
		putProperty("express-splitter", 50, "splitter", 45, 0);

		//Inserters - ticks per full turn. theres a photo of wiki data in inserter folder
		putProperty("burner-inserter", 50, "inserter", 100, 0);
		putProperty("inserter", 50, "inserter", 72, 0);
		putProperty("long-handed-inserter", 50, "inserter", 50, 0);
		putProperty("fast-inserter", 50, "inserter", 26, 0);
		putProperty("filter-inserter", 50, "inserter", 26, 0);
		putProperty("stack-inserter", 50, "inserter", 26, 0);
		putProperty("stack-filter-inserter", 50, "inserter", 26, 0);

		//Power poles - supply area n*n; wire reach distance n
		putProperty("small-electric-pole", 50, "electric-pole", 5, 7);
		putProperty("medium-electric-pole", 50, "electric-pole", 7, 9);
		putProperty("big-electric-pole", 50, "electric-pole", 4, 30);
		putProperty("substation", 50, "electric-pole", 18, 18);
		//Pipes
		putProperty("pipe", 50, "pipe", 0, 0);
		putProperty("pipe-to-ground", 50, "pipe", 0, 0);
		putProperty("pump", 50, "pipe", 0, 0);

		//Train items
		putProperty("rail", 50, "train-transport", 0, 0);
		putProperty("train-stop", 50, "train-transport", 0, 0);
		putProperty("rail-signal", 50, "train-transport", 0, 0);
		putProperty("rail-chain-signal", 50, "train-transport", 0, 0);
		putProperty("locomotive", 50, "train-transport", 0, 0);
		putProperty("cargo-wagon", 50, "train-transport", 0, 0);
		putProperty("fluid-wagon", 50, "train-transport", 0, 0);
		putProperty("artillery-wagon", 50, "train-transport", 0, 0);
		
		//Transport
		putProperty("car", 50, "transport", 0, 0);
		putProperty("tank", 50, "transport", 0, 0);
		putProperty("spidertron", 50, "transport", 0, 0);
		putProperty("spidertron-remote", 50, "transport", 0, 0);

		//Logistics
		putProperty("logistic-robot", 50, "logistics", 0, 0);
		putProperty("construction-robot", 50, "logistics", 0, 0);
		putProperty("logistic-chest-active-provider", 50, "logistics", 0, 0);
		putProperty("logistic-chest-passive-provider", 50, "logistics", 0, 0);
		putProperty("logistic-chest-storage", 50, "logistics", 0, 0);
		putProperty("logistic-chest-buffer", 50, "logistics", 0, 0);
		putProperty("logistic-chest-requester", 50, "logistics", 0, 0);
		putProperty("robotport", 50, "logistics", 0, 0);

		//Circuit network
		putProperty("small-lamp", 50, "circuit-network", 0, 0);
		putProperty("red-wire", 50, "circuit-network", 0, 0);
		putProperty("green-wire", 50, "circuit-network", 0, 0);
		putProperty("arithmetic-combinator", 50, "circuit-network", 0, 0);
		putProperty("decider-combinator", 50, "circuit-network", 0, 0);
		putProperty("constant-combinator", 50, "circuit-network", 0, 0);
		putProperty("power-switch", 50, "circuit-network", 0, 0);
		putProperty("programmable-speaker", 50, "circuit-network", 0, 0);

		//Terrain
		putProperty("stone-brick", 100, "terrain", 0, 0);
		putProperty("concrete", 100, "terrain", 0, 0);
		putProperty("hazard-concrete", 100, "terrain", 0, 0);
		putProperty("refined-concrete", 100, "terrain", 0, 0);
		putProperty("refined-hazard-concrete", 100, "terrain", 0, 0);
		putProperty("landfill", 100, "terrain", 0, 0);
		putProperty("cliff-explosives", 20, "terrain", 0, 0);




		putProperty("assembling-machine-1", 50, "assembler", .5, 0);
		putProperty("stone-furnace", 50, "furnace", 1, 0);



	}

	public static void putProperty(String itemID, int stackSize, String itemType, double propertyOne, double propertyTwo){
		itemPropertyMap.put(itemID, new ItemProperties(stackSize, itemType, propertyOne, propertyTwo));

	}

}
