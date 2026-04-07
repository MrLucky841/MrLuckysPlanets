package net.mrlucky841.mlplanets.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.mrlucky841.mlplanets.MLPlanets;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ItemTagDataGenerator extends ItemTagsProvider {
    public ItemTagDataGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> completableFutureProvider,
                               CompletableFuture<TagLookup<Block>> blockCompletableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(packOutput, completableFutureProvider, blockCompletableFuture, MLPlanets.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}
