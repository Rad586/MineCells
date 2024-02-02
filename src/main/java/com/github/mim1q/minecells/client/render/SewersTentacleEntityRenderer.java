package com.github.mim1q.minecells.client.render;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.client.render.model.SewersTentacleEntityModel;
import com.github.mim1q.minecells.entity.SewersTentacleEntity;
import com.github.mim1q.minecells.registry.MineCellsRenderers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SewersTentacleEntityRenderer extends MineCellsEntityRenderer<SewersTentacleEntity, SewersTentacleEntityModel> {

  private static final Identifier TEXTURE_BLUE = MineCells.createId("textures/entity/sewers_tentacle/blue.png");
  private static final Identifier TEXTURE_RED = MineCells.createId("textures/entity/sewers_tentacle/red.png");
  private static final Identifier TEXTURE_PURPLE = MineCells.createId("textures/entity/sewers_tentacle/purple.png");

  public SewersTentacleEntityRenderer(EntityRendererFactory.Context context) {
    super(context, new SewersTentacleEntityModel(context.getPart(MineCellsRenderers.SEWERS_TENTACLE_LAYER)), 0.0F);
  }

  @Override
  public Identifier getTexture(SewersTentacleEntity entity) {
    return switch (entity.getVariant()) {
      default -> TEXTURE_BLUE;
      case 1 -> TEXTURE_RED;
      case 2 -> TEXTURE_PURPLE;
    };
  }

  @Override
  public void render(SewersTentacleEntity mobEntity, float f, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
    mobEntity.wobble.update(mobEntity.age + tickDelta);
    mobEntity.belowGround.update(mobEntity.age + tickDelta);
    mobEntity.wobbleOffset.update(mobEntity.age + tickDelta);
    if (mobEntity.isInvisible()) {
      return;
    }
    super.render(mobEntity, f, tickDelta, matrixStack, vertexConsumerProvider, i);
  }

  @Override
  protected float getLyingAngle(SewersTentacleEntity entity) {
    return 0.0F;
  }
}
