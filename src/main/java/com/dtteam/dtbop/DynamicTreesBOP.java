package com.dtteam.dtbop;

import com.dtteam.dynamictrees.api.DynamicTreesAddonEntrypoint;
import net.minecraft.resources.Identifier;

/** Fabric entrypoint for the Biomes O' Plenty tree pack. */
public final class DynamicTreesBOP implements DynamicTreesAddonEntrypoint {
    public static final String MOD_ID = "dtbop";

    @Override
    public void onDynamicTreesPreSetup() {
        DynamicTreesAddonEntrypoint.setupAddon(MOD_ID);
        DTBOPRegistries.setup();
    }

    public static Identifier location(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}
