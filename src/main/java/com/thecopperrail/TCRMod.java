package com.thecopperrail;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.registry.Registry;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

public class TCRMod implements ModInitializer {
	public static final String MOD_ID = "thecopperrail";

	@Override
	public void onInitialize() {
		Registry.register(Registries.BLOCK, Identifier.of(MOD_ID, "copper_rail"), CopperRailBlock.BLOCK);
		Registry.register(Registries.ITEM , Identifier.of(MOD_ID, "copper_rail"), CopperRailBlock.BLOCK_ITEM);
		ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(content -> {
			content.addAfter(Items.ACTIVATOR_RAIL, CopperRailBlock.BLOCK_ITEM);
		});
		LootTableEvents.MODIFY.register((id, tableBuilder, source, wrapper) -> {
    		if (source.isBuiltin() && LootTables.ABANDONED_MINESHAFT_CHEST.equals(id)) {
				final int[] i = new int[]{0};
				tableBuilder.modifyPools(poolBuilder -> {
					if(i[0]++ == 2){
						poolBuilder.with(
							ItemEntry.builder(CopperRailBlock.BLOCK_ITEM)
							.apply(
								SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 4))
							)
							.weight(5)
						);
					}
				});
    		}
		});
	}
}