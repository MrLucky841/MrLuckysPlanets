package net.mrlucky841.mlplanets.datagen;


import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.block.ModBlocks;

public class BlockStateDataGenerator extends BlockStateProvider {
    public BlockStateDataGenerator(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MLPlanets.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.REGOLITH);
        blockWithItem(ModBlocks.CHONDRITE);
        blockWithItem(ModBlocks.MARTIAN_REGOLITH);
        blockWithItem(ModBlocks.MARS_ROCK);
        blockWithItem(ModBlocks.MARS_BEDROCK);

        blockWithItem(ModBlocks.VENUSIAN_OLIVINE);
        blockWithItem(ModBlocks.VENUSIAN_TOPROCK);
        blockWithItem(ModBlocks.VENUSIAN_OBSIDIAN);
        blockWithItem(ModBlocks.SULFURIC_CLOUD);

        blockWithItem(ModBlocks.MERCURIAN_PYROXENE);
        blockWithItem(ModBlocks.GRAPHITE_ORE);
        blockWithItem(ModBlocks.SULFUROUS_HOARFROST);
        blockWithItem(ModBlocks.PYRITE_ORE);
        blockWithItem(ModBlocks.IONIAN_FOYADA);

        blockWithItem(ModBlocks.STELLAR_ICE);
        blockWithItem(ModBlocks.MINERAL_VENT);
        blockWithItem(ModBlocks.DARK_REGOLITH);
        blockWithItem(ModBlocks.METEORITE_ORE);

        blockWithItem(ModBlocks.TITAN_TOPSOIL);
        blockWithItem(ModBlocks.LIGHT_REGOLITH);
        blockWithItem(ModBlocks.NEPTUNIAN_CLOUD);
        blockWithItem(ModBlocks.NITROGEN_SNOW);
        blockWithItem(ModBlocks.NITROGEN_ICE);
        blockWithItem(ModBlocks.THIOLIN_ORE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    //private void layerBlockWithItem(RegistryObject<Block> blockRegistryObject) {
    //    simpleBlockItem(blockRegistryObject.get(), );
    //}
}
