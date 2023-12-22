package com.github.mim1q.minecells.client.render.nonliving;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.client.render.model.nonliving.ObeliskEntityModel;
import com.github.mim1q.minecells.entity.nonliving.obelisk.ObeliskEntity;
import com.github.mim1q.minecells.registry.MineCellsRenderers;
import com.github.mim1q.minecells.util.MathUtils;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Quaternionf;

public class ObeliskEntityRenderer extends EntityRenderer<ObeliskEntity> {
  private final Identifier texture;
  private final ObeliskEntityModel model;

  public ObeliskEntityRenderer(EntityRendererFactory.Context ctx, String texture) {
    super(ctx);
    this.model = new ObeliskEntityModel(ctx.getPart(MineCellsRenderers.OBELISK_LAYER));
    this.texture = MineCells.createId("textures/entity/obelisk/" + texture + ".png");
  }

  @Override
  public void render(ObeliskEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
    super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    matrices.push();
    matrices.scale(1.0F, -1.0F, -1.0F);
    matrices.translate(0.0F, -1.5F, 0.0F);
    matrices.multiply(new Quaternionf().rotationY(MathUtils.radians(yaw)));
    float animationProgress = entity.age + tickDelta;
    this.model.setAngles(entity, 0.0F, 0.0F, animationProgress, 0.0F, 0.0F);
    VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(texture));
    this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    VertexConsumer glowVertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(texture));
    var glow = entity.glow.update(animationProgress);
    this.model.renderGlow(matrices, glowVertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, glow);
    matrices.pop();
  }

  @Override
  public Identifier getTexture(ObeliskEntity entity) {
    return texture;
  }
}
