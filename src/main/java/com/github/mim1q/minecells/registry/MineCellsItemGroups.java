package com.github.mim1q.minecells.registry;

import com.github.mim1q.minecells.MineCells;
import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import io.wispforest.owo.itemgroup.gui.ItemGroupTab;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.List;
import java.util.stream.Stream;

public class MineCellsItemGroups {
  private static final Identifier DISCORD_ICON = MineCells.createId("textures/gui/button/discord.png");
  private static final Identifier KOFI_ICON = MineCells.createId("textures/gui/button/kofi.png");
  private static final Identifier BACKGROUND = MineCells.createId("textures/gui/group.png");
  private static final Identifier TABS = MineCells.createId("textures/gui/tabs.png");

  private static ItemStack stack(ItemConvertible item) {
    return new ItemStack(item);
  }

  private static List<ItemStack> stackList(ItemConvertible ...item) {
    return Stream.of(item).map(MineCellsItemGroups::stack).toList();
  }

  private static void generalStacks(ItemGroup.DisplayContext ctx, ItemGroup.Entries stacks) {
    stacks.addAll(MineCellsItems.DOORWAY_COLORS.keySet().stream().map(MineCellsItemGroups::stack).toList());
    stacks.addAll(MineCellsItems.DIMENSIONAL_RUNES.stream().map(MineCellsItemGroups::stack).toList());

    stacks.addAll(MineCellsBlocks.PRISON_STONE.getStacks());
    stacks.addAll(MineCellsBlocks.PRISON_COBBLESTONE.getStacks());
    stacks.addAll(MineCellsBlocks.PRISON_BRICKS.getStacks());
    stacks.addAll(MineCellsBlocks.CRACKED_PRISON_BRICKS.getStacks());
    stacks.addAll(MineCellsBlocks.SMALL_PRISON_BRICKS.getStacks());
    stacks.add(stack(MineCellsBlocks.WILTED_GRASS_BLOCK));

    stacks.addAll(MineCellsBlocks.BLOOMROCK.getStacks());
    stacks.addAll(MineCellsBlocks.BLOOMROCK_BRICKS.getStacks());
    stacks.addAll(MineCellsBlocks.CRACKED_BLOOMROCK_BRICKS.getStacks());
    stacks.addAll(MineCellsBlocks.BLOOMROCK_TILES.getStacks());
    stacks.add(stack(MineCellsBlocks.BLOOMROCK_WILTED_GRASS_BLOCK));

    stacks.addAll(MineCellsBlocks.PUTRID_WOOD.getStacks());
    stacks.add(MineCellsBlocks.ARROW_SIGN);
    stacks.add(stack(MineCellsBlocks.PUTRID_BOARDS));
    stacks.addAll(MineCellsBlocks.PUTRID_BOARD.getStacks());
    stacks.addAll(MineCellsBlocks.WILTED_LEAVES.getStacks());
    stacks.add(MineCellsBlocks.PUTRID_SAPLING);
    stacks.addAll(MineCellsBlocks.ORANGE_WILTED_LEAVES.getStacks());
    stacks.add(MineCellsBlocks.ORANGE_PUTRID_SAPLING);
    stacks.addAll(MineCellsBlocks.RED_WILTED_LEAVES.getStacks());
    stacks.addAll(List.of(
      stack(MineCellsBlocks.RED_PUTRID_SAPLING),
      stack(MineCellsBlocks.CRATE),
      stack(MineCellsBlocks.SMALL_CRATE),
      stack(MineCellsBlocks.BRITTLE_BARREL),
      stack(MineCellsBlocks.KING_STATUE),
      stack(MineCellsBlocks.SKELETON),
      stack(MineCellsBlocks.ROTTING_CORPSE),
      stack(MineCellsBlocks.CORPSE),
      stack(MineCellsBlocks.ELEVATOR_ASSEMBLER),
      stack(MineCellsBlocks.CELL_CRAFTER),
      stack(MineCellsBlocks.HARDSTONE),
      stack(MineCellsBlocks.CHAIN_PILE_BLOCK),
      stack(MineCellsBlocks.CHAIN_PILE),
      stack(MineCellsBlocks.BIG_CHAIN),
      stack(MineCellsBlocks.CAGE),
      stack(MineCellsBlocks.BROKEN_CAGE),
      stack(MineCellsBlocks.SPIKES),
      stack(MineCellsBlocks.FLAG_POLE)
    ));
    stacks.addAll(MineCellsBlocks.FLAG_BLOCKS.stream().map(it -> it.asItem().getDefaultStack()).toList());
    stacks.addAll(List.of(
      stack(MineCellsBlocks.ALCHEMY_EQUIPMENT_0),
      stack(MineCellsBlocks.ALCHEMY_EQUIPMENT_1),
      stack(MineCellsBlocks.ALCHEMY_EQUIPMENT_2),
      stack(MineCellsBlocks.PRISON_TORCH),
      stack(MineCellsBlocks.PROMENADE_TORCH),
      stack(MineCellsBlocks.RAMPARTS_TORCH),
      stack(MineCellsItems.SEWAGE_BUCKET),
      stack(MineCellsItems.ANCIENT_SEWAGE_BUCKET),
      stack(MineCellsItems.ELEVATOR_MECHANISM),
      stack(MineCellsItems.HEALTH_FLASK),
//      stack(MineCellsItems.BLANK_RUNE),
      stack(MineCellsItems.RESET_RUNE),
      stack(MineCellsItems.CONJUNCTIVIUS_RESPAWN_RUNE),
      stack(MineCellsItems.CONCIERGE_RESPAWN_RUNE),
      stack(MineCellsItems.VINE_RUNE)
    ));

    // Crafting ingredients
    stacks.addAll(stackList(
      MineCellsItems.MONSTER_CELL,
      MineCellsItems.BOSS_STEM_CELL,
      MineCellsItems.CELL_HOLDER,
      MineCellsItems.GUTS,
      MineCellsItems.MONSTERS_EYE,
      MineCellsItems.EXPLOSIVE_BULB,
      MineCellsItems.INFECTED_FLESH,
      MineCellsItems.CELL_INFUSED_STEEL,
      MineCellsItems.METAL_SHARDS,
      MineCellsItems.BUZZCUTTER_FANG,
      MineCellsItems.MOLTEN_CHUNK,
      MineCellsItems.SEWER_CALAMARI,
      MineCellsItems.COOKED_SEWER_CALAMARI,
      MineCellsItems.TRANSPOSITION_CORE,
      MineCellsItems.BLOOD_BOTTLE,
      MineCellsItems.ARCANE_GOO
    ));
  }

  private static void toolsAndWeaponsStacks(ItemGroup.DisplayContext ctx, ItemGroup.Entries stacks) {
    stacks.addAll(List.of(
      stack(MineCellsItems.ASSASSINS_DAGGER),
      stack(MineCellsItems.BLOOD_SWORD),
      stack(MineCellsItems.BROADSWORD),
      stack(MineCellsItems.BALANCED_BLADE),
      stack(MineCellsItems.CROWBAR),
      stack(MineCellsItems.NUTCRACKER),
      stack(MineCellsItems.CURSED_SWORD),
      stack(MineCellsItems.HATTORIS_KATANA),
      stack(MineCellsItems.TENTACLE),
      stack(MineCellsItems.FROST_BLAST),
      stack(MineCellsItems.SPITE_SWORD),
      stack(MineCellsItems.FLINT),
      stack(MineCellsItems.PHASER)
    ));
    stacks.addAll(MineCellsItems.BOWS.stream().map(MineCellsItemGroups::stack).toList());
    stacks.add(stack(MineCellsItems.ICE_ARROW));
    stacks.addAll(MineCellsItems.CROSSBOWS.stream().map(MineCellsItemGroups::stack).toList());
    stacks.add(stack(MineCellsItems.EXPLOSIVE_BOLT));
    stacks.addAll(MineCellsItems.OTHER_RANGED.stream().map(MineCellsItemGroups::stack).toList());
    stacks.addAll(MineCellsItems.SHIELDS.stream().map(MineCellsItemGroups::stack).toList());
  }

  private static void spawnEggStacks(ItemGroup.DisplayContext ctx, ItemGroup.Entries stacks) {
    stacks.addAll(MineCellsEntities.getSpawnEggStacks());
  }

  public static final ItemGroupTab GENERAL_TAB = new ItemGroupTab(
    Icon.of(MineCellsBlocks.WILTED_LEAVES.wallLeaves),
    getTabTitle("general"),
    MineCellsItemGroups::generalStacks,
    TABS,
    true
  );

  public static final ItemGroupTab COMBAT_TAB = new ItemGroupTab(
    Icon.of(MineCellsItems.BLOOD_SWORD),
    getTabTitle("combat"),
    MineCellsItemGroups::toolsAndWeaponsStacks,
    TABS,
    true
  );

  public static final ItemGroupTab SPAWN_EGGS_TAB = new ItemGroupTab(
    Icon.of(MineCellsEntities.GRENADIER_SPAWN_EGG),
    getTabTitle("spawn_eggs"),
    MineCellsItemGroups::spawnEggStacks,
    TABS,
    true
  );

  private static Text getTabTitle(String componentName) {
    return Text
      .translatable("itemGroup.minecells.minecells.tab." + componentName)
      .styled(style -> style.withColor(0x46D4FF));
  }

  public static final OwoItemGroup MINECELLS = OwoItemGroup
    .builder(MineCells.createId("minecells"), () -> Icon.of(MineCellsItems.MONSTER_CELL))
    .customTexture(BACKGROUND)
    .initializer(group -> {
      group.tabs.add(GENERAL_TAB);
      group.tabs.add(COMBAT_TAB);
      group.tabs.add(SPAWN_EGGS_TAB);
      group.addButton(linkButton(Icon.of(Items.BOOK), "wiki", "https://mim1q.dev/minecells"));
      group.addButton(linkButton(Icon.of(DISCORD_ICON, 0, 0, 16, 16), "discord", "https://discord.gg/6TjQbSjbuB"));
      group.addButton(linkButton(Icon.of(KOFI_ICON, 0, 0, 16, 16), "kofi", "https://ko-fi.com/mim1q"));
    })
    .displaySingleTab()
    .build();

  public static void init() {
    MINECELLS.initialize();
  }

  public static ItemGroupButton linkButton(Icon icon, String name, String url) {
    return new ItemGroupButton(MINECELLS, icon, name, TABS, () -> {
      final var client = MinecraftClient.getInstance();
      var screen = client.currentScreen;
      client.setScreen(new ConfirmLinkScreen(confirmed -> {
        if (confirmed) Util.getOperatingSystem().open(url);
        client.setScreen(screen);
      }, url, true));
    });
  }
}
