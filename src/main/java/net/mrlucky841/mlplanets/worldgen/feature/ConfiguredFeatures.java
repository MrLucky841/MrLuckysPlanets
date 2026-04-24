package net.mrlucky841.mlplanets.worldgen.feature;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.mrlucky841.mlplanets.MLPlanets;

import java.util.List;

public class ConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?,?>> TEST_ORE_KEY = registerKey("test_ore");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?,?>> context) {
        RuleTest stoners = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        List<OreConfiguration.TargetBlockState> testOres = List.of(OreConfiguration.target(stoners,
                Blocks.DIAMOND_BLOCK.defaultBlockState()));
                //,OreConfiguration.target());  //<<Put other replacement maps here, ie deepslate to the deepslate ore variant

        register(context, TEST_ORE_KEY, Feature.ORE, new OreConfiguration(testOres, 15));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MLPlanets.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
