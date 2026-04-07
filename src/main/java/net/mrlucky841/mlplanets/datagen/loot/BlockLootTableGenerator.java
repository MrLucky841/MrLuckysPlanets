package net.mrlucky841.mlplanets.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.mrlucky841.mlplanets.block.ModBlocks;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class BlockLootTableGenerator extends BlockLootSubProvider {
    public BlockLootTableGenerator() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.REGOLITH.get());
        this.dropSelf(ModBlocks.CHONDRITE.get());
        this.dropSelf(ModBlocks.MARTIAN_REGOLITH.get());
        this.dropSelf(ModBlocks.MARS_ROCK.get());
        this.dropSelf(ModBlocks.MARS_BEDROCK.get());

        this.dropSelf(ModBlocks.VENUSIAN_OLIVINE.get());
        this.dropSelf(ModBlocks.VENUSIAN_TOPROCK.get());
        this.dropSelf(ModBlocks.VENUSIAN_OBSIDIAN.get());
        this.dropSelf(ModBlocks.SULFURIC_CLOUD.get());          //TODO: make this into a proper gas <<<

        this.dropSelf(ModBlocks.MERCURIAN_PYROXENE.get());
        this.dropSelf(ModBlocks.GRAPHITE_ORE.get());            //TODO: make these unique (add graphite)
        this.dropSelf(ModBlocks.SULFUROUS_HOARFROST.get());     //Add sulfur
        this.dropSelf(ModBlocks.PYRITE_ORE.get());              //Add pyrite ore items
        this.dropSelf(ModBlocks.IONIAN_FOYADA.get());

        this.dropSelf(ModBlocks.STELLAR_ICE.get());             //add ice cubes
        this.dropSelf(ModBlocks.MINERAL_VENT.get());            //drop cobblestone?
        this.dropSelf(ModBlocks.DARK_REGOLITH.get());
        this.dropSelf(ModBlocks.METEORITE_ORE.get());           //drop meteorite ore items?

        this.dropSelf(ModBlocks.TITAN_TOPSOIL.get());
        this.dropSelf(ModBlocks.LIGHT_REGOLITH.get());
        this.dropSelf(ModBlocks.NEPTUNIAN_CLOUD.get());         //turn this into a proper gas as well <<<
        this.dropSelf(ModBlocks.NITROGEN_SNOW.get());           //nitrogen snowball
        this.dropSelf(ModBlocks.NITROGEN_ICE.get());
        this.dropSelf(ModBlocks.THIOLIN_ORE.get());             //liquid or solid drops?

        //this.add(ModBlocks.SAPPHIRE_ORE.get(),
        //        block -> createCopperLikeOreDrops(ModBlocks.SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        //this.add(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(),
        //        block -> createCopperLikeOreDrops(ModBlocks.DEEPSLATE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        //this.add(ModBlocks.NETHER_SAPPHIRE_ORE.get(),
        //        block -> createCopperLikeOreDrops(ModBlocks.NETHER_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));
        //this.add(ModBlocks.END_STONE_SAPPHIRE_ORE.get(),
        //        block -> createCopperLikeOreDrops(ModBlocks.END_STONE_SAPPHIRE_ORE.get(), ModItems.RAW_SAPPHIRE.get()));

    }

    //protected LootTable.Builder createCopperLikeOreDrops(Block pBlock, Item item) {
    //    return createSilkTouchDispatchTable(pBlock,
    //            this.applyExplosionDecay(pBlock,
    //                    LootItem.lootTableItem(item)
    //                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F)))
    //                            .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    //}

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
