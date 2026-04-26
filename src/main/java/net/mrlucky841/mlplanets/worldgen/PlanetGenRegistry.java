package net.mrlucky841.mlplanets.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import net.mrlucky841.mlplanets.MLPlanets;
import net.mrlucky841.mlplanets.dimension.CeresDensityFunction;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class PlanetGenRegistry {
    @SubscribeEvent
    public static void onRegisterEvent(RegisterEvent event) {
        event.register(Registries.DENSITY_FUNCTION_TYPE, helper -> {
            helper.register(
                    ResourceLocation.fromNamespaceAndPath(MLPlanets.MODID, "ceres_density_function"),
                    CeresDensityFunction.CODEC.codec()
            );
        });
    }
}