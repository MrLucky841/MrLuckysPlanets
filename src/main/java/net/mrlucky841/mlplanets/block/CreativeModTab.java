package net.mrlucky841.mlplanets.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.MLPlanets;

public class CreativeModTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MLPlanets.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_TAB = CREATIVE_MODE_TABS.register("main_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MARTIAN_DUST.get()))
                    .title(Component.translatable("MrLucky's Planets"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.MARTIAN_DUST.get());

                        pOutput.accept(ModBlocks.REGOLITH.get());
                        pOutput.accept(ModBlocks.CHONDRITE.get());
                        pOutput.accept(ModBlocks.MARTIAN_REGOLITH.get());
                        pOutput.accept(ModBlocks.MARS_ROCK.get());
                        pOutput.accept(ModBlocks.MARS_BEDROCK.get());

                        pOutput.accept(ModBlocks.VENUSIAN_OLIVINE.get());
                        pOutput.accept(ModBlocks.VENUSIAN_TOPROCK.get());
                        pOutput.accept(ModBlocks.VENUSIAN_OBSIDIAN.get());
                        pOutput.accept(ModBlocks.SULFURIC_CLOUD.get());

                        pOutput.accept(ModBlocks.MERCURIAN_PYROXENE.get());
                        pOutput.accept(ModBlocks.GRAPHITE_ORE.get());
                        pOutput.accept(ModBlocks.SULFUROUS_HOARFROST.get());
                        pOutput.accept(ModBlocks.PYRITE_ORE.get());
                        pOutput.accept(ModBlocks.IONIAN_FOYADA.get());

                        pOutput.accept(ModBlocks.STELLAR_ICE.get());
                        pOutput.accept(ModBlocks.MINERAL_VENT.get());
                        pOutput.accept(ModBlocks.DARK_REGOLITH.get());
                        pOutput.accept(ModBlocks.METEORITE_ORE.get());

                        pOutput.accept(ModBlocks.TITAN_TOPSOIL.get());
                        pOutput.accept(ModBlocks.LIGHT_REGOLITH.get());
                        pOutput.accept(ModBlocks.NEPTUNIAN_CLOUD.get());
                        pOutput.accept(ModBlocks.NITROGEN_SNOW.get());
                        pOutput.accept(ModBlocks.NITROGEN_ICE.get());
                        pOutput.accept(ModBlocks.THIOLIN_ORE.get());


                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
