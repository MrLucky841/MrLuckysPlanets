package net.mrlucky841.mlplanets.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.datagen.*;
import net.mrlucky841.mlplanets.datagen.WorldGenDataProvider;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = MLPlanets.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeClient(), new BlockStateDataGenerator(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ItemModelDataProvider(packOutput, fileHelper));
        generator.addProvider(event.includeServer(), new RecipeDataProvider(packOutput));
        generator.addProvider(event.includeServer(), LootTableDataProvider.create(packOutput));
        generator.addProvider(event.includeServer(), new WorldGenDataProvider(packOutput, lookupProvider));
        BlockTagDataGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new BlockTagDataGenerator(packOutput, lookupProvider, fileHelper));
        generator.addProvider(event.includeServer(), new ItemTagDataGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), fileHelper));
    }
}
