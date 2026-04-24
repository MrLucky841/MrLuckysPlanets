package net.mrlucky841.mlplanets.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
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

        this.dropSelf(ModBlocks.VENUSIAN_OLIVINE.get());        //TODO: Add olivine ores???
        this.dropSelf(ModBlocks.VENUSIAN_TOPROCK.get());
        this.dropSelf(ModBlocks.VENUSIAN_OBSIDIAN.get());

        this.dropSelf(ModBlocks.MERCURIAN_PYROXENE.get());
        this.dropOre(ModBlocks.GRAPHITE_ORE.get(), ModItems.GRAPHITE.get(),3,5);
        this.dropOther(ModBlocks.SULFUROUS_HOARFROST.get(), ModItems.SULFUR.get());
        this.dropOre(ModBlocks.PYRITE_ORE.get(), ModItems.RAW_PYRITE.get(),1,3);
        this.dropSelf(ModBlocks.IONIAN_FOYADA.get());

        this.dropOreMix(ModBlocks.STELLAR_ICE.get(), ModItems.STELLAR_ICE_CUBES.get(),1,3, Items.GRAVEL, 0.7F);
        this.dropSelf(ModBlocks.MINERAL_VENT.get());
        this.dropSelf(ModBlocks.DARK_REGOLITH.get());
        this.dropSelf(ModBlocks.METEORITE_ORE.get());

        this.dropSelf(ModBlocks.TITAN_TOPSOIL.get());
        this.dropOreMix(ModBlocks.TITAN_THIOLIN_ORE.get(), ModItems.RAW_THIOLIN.get(),1,3, ModBlocks.TITAN_TOPSOIL.get(), 0.3F);
        this.dropSelf(ModBlocks.LIGHT_REGOLITH.get());
        this.dropOre(ModBlocks.NITROGEN_SNOW.get(), ModItems.NITROGEN_SNOWBALL.get(),4,4);
        this.dropSelf(ModBlocks.NITROGEN_ICE.get());
        this.dropOre(ModBlocks.OUTER_THIOLIN_ORE.get(), ModItems.RAW_THIOLIN.get(),3,5);
    }

    protected void dropOre(Block block, ItemLike item, float min, float max) {
        this.add(block,
                createSilkTouchDispatchTable(block,
                this.applyExplosionDecay(block,
                        LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))))
        );
    }

    protected void dropOreMix(Block parentBlock, ItemLike mainItem, float min, float max, ItemLike dirtItem, float chance) {
        this.add(parentBlock,
                LootTable.lootTable().withPool(
                        this.applyExplosionCondition(parentBlock,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(mainItem)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
                                                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))
                        .withPool(this.applyExplosionCondition(parentBlock,
                                LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                                        .add(LootItem.lootTableItem(dirtItem)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0F, 1F))))))
        );
    }

    //protected void dropDusts(Block block, ItemLike item) {
    //    block.getStateDefinition().???
    //    this.add(block,
    //            createSilkTouchDispatchTable(block,
    //                    this.applyExplosionDecay(block,
    //                            LootItem.lootTableItem(item)
    //                                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4F,4F)))
    //                                    ))
    //    );
    //}

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
