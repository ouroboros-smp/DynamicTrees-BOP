package com.dtteam.dtbop;

import com.dtteam.dtbop.block.CobwebLeavesProperties;
import com.dtteam.dtbop.cell.DTBOPCellKits;
import com.dtteam.dtbop.genfeature.DTBOPGenFeatures;
import com.dtteam.dtbop.growthlogic.DTBOPGrowthLogicKits;
import com.dtteam.dtbop.tree.Bush;
import com.dtteam.dtbop.tree.CypressSpecies;
import com.dtteam.dtbop.tree.GenOnStoneSpecies;
import com.dtteam.dtbop.tree.PoplarSpecies;
import com.dtteam.dtbop.tree.TwigletSpecies;
import com.dtteam.dynamictrees.api.cell.CellKit;
import com.dtteam.dynamictrees.block.CommonVoxelShapes;
import com.dtteam.dynamictrees.block.leaves.LeavesProperties;
import com.dtteam.dynamictrees.systems.genfeature.GenFeature;
import com.dtteam.dynamictrees.systems.growthlogic.GrowthLogicKit;
import com.dtteam.dynamictrees.tree.species.Species;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Registers BOP-specific Dynamic Trees types before the tree pack is parsed. */
public final class DTBOPRegistries {

    public static final VoxelShape GLOWSHROOM_AGE0 = Shapes.create(0, 0, 0, 1, 0.75, 1);
    public static final VoxelShape TOADSTOOL_AGE0 = Shapes.create(2 / 16f, 0, 2 / 16f, 14 / 16f, 1, 14 / 16f);
    public static final VoxelShape MUSHROOM_CAP_SHORT_ROUND = Block.box(5D, 3D, 5D, 11D, 7D, 11D);
    public static final VoxelShape ROUND_SHORT_MUSHROOM = Shapes.or(CommonVoxelShapes.SAPLING_TRUNK, MUSHROOM_CAP_SHORT_ROUND);
    public static final VoxelShape TOADSTOOL_CAP = Block.box(5.5D, 3D, 5.5D, 10.5D, 10.0D, 10.5D);
    public static final VoxelShape TOADSTOOL = Shapes.or(CommonVoxelShapes.SAPLING_TRUNK, TOADSTOOL_CAP);

    private DTBOPRegistries() {
    }

    public static void setup() {
        CommonVoxelShapes.SHAPES.put(DynamicTreesBOP.location("glowshroom_age0").toString(), GLOWSHROOM_AGE0);
        CommonVoxelShapes.SHAPES.put(DynamicTreesBOP.location("toadstool_age0").toString(), TOADSTOOL_AGE0);
        CommonVoxelShapes.SHAPES.put(DynamicTreesBOP.location("round_short_mushroom").toString(), ROUND_SHORT_MUSHROOM);
        CommonVoxelShapes.SHAPES.put(DynamicTreesBOP.location("toadstool").toString(), TOADSTOOL);

        DTBOPGenFeatures.register(GenFeature.REGISTRY);
        DTBOPGrowthLogicKits.register(GrowthLogicKit.REGISTRY);
        DTBOPCellKits.register(CellKit.REGISTRY);
        LeavesProperties.REGISTRY.registerType(DynamicTreesBOP.location("cobweb"), CobwebLeavesProperties.TYPE);
        Species.REGISTRY.registerType(DynamicTreesBOP.location("twiglet"), TwigletSpecies.TYPE);
        Species.REGISTRY.registerType(DynamicTreesBOP.location("poplar"), PoplarSpecies.TYPE);
        Species.REGISTRY.registerType(DynamicTreesBOP.location("cypress"), CypressSpecies.TYPE);
        Species.REGISTRY.registerType(DynamicTreesBOP.location("generates_on_stone"), GenOnStoneSpecies.TYPE);
        Species.REGISTRY.runOnNextLock(DTBOPRegistries::finishTreePackSetup);
    }

    private static void finishTreePackSetup() {
        Bush.INSTANCES.forEach(Bush::setup);

        Species floweringOak = Species.REGISTRY.get(DynamicTreesBOP.location("flowering_oak"));
        Species floweringAppleOak = Species.REGISTRY.get(DynamicTreesBOP.location("flowering_apple_oak"));
        Species infested = Species.REGISTRY.get(DynamicTreesBOP.location("infested"));
        LeavesProperties floweringLeaves = LeavesProperties.REGISTRY.get(DynamicTreesBOP.location("flowering_oak"));

        if (floweringOak.isValid() && floweringLeaves.isValid()) {
            floweringLeaves.setFamily(floweringOak.getFamily());
            floweringOak.addValidLeafBlocks(floweringLeaves);
        }
        if (floweringAppleOak.isValid() && floweringLeaves.isValid()) {
            floweringAppleOak.addValidLeafBlocks(floweringLeaves);
        }
        if (infested.isValid()) {
            infested.addValidLeafBlocks(LeavesProperties.REGISTRY.get(DynamicTreesBOP.location("silk")));
        }
    }
}
