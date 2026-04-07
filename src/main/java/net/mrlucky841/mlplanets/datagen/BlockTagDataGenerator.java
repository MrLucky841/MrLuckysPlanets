package net.mrlucky841.mlplanets.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class BlockTagDataGenerator extends BlockTagsProvider {
    public BlockTagDataGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MLPlanets.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_SHOVEL)
                .add(ModBlocks.REGOLITH.get())
                .add(ModBlocks.MARTIAN_REGOLITH.get())
                .add(ModBlocks.VENUSIAN_OLIVINE.get())
                .add(ModBlocks.VENUSIAN_TOPROCK.get())
                .add(ModBlocks.SULFUROUS_HOARFROST.get())
                .add(ModBlocks.TITAN_TOPSOIL.get())
                .add(ModBlocks.LIGHT_REGOLITH.get())
                .add(ModBlocks.NITROGEN_SNOW.get())
                .add(ModBlocks.THIOLIN_ORE.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.CHONDRITE.get())
                .add(ModBlocks.MARS_ROCK.get())
                .add(ModBlocks.MARS_BEDROCK.get())
                .add(ModBlocks.VENUSIAN_OBSIDIAN.get())
                .add(ModBlocks.MERCURIAN_PYROXENE.get())
                .add(ModBlocks.GRAPHITE_ORE.get())
                .add(ModBlocks.PYRITE_ORE.get())
                .add(ModBlocks.IONIAN_FOYADA.get())
                .add(ModBlocks.STELLAR_ICE.get())
                .add(ModBlocks.MINERAL_VENT.get())
                .add(ModBlocks.NITROGEN_ICE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.PYRITE_ORE.get())
                .add(ModBlocks.GRAPHITE_ORE.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MARS_BEDROCK.get())
                .add(ModBlocks.VENUSIAN_OBSIDIAN.get())
                .add(ModBlocks.METEORITE_ORE.get());
    }
}
