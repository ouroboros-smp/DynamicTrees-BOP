package com.dtteam.dtbop;

import com.dtteam.dynamictrees.api.DynamicTreesAddonEntrypoint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;

/** Fabric entrypoint for the Biomes O' Plenty tree pack. */
public final class DynamicTreesBOP implements DynamicTreesAddonEntrypoint {
    public static final String MOD_ID = "dtbop";

    @Override
    public void onDynamicTreesPreSetup() {
        // BOP normally initializes later than Dynamic Trees in large Fabric mod
        // sets. Ask the Ouroboros BOP fork to publish its built-ins before this
        // add-on's tree pack resolves primitive blocks and items.
        FabricLoader.getInstance().getEntrypoints("dtbop-bootstrap", ModInitializer.class)
                .forEach(ModInitializer::onInitialize);
        DynamicTreesAddonEntrypoint.setupAddon(MOD_ID);
        DTBOPRegistries.setup();
    }

    public static Identifier location(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
