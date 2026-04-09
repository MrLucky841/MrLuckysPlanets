package net.mrlucky841.mlplanets.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.MLPlanets;

import java.util.function.Supplier;

import static net.minecraft.world.item.Items.registerBlock;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, MLPlanets.MODID);

    //Ceres, Deimos, Phobos, basic bodies
    public static final RegistryObject<Block> REGOLITH = registerBlock("regolith",
            () -> new SandBlock(0, BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND)));
    public static final RegistryObject<Block> CHONDRITE = registerBlock("chondrite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));

    //Mars
    public static final RegistryObject<Block> MARTIAN_REGOLITH = registerBlock("martian_regolith",
            () -> new SnowLayerBlock(BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND)));
    public static final RegistryObject<Block> MARS_ROCK = registerBlock("mars_rock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    public static final RegistryObject<Block> MARS_BEDROCK = registerBlock("martian_bedrock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OBSIDIAN).sound(SoundType.STONE)));

    //Venus(sulfuric skies,temperate band in skies,supercritical CO2 sea, temperate at 1bar, no magnetosphere, continents)
    public static final RegistryObject<Block> VENUSIAN_OLIVINE = registerBlock("venusian_olivine",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    public static final RegistryObject<Block> VENUSIAN_TOPROCK = registerBlock("venusian_toprock",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    public static final RegistryObject<Block> VENUSIAN_OBSIDIAN = registerBlock("venusian_obsidian",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> SULFURIC_CLOUD = registerBlock("sulfuric_cloud",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBWEB).sound(SoundType.WOOL)
                    .noCollission().noOcclusion().noLootTable().noParticlesOnBreak()));
    //public static final RegistryObject<Block> SUPERCRITICAL_CO2 = registerBlock("supercritical_co2",
    //        () -> new Fluid(); ??
    //Mercury(metallic,violent temp swings)
    public static final RegistryObject<Block> MERCURIAN_PYROXENE = registerBlock("mercurian_pyroxene",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    public static final RegistryObject<Block> GRAPHITE_ORE = registerBlock("graphite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    //Jupiter(big red spot,rapid winds,H2/He)
    //public static final RegistryObject<Block> STELLAR_GAS = registerBlock("stellar_gas",
    //        () -> new AirBlock(BlockBehaviour.Properties.copy(Blocks.AIR).sound(SoundType.WOOL)));
    //^Io(sulfur/magma volcanoes, mountainous, pyrite ores, sulfur snow)
    public static final RegistryObject<Block> SULFUROUS_HOARFROST = registerBlock("sulfurous_hoarfrost",
            () -> new MultifaceBlock(BlockBehaviour.Properties.copy(Blocks.SNOW).sound(SoundType.SNOW)) {
                @Override
                public MultifaceSpreader getSpreader() {
                    return null;
                }
            });
    public static final RegistryObject<Block> PYRITE_ORE = registerBlock("pyrite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.STONE)));
    public static final RegistryObject<Block> IONIAN_FOYADA = registerBlock("ionian_foyada",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.CALCITE))); //make this change color! subtle red, yellow, red, white, black
    //public static final RegistryObject<Block> SULFUROUS_MAGMA = registerBlock("sulfurous_magma",
    //        () -> new LavaFluid(); ??
    //^Europa(smooth surface,subsurface ocean,thermal vents,icy,few cryovolcanoes)
    public static final RegistryObject<Block> STELLAR_ICE = registerBlock("stellar_ice",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.ICE).sound(SoundType.GLASS)));
    public static final RegistryObject<Block> MINERAL_VENT = registerBlock("mineral_vent_side", //TODO: make this just "mineral_vent" with the right sides!!!
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).sound(SoundType.TUFF)));
    //public static final RegistryObject<Block> LUNAR_BRINE = registerBlock("lunar_brine",
    //        () -> new LavaFluid(); ??
    //^Ganymede(light surface w/ carvers & dark surface w/ craters, subsurface ocean, thin O2 atmosphere)
    public static final RegistryObject<Block> DARK_REGOLITH = registerBlock("dark_regolith",
            () -> new SandBlock(0, BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND)));
    //^Callisto(dead,heavily cratered,ionosphere,weak CO2/O2 atmosphere)
    public static final RegistryObject<Block> METEORITE_ORE = registerBlock("meteorite_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));
    //Saturn(H2/He, undense, yellow, liquid H2 surface)
    //public static final RegistryObject<Block> LIQUID_METAL_HYDROGEN = registerBlock("liquid_metal_hydrogen",
    //        () -> new LavaFluid(); ??
    //^Titan(N2+hydrocarbons atmosphere,large,yellow)
    //public static final RegistryObject<Block> LIQUID_METHANE = registerBlock("liquid_methane",
    //        () -> new Fluid(); ??
    public static final RegistryObject<Block> TITAN_TOPSOIL = registerBlock("titan_topsoil",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT).sound(SoundType.GRAVEL)));
    //^Enceladus(Ice,snow,cryovolcanoes,6mi deep ocean,porous rocky core)(young smooth areas + old heavily cratered regions)

    //^Lapetus(black/white colors,lapis??,high mountains)
    public static final RegistryObject<Block> LIGHT_REGOLITH = registerBlock("light_regolith",
            () -> new SandBlock(0, BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND)));
    //Uranus(H2/He/CH4, very cold)
    //^Titania(ionosphere,craters,dark rock)
    //Neptune(Great Dark Spot, small giant, H2/He, ionic water surface, NH3/H2S clouds, storms?)
    public static final RegistryObject<Block> NEPTUNIAN_CLOUD = registerBlock("neptunian_cloud",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.COBWEB).sound(SoundType.WOOL)
                .noCollission().noOcclusion().noLootTable().noParticlesOnBreak()));
    //public static final RegistryObject<Block> CORE_IONIC_WATER = registerBlock("core_ionic_water",
    //        () -> new LavaFluid(); ??
    //^Triton(thin N2 atmosphere, clouds, N2 snow, cryovolcanoes, flat+ridges, thiolin deposits)
    public static final RegistryObject<Block> NITROGEN_SNOW = registerBlock("nitrogen_snow",
            () -> new SnowLayerBlock(BlockBehaviour.Properties.copy(Blocks.SNOW).sound(SoundType.SNOW)));
    public static final RegistryObject<Block> THIOLIN_ORE = registerBlock("thiolin_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SNOW_BLOCK).sound(SoundType.SOUL_SOIL)));
    //Pluto(N2 ice plains, subsurface ocean, wildly varying color)
    public static final RegistryObject<Block> NITROGEN_ICE = registerBlock("nitrogen_ice",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.PACKED_ICE).sound(SoundType.GLASS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
