package net.mrlucky841.mlplanets.worldgen.biome;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource DIRT = ModSurfaceRules.makeStateRule(Blocks.DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = ModSurfaceRules.makeStateRule(Blocks.GRASS_BLOCK);
    private static final SurfaceRules.RuleSource WART = ModSurfaceRules.makeStateRule(Blocks.NETHER_WART);
    private static final SurfaceRules.RuleSource BRICKS = ModSurfaceRules.makeStateRule(Blocks.BRICKS);

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource isAtOrAboveWaterLevel = SurfaceRules.waterBlockCheck(-1, 0);
        SurfaceRules.RuleSource grassSurface = SurfaceRules.sequence(SurfaceRules.ifTrue(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT);

        return SurfaceRules.sequence(
                SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.isBiome(SpaceBiomes.HARANDRA),
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, BRICKS)),
                        SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, WART)),
            //This defaults to a grass/dirt surface
            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, grassSurface)
        );
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
