package net.mrlucky841.mlplanets.worldgen.surface;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.mrlucky841.mlplanets.block.ModBlocks;

import static net.minecraft.world.level.levelgen.SurfaceRules.verticalGradient;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource BEDROCK = makeStateRule(Blocks.BEDROCK);
    public static final SurfaceRules.RuleSource REGOLITH = makeStateRule(ModBlocks.REGOLITH.get());
    public static final SurfaceRules.RuleSource CHONDRITE = makeStateRule(ModBlocks.CHONDRITE.get());

    public static final SurfaceRules.RuleSource MARTIAN_REGOLITH = makeStateRule(ModBlocks.MARTIAN_REGOLITH.get());
    public static final SurfaceRules.RuleSource MARS_ROCK = makeStateRule(ModBlocks.MARS_ROCK.get());
    public static final SurfaceRules.RuleSource MARS_BEDROCK = makeStateRule(ModBlocks.MARS_BEDROCK.get());

    public static final SurfaceRules.RuleSource VENUSIAN_OLIVINE = makeStateRule(ModBlocks.VENUSIAN_OLIVINE.get());
    public static final SurfaceRules.RuleSource VENUSIAN_TOPROCK = makeStateRule(ModBlocks.VENUSIAN_TOPROCK.get());
    public static final SurfaceRules.RuleSource VENUSIAN_OBSIDIAN = makeStateRule(ModBlocks.VENUSIAN_OBSIDIAN.get());
    public static final SurfaceRules.RuleSource VENUSIAN_CLOUD = makeStateRule(ModBlocks.SULFURIC_CLOUD.get());
    //public static final SurfaceRules.RuleSource SUPERCRIT_CO2 = makeStateRule(ModBlocks.SUPERCRIT_CO2.get());

    public static final SurfaceRules.RuleSource MERCURIAN_PYROXENE = makeStateRule(ModBlocks.MERCURIAN_PYROXENE.get());
    public static final SurfaceRules.RuleSource GRAPHITE_ORE = makeStateRule(ModBlocks.GRAPHITE_ORE.get());
    //public static final SurfaceRules.RuleSource STELLAR_GAS = makeStateRule(ModBlocks.STELLAR_GAS.get());

    public static final SurfaceRules.RuleSource SULFUROUS_HOARFROST = makeStateRule(ModBlocks.SULFUROUS_HOARFROST.get());
    public static final SurfaceRules.RuleSource PYRITE_ORE = makeStateRule(ModBlocks.PYRITE_ORE.get());
    public static final SurfaceRules.RuleSource IONIAN_FOYADA = makeStateRule(ModBlocks.IONIAN_FOYADA.get());
    //public static final SurfaceRules.RuleSource SULFUROUS_MAGMA = makeStateRule(ModBlocks.SULFUROUS_MAGMA.get());

    public static final SurfaceRules.RuleSource STELLAR_ICE = makeStateRule(ModBlocks.STELLAR_ICE.get());
    public static final SurfaceRules.RuleSource MINERAL_VENT = makeStateRule(ModBlocks.MINERAL_VENT.get());
    //public static final SurfaceRules.RuleSource LUNAR_BRINE = makeStateRule(ModBlocks.LUNAR_BRINE.get());

    public static final SurfaceRules.RuleSource DARK_REGOLITH = makeStateRule(ModBlocks.DARK_REGOLITH.get());
    public static final SurfaceRules.RuleSource METEORITE_ORE = makeStateRule(ModBlocks.METEORITE_ORE.get());
    //public static final SurfaceRules.RuleSource LIQUID_METAL_HYDROGEN = makeStateRule(ModBlocks.LIQUID_METAL_HYDROGEN.get());

    //public static final SurfaceRules.RuleSource LIQUID_METHANE = makeStateRule(ModBlocks.LIQUID_METHANE.get());
    public static final SurfaceRules.RuleSource TITAN_TOPSOIL = makeStateRule(ModBlocks.TITAN_TOPSOIL.get());
    public static final SurfaceRules.RuleSource TITAN_THIOLIN_ORE = makeStateRule(ModBlocks.TITAN_THIOLIN_ORE.get());

    public static final SurfaceRules.RuleSource LIGHT_REGOLITH = makeStateRule(ModBlocks.LIGHT_REGOLITH.get());

    public static final SurfaceRules.RuleSource NEPTUNIAN_CLOUD = makeStateRule(ModBlocks.NEPTUNIAN_CLOUD.get());
    //public static final SurfaceRules.RuleSource CORE_IONIC_WATER = makeStateRule(ModBlocks.CORE_IONIC_WATER.get());

    public static final SurfaceRules.RuleSource NITROGEN_ICE = makeStateRule(ModBlocks.NITROGEN_ICE.get());
    public static final SurfaceRules.RuleSource NITROGEN_SNOW = makeStateRule(ModBlocks.NITROGEN_SNOW.get());
    public static final SurfaceRules.RuleSource OUTER_THIOLIN_ORE = makeStateRule(ModBlocks.OUTER_THIOLIN_ORE.get());



    public static SurfaceRules.RuleSource makeRockRules(SurfaceRules.RuleSource surfaceBlock, SurfaceRules.RuleSource mainRock, SurfaceRules.RuleSource deepRock) {
        SurfaceRules.ConditionSource waterLevelCheck = SurfaceRules.waterBlockCheck(-1,0);
        SurfaceRules.RuleSource regolithSurface = srSeq(sIfTroo(waterLevelCheck, surfaceBlock), mainRock);

        //See also:
        //https://github.com/KyaniteMods/DeeperAndDarker/blob/ba7bcc0712d1e44993e1dc99c02d52995f0d2c89/src/main/java/com/kyanite/deeperdarker/world/otherside/gen/OthersideGeneration.java#L26
        return srSeq(
                //deepslate below 30
                sIfTroo(verticalGradient("random_bullshit", VerticalAnchor.aboveBottom(30), VerticalAnchor.aboveBottom(35)), deepRock),
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