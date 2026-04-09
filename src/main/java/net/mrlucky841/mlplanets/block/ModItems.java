package net.mrlucky841.mlplanets.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SnowballItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.MLPlanets;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, MLPlanets.MODID);

    public static final RegistryObject<Item> MARTIAN_DUST = ITEMS.register("mars_dust",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GRAPHITE = ITEMS.register("graphite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NITROGEN_SNOWBALL = ITEMS.register("nitrogen_snowball",
            () -> new SnowballItem(new Item.Properties()));
    public static final RegistryObject<Item> PYRITE = ITEMS.register("pyrite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_PYRITE = ITEMS.register("raw_pyrite",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_THIOLIN = ITEMS.register("raw_thiolin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STELLAR_ICE_CUBES = ITEMS.register("stellar_ice_cubes",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SULFUR = ITEMS.register("sulfur",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
