package net.mrlucky841.mlplanets.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.mrlucky841.mlplanets.block.ModBlocks;
import net.minecraftforge.registries.RegistryObject;
import net.mrlucky841.mlplanets.block.ModItems;

import java.util.Set;

public class BlockLootTableGenerator extends BlockLootSubProvider {
    public BlockLootTableGenerator() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.REGOLITH.get());
        this.dropSelf(ModBlocks.CHONDRITE.get());
        this.dropOther(ModBlocks.MARTIAN_REGOLITH.get(), ModItems.MARTIAN_DUST.get());  //TODO: drop multiple dusts?
        this.dropSelf(ModBlocks.MARS_ROCK.get());
        this.dropSelf(ModBlocks.MARS_BEDROCK.get());

        this.dropSelf(ModBlocks.VENUSIAN_OLIVINE.get());
        this.dropSelf(ModBlocks.VENUSIAN_TOPROCK.get());
        this.dropSelf(ModBlocks.VENUSIAN_OBSIDIAN.get());

        this.dropSelf(ModBlocks.MERCURIAN_PYROXENE.get());
        this.createOreDrop(ModBlocks.GRAPHITE_ORE.get(), ModItems.GRAPHITE.get());
        this.dropOther(ModBlocks.SULFUROUS_HOARFROST.get(), ModItems.SULFUR.get());
        this.createOreDrop(ModBlocks.PYRITE_ORE.get(), ModItems.RAW_PYRITE.get());
        this.dropSelf(ModBlocks.IONIAN_FOYADA.get());

        this.createOreDrop(ModBlocks.STELLAR_ICE.get(), ModItems.STELLAR_ICE_CUBES.get());
        this.dropSelf(ModBlocks.MINERAL_VENT.get());
        this.dropSelf(ModBlocks.DARK_REGOLITH.get());
        this.dropSelf(ModBlocks.METEORITE_ORE.get());

        this.dropSelf(ModBlocks.TITAN_TOPSOIL.get());
        this.dropSelf(ModBlocks.LIGHT_REGOLITH.get());
        this.dropOther(ModBlocks.NITROGEN_SNOW.get(), ModItems.NITROGEN_SNOWBALL.get());    //TODO: make drop 4 snowballs
        this.dropSelf(ModBlocks.NITROGEN_ICE.get());
        this.createOreDrop(ModBlocks.THIOLIN_ORE.get(), ModItems.RAW_THIOLIN.get());
    }

    //protected LootTable.Builder createPlentifulOreDrops(Block pBlock, Item item) {
    //    return createSilkTouchDispatchTable(pBlock,
    //            this.applyExplosionDecay(pBlock,
    //                    LootItem.lootTableItem(item)
    //                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 5.0F)))
    //                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    //}

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
