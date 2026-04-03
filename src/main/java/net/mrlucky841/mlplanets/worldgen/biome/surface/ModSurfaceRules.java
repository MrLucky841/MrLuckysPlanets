package net.mrlucky841.mlplanets.worldgen.biome.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

import static net.minecraft.world.level.levelgen.SurfaceRules.verticalGradient;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource STONE = makeStateRule(Blocks.COBBLESTONE); //TODO: Change to my own blocks!
    private static final SurfaceRules.RuleSource SAND = makeStateRule(Blocks.SAND);
    private static final SurfaceRules.RuleSource DEEPSLATE = makeStateRule(Blocks.DEEPSLATE_BRICKS);
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);


    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.ConditionSource waterLevelCheck = SurfaceRules.waterBlockCheck(-1,0);
        SurfaceRules.RuleSource regolithSurface = srSeq(sIfTroo(waterLevelCheck, SAND), STONE);

        return srSeq(
                //deepslate below 30
                sIfTroo(verticalGradient("random_bullshit", VerticalAnchor.aboveBottom(30), VerticalAnchor.aboveBottom(35)),DEEPSLATE),
                //bedrock from 0-5
                sIfTroo(verticalGradient("bedtime_rock", VerticalAnchor.absolute(0), VerticalAnchor.aboveBottom(5)),BEDROCK)


        );
    }


    //helper methods to reduce typing fatigue
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
    protected static SurfaceRules.RuleSource srSeq(SurfaceRules.RuleSource... rules) {
        return SurfaceRules.sequence(rules);
    }
    protected static SurfaceRules.RuleSource sIfTroo(SurfaceRules.ConditionSource condition, SurfaceRules.RuleSource rule) {
        return SurfaceRules.ifTrue(condition, rule);
    }
}