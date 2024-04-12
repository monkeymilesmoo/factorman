package src.item;

public abstract class ItemGroups {
	
	public static String[][] rows;

	public static class Logistics extends ItemGroups{
		static{
			rows[0][0] = "wooden-chest";
			rows[0][1] = "iron-chest";
			rows[0][2] = "steel-chest";
			rows[0][3] = "storage-tank";
			
			rows[1][0] = "transport-belt";
			rows[1][1] = "fast-transport-belt";
			rows[1][2] = "express-transport-belt";
			rows[1][3] = "underground-belt";
			rows[1][4] = "fast-underground-belt";
			rows[1][5] = "express-underground-belt";
			rows[1][6] = "splitter";
			rows[1][7] = "fast-splitter";
			rows[1][8] = "express-splitter";

			rows[2][0] = "burner-inserter";
			rows[2][1] = "inserter";
			rows[2][2] = "long-handed-inserter";
			rows[2][3] = "fast-inserter";
			rows[2][4] = "filter-inserter";
			rows[2][5] = "stack-inserter";
			rows[2][6] = "stack-filter-inserter";
			
			rows[3][0] = "small-electric-pole";
			rows[3][1] = "medium-electric-pole";
			rows[3][2] = "big-electric-pole";
			rows[3][3] = "substation";
			rows[3][4] = "pipe";
			rows[3][5] = "pipe-to-ground";
			rows[3][6] = "pump";
			
			rows[4][0] = "rail";
			rows[4][1] = "train-stop";
			rows[4][2] = "rail-signal";
			rows[4][3] = "rail-chain-signal";
			rows[4][4] = "locomotive";
			rows[4][5] = "cargo-wagon";
			rows[4][6] = "fluid-wagon";
			rows[4][7] = "artillery-wagon";
			
			rows[5][0] = "car";
			rows[5][1] = "tank";
			rows[5][2] = "spidertron";
			rows[5][3] = "spidertron-remote";
			
			rows[6][0] = "logistic-robot";
			rows[6][1] = "construction-robot";
			rows[6][2] = "logistic-chest-active-provider";
			rows[6][3] = "logistic-chest-passive-provider";
			rows[6][4] = "logistic-chest-storage";
			rows[6][5] = "logistic-chest-buffer";
			rows[6][6] = "logistic-chest-requester";
			rows[6][7] = "roboport";

			rows[7][0] = "small-lamp";
			rows[7][1] = "red-wire";
			rows[7][2] = "green-wire";
			rows[7][3] = "arithmetic-combinator";
			rows[7][4] = "decider-combinator";
			rows[7][5] = "constant-combinator";
			rows[7][6] = "power-switch";
			rows[7][7] = "programmable-speaker";
			
			rows[7][0] = "stone-brick";
			rows[7][1] = "concrete";
			rows[7][2] = "hazard-concrete";
			rows[7][3] = "refined-concrete";
			rows[7][4] = "refined-hazard-concrete";
			rows[7][5] = "landfill";
			rows[7][6] = "cliff-explosives";

		}
	}

}
