package com.github.mim1q.minecells.registry;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.client.gui.screen.CellForgeScreen;
import com.github.mim1q.minecells.client.render.*;
import com.github.mim1q.minecells.client.render.blockentity.BiomeBannerBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.KingdomPortalBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.ReturnStoneBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.RunicVinePlantBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.portal.DoorwayPortalBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.portal.TeleporterBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.statue.DecorativeStatueBlockEntityRenderer;
import com.github.mim1q.minecells.client.render.blockentity.statue.KingStatueModel;
import com.github.mim1q.minecells.client.render.conjunctivius.*;
import com.github.mim1q.minecells.client.render.item.BiomeBannerItemRenderer;
import com.github.mim1q.minecells.client.render.model.*;
import com.github.mim1q.minecells.client.render.model.conjunctivius.ConjunctiviusEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.ElevatorEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.ObeliskEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.TentacleWeaponEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.projectile.BigGrenadeEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.projectile.ConjunctiviusProjectileEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.projectile.DisgustingWormEggEntityModel;
import com.github.mim1q.minecells.client.render.model.nonliving.projectile.GrenadeEntityModel;
import com.github.mim1q.minecells.client.render.nonliving.*;
import com.github.mim1q.minecells.client.render.nonliving.projectile.*;
import com.github.mim1q.minecells.world.FoggyDimensionEffects;
import com.github.mim1q.minecells.world.PromenadeDimensionEffects;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.client.rendering.v1.*;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.TexturedModelDataProvider;
import net.fabricmc.fabric.mixin.client.rendering.DimensionEffectsAccessor;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class MineCellsRenderers {
  private static boolean dynamicItemRenderersRegistered = false;

  public static final EntityModelLayer LEAPING_ZOMBIE_LAYER = new EntityModelLayer(MineCells.createId("leaping_zombie"), "main");
  public static final EntityModelLayer SHOCKER_LAYER = new EntityModelLayer(MineCells.createId("shocker"), "main");
  public static final EntityModelLayer GRENADIER_LAYER = new EntityModelLayer(MineCells.createId("grenadier"), "main");
  public static final EntityModelLayer DISGUSTING_WORM_LAYER = new EntityModelLayer(MineCells.createId("disgusting_worm"), "main");
  public static final EntityModelLayer INQUISITOR_LAYER = new EntityModelLayer(MineCells.createId("inquisitor"), "main");
  public static final EntityModelLayer KAMIKAZE_LAYER = new EntityModelLayer(MineCells.createId("kamikaze"), "main");
  public static final EntityModelLayer PROTECTOR_LAYER = new EntityModelLayer(MineCells.createId("protector"), "main");
  public static final EntityModelLayer UNDEAD_ARCHER_LAYER = new EntityModelLayer(MineCells.createId("undead_archer"), "main");
  public static final EntityModelLayer SHIELDBEARER_LAYER = new EntityModelLayer(MineCells.createId("shieldbearer"), "main");
  public static final EntityModelLayer MUTATED_BAT_LAYER = new EntityModelLayer(MineCells.createId("mutated_bat"), "main");
  public static final EntityModelLayer SEWERS_TENTACLE_LAYER = new EntityModelLayer(MineCells.createId("sewers_tentacle"), "main");
  public static final EntityModelLayer RANCID_RAT_LAYER = new EntityModelLayer(MineCells.createId("rancid_rat"), "main");
  public static final EntityModelLayer RUNNER_LAYER = new EntityModelLayer(MineCells.createId("runner"), "main");
  public static final EntityModelLayer SCORPION_LAYER = new EntityModelLayer(MineCells.createId("scorpion"), "main");

  public static final EntityModelLayer CONJUNCTIVIUS_MAIN_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius"), "main");
  public static final EntityModelLayer CONJUNCTIVIUS_EYE_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius"), "eye");
  public static final EntityModelLayer CONJUNCTIVIUS_TENTACLE_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius"), "tentacle");
  public static final EntityModelLayer CONJUNCTIVIUS_SPIKE_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius"), "spike");
  public static final EntityModelLayer CONJUNCTIVIUS_CHAIN_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius"), "chain");

  public static final EntityModelLayer GRENADE_LAYER = new EntityModelLayer(MineCells.createId("grenade"), "main");
  public static final EntityModelLayer BIG_GRENADE_LAYER = new EntityModelLayer(MineCells.createId("big_grenade"), "main");
  public static final EntityModelLayer DISGUSTING_WORM_EGG_LAYER = new EntityModelLayer(MineCells.createId("disgusting_worm_egg"), "main");
  public static final EntityModelLayer CONJUNCTIVIUS_PROJECTILE_LAYER = new EntityModelLayer(MineCells.createId("conjunctivius_projectile"), "main");

  public static final EntityModelLayer ELEVATOR_LAYER = new EntityModelLayer(MineCells.createId("elevator"), "main");
  public static final EntityModelLayer TENTACLE_WEAPON_LAYER = new EntityModelLayer(MineCells.createId("tentacle_weapon"), "main");
  public static final EntityModelLayer OBELISK_LAYER = new EntityModelLayer(MineCells.createId("obelisk"), "main");

  public static final EntityModelLayer KINGDOM_PORTAL_LAYER = new EntityModelLayer(MineCells.createId("kingdom_portal"), "main");
  public static final EntityModelLayer BIOME_BANNER_LAYER = new EntityModelLayer(MineCells.createId("biome_banner"), "main");
  public static final EntityModelLayer KING_STATUE_LAYER = new EntityModelLayer(MineCells.createId("king_statue"), "main");
  public static final EntityModelLayer TELEPORTER_LAYER = registerLayer("teleporter", TeleporterBlockEntityRenderer.TeleporterModel::getTexturedModelData);

  private static EntityModelLayer registerLayer(String path, String name, TexturedModelDataProvider provider) {
    var layer = new EntityModelLayer(MineCells.createId(path), name);
    EntityModelLayerRegistry.registerModelLayer(layer, provider);
    return layer;
  }

  private static EntityModelLayer registerLayer(String path, TexturedModelDataProvider provider) {
    return registerLayer(path, "main", provider);
  }

  @SuppressWarnings("UnstableApiUsage")
  public static void init() {
    EntityModelLayerRegistry.registerModelLayer(LEAPING_ZOMBIE_LAYER, LeapingZombieEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(SHOCKER_LAYER, ShockerEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(GRENADIER_LAYER, GrenadierEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(DISGUSTING_WORM_LAYER, DisgustingWormEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(INQUISITOR_LAYER, InquisitorEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(KAMIKAZE_LAYER, KamikazeEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(PROTECTOR_LAYER, ProtectorEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(UNDEAD_ARCHER_LAYER, UndeadArcherEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(SHIELDBEARER_LAYER, ShieldbearerEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(MUTATED_BAT_LAYER, MutatedBatEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(SEWERS_TENTACLE_LAYER, SewersTentacleEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(RANCID_RAT_LAYER, RancidRatEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(RUNNER_LAYER, RunnerEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(SCORPION_LAYER, ScorpionEntityModel::getTexturedModelData);

    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_MAIN_LAYER, ConjunctiviusEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_EYE_LAYER, ConjunctiviusEyeRenderer.ConjunctiviusEyeModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_TENTACLE_LAYER, ConjunctiviusTentacleRenderer.ConjunctiviusTentacleModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_SPIKE_LAYER, ConjunctiviusSpikeRenderer.ConjunctiviusSpikeModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_CHAIN_LAYER, ConjunctiviusChainRenderer.ConjunctiviusChainModel::getTexturedModelData);

    EntityModelLayerRegistry.registerModelLayer(GRENADE_LAYER, GrenadeEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(BIG_GRENADE_LAYER, BigGrenadeEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(DISGUSTING_WORM_EGG_LAYER, DisgustingWormEggEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(CONJUNCTIVIUS_PROJECTILE_LAYER, ConjunctiviusProjectileEntityModel::getTexturedModelData);

    EntityModelLayerRegistry.registerModelLayer(ELEVATOR_LAYER, ElevatorEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(TENTACLE_WEAPON_LAYER, TentacleWeaponEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(OBELISK_LAYER, ObeliskEntityModel::getTexturedModelData);

    EntityRendererRegistry.register(MineCellsEntities.LEAPING_ZOMBIE, LeapingZombieEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SHOCKER, ShockerEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.GRENADIER, GrenadierEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.DISGUSTING_WORM, DisgustingWormEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.INQUISITOR, InquisitorEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.KAMIKAZE, KamikazeEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.PROTECTOR, ProtectorEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.UNDEAD_ARCHER, UndeadArcherEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SHIELDBEARER, ShieldbearerEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.MUTATED_BAT, MutatedBatEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SEWERS_TENTACLE, SewersTentacleEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.RANCID_RAT, RancidRatEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.RUNNER, RunnerEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SCORPION, ScorpionEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.CONJUNCTIVIUS, ConjunctiviusEntityRenderer::new);

    EntityRendererRegistry.register(MineCellsEntities.GRENADE, GrenadeEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.BIG_GRENADE, BigGrenadeEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.DISGUSTING_WORM_EGG, DisgustingWormEggEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.MAGIC_ORB, MagicOrbEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SCORPION_SPIT, ScorpionSpitEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.CONJUNCTIVIUS_PROJECTILE, ConjunctiviusProjectileEntityRenderer::new);

    EntityRendererRegistry.register(MineCellsEntities.ELEVATOR, ElevatorEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.CELL, CellEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.TENTACLE_WEAPON, TentacleWeaponEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.CONJUNCTIVIUS_OBELISK, ObeliskEntityRenderer::new);
    EntityRendererRegistry.register(MineCellsEntities.SPAWNER_RUNE, SpawnerRuneEntityRenderer::new);

    DimensionEffectsAccessor.getIdentifierMap().put(MineCells.createId("foggy"), new FoggyDimensionEffects());
    DimensionEffectsAccessor.getIdentifierMap().put(MineCells.createId("promenade"), new PromenadeDimensionEffects());

    HandledScreens.register(MineCellsScreenHandlerTypes.CELL_FORGE, CellForgeScreen::new);
  }

  public static void initBlocks() {
    FluidRenderHandlerRegistry.INSTANCE.register(MineCellsFluids.STILL_SEWAGE, MineCellsFluids.FLOWING_SEWAGE, new SimpleFluidRenderHandler(
      new Identifier("block/water_still"),
      new Identifier("block/water_flow"),
      0xA2E751
    ));
    FluidRenderHandlerRegistry.INSTANCE.register(MineCellsFluids.STILL_ANCIENT_SEWAGE, MineCellsFluids.FLOWING_ANCIENT_SEWAGE, new SimpleFluidRenderHandler(
      new Identifier("block/water_still"),
      new Identifier("block/water_flow"),
      0xE0C93B
    ));

    BlockRenderLayerMap.INSTANCE.putBlocks(
      RenderLayer.getCutout(),
      MineCellsBlocks.BIG_CHAIN,
      MineCellsBlocks.CHAIN_PILE,
      MineCellsBlocks.CAGE,
      MineCellsBlocks.BROKEN_CAGE,
      MineCellsBlocks.SPIKES,
      MineCellsBlocks.HANGED_SKELETON,
      MineCellsBlocks.SKELETON,
      MineCellsBlocks.HANGED_ROTTING_CORPSE,
      MineCellsBlocks.HANGED_CORPSE,
      MineCellsBlocks.WILTED_LEAVES.leaves,
      MineCellsBlocks.WILTED_LEAVES.hangingLeaves,
      MineCellsBlocks.WILTED_LEAVES.wallLeaves,
      MineCellsBlocks.ORANGE_WILTED_LEAVES.leaves,
      MineCellsBlocks.ORANGE_WILTED_LEAVES.hangingLeaves,
      MineCellsBlocks.ORANGE_WILTED_LEAVES.wallLeaves,
      MineCellsBlocks.RED_WILTED_LEAVES.leaves,
      MineCellsBlocks.RED_WILTED_LEAVES.hangingLeaves,
      MineCellsBlocks.RED_WILTED_LEAVES.wallLeaves,
      MineCellsBlocks.RUNIC_VINE,
      MineCellsBlocks.RUNIC_VINE_PLANT,
      MineCellsBlocks.CELL_FORGE,
      MineCellsBlocks.ALCHEMY_EQUIPMENT_0,
      MineCellsBlocks.ALCHEMY_EQUIPMENT_2,
      MineCellsBlocks.PUTRID_WOOD.door,
      MineCellsBlocks.PRISON_TORCH,
      MineCellsBlocks.PROMENADE_TORCH,
      MineCellsBlocks.SPAWNER_RUNE,
      MineCellsBlocks.WILTED_GRASS_BLOCK
    );
    BlockRenderLayerMap.INSTANCE.putBlocks(
      RenderLayer.getTranslucent(),
      MineCellsBlocks.ALCHEMY_EQUIPMENT_1
    );

    BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MineCellsFluids.STILL_SEWAGE, MineCellsFluids.FLOWING_SEWAGE);

    EntityModelLayerRegistry.registerModelLayer(KINGDOM_PORTAL_LAYER, KingdomPortalBlockEntityRenderer.KingdomPortalBlockEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(BIOME_BANNER_LAYER, BiomeBannerBlockEntityRenderer.BiomeBannerBlockEntityModel::getTexturedModelData);
    EntityModelLayerRegistry.registerModelLayer(KING_STATUE_LAYER, KingStatueModel::getTexturedModelData);

    BlockEntityRendererFactories.register(MineCellsBlockEntities.KINGDOM_PORTAL_CORE_BLOCK_ENTITY, KingdomPortalBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.BIOME_BANNER_BLOCK_ENTITY, BiomeBannerBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.DECORATIVE_STATUE_BLOCK_ENTITY, DecorativeStatueBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.RETURN_STONE, ReturnStoneBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.RUNIC_VINE_PLANT, RunicVinePlantBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.TELEPORTER, TeleporterBlockEntityRenderer::new);
    BlockEntityRendererFactories.register(MineCellsBlockEntities.DOORWAY, DoorwayPortalBlockEntityRenderer::new);

    ModelPredicateProviderRegistry.register(
      MineCellsItems.HATTORIS_KATANA,
      new Identifier("blocking"),
      (stack, world, entity, i) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F
    );

    ColorProviderRegistry.BLOCK.register(
      (state, world, pos, tintIndex) -> world == null ? 0x80CC80 : BiomeColors.getFoliageColor(world, pos),
      MineCellsBlocks.WILTED_LEAVES.leaves, MineCellsBlocks.WILTED_LEAVES.hangingLeaves, MineCellsBlocks.WILTED_LEAVES.wallLeaves
    );

    ColorProviderRegistry.BLOCK.register(
      (state, world, pos, tintIndex) -> world == null ? 0x80CC80 : BiomeColors.getGrassColor(world, pos),
      MineCellsBlocks.WILTED_GRASS_BLOCK
    );

    ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 0x80CC80,
      MineCellsBlocks.WILTED_LEAVES.leaves, MineCellsBlocks.WILTED_LEAVES.hangingLeaves, MineCellsBlocks.WILTED_LEAVES.wallLeaves,
      MineCellsBlocks.WILTED_GRASS_BLOCK
    );

    // I've accepted this code just like it is
    // What really matters is on the inside (and that it somehow works)
    LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
      ((entityType, entityRenderer, registrationHelper, context) -> {
        if (!dynamicItemRenderersRegistered) {
          BuiltinItemRendererRegistry.INSTANCE.register(
            MineCellsItems.BIOME_BANNER,
            new BiomeBannerItemRenderer(context.getModelLoader())
          );
          dynamicItemRenderersRegistered = true;
        }
      })
    );
  }
}