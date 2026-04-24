package net.mrlucky841.mlplanets.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.block.ModItems;

public class ItemModelDataProvider extends ItemModelProvider {
    public ItemModelDataProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MLPlanets.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.GRAPHITE);
        simpleItem(ModItems.MARTIAN_DUST);
        simpleItem(ModItems.NITROGEN_SNOWBALL);
        simpleItem(ModItems.PYRITE);
        simpleItem(ModItems.RAW_PYRITE);
        simpleItem(ModItems.RAW_THIOLIN);
        simpleItem(ModItems.STELLAR_ICE_CUBES);
        simpleItem(ModItems.SULFUR);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(MLPlanets.MODID,"item/" + item.getId().getPath()));
    }
}
