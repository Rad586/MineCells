package com.github.mim1q.minecells.client.render.model;

import com.github.mim1q.minecells.entity.SewersTentacleEntity;
import com.github.mim1q.minecells.util.MathUtils;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SewersTentacleEntityModel extends EntityModel<SewersTentacleEntity> {

  private final ModelPart root;
  private final ModelPart[] segments = new ModelPart[5];

  public SewersTentacleEntityModel(ModelPart root) {
    this.root = root.getChild("root");
    this.segments[0] = this.root.getChild("segment_0");
    this.segments[1] = this.segments[0].getChild("segment_1");
    this.segments[2] = this.segments[1].getChild("segment_2");
    this.segments[3] = this.segments[2].getChild("segment_3");
    this.segments[4] = this.segments[3].getChild("segment_4");
  }

  public static TexturedModelData getTexturedModelData() {
    ModelData modelData = new ModelData();
    ModelPartData modelPartData = modelData.getRoot();

    ModelPartData dRoot = modelPartData.addChild("root",
      ModelPartBuilder.create(),
      ModelTransform.pivot(0.0F, 24.0F, 0.0F)
    );

    ModelPartData dSegment0 = dRoot.addChild("segment_0",
      ModelPartBuilder.create()
        .uv(0, 0)
        .cuboid(-4.0F, -12.0F, -3.0F, 8, 16, 6, new Dilation(0.01F))
        .uv(26, 31)
        .cuboid(-2.0F, -9.0F, -4.0F, 4, 5, 1),
      ModelTransform.NONE
    );

    ModelPartData dSegment1 = dSegment0.addChild("segment_1",
      ModelPartBuilder.create()
        .uv(0, 22)
        .cuboid(-3.5F, -11.0F, -3.0F, 7, 11, 6)
        .uv(26, 31)
        .cuboid(-2.0F, -8.0F, -4.0F, 4, 5, 1),
      ModelTransform.pivot(0.0F, -10.0F, 0.0F)
    );

    ModelPartData dSegment2 = dSegment1.addChild("segment_2",
      ModelPartBuilder.create()
        .uv(26, 18)
        .cuboid(-3.0F, -9.0F, -2.0F, 6, 9, 4)
        .uv(36, 31)
        .cuboid(-1.5F, -6.0F, -3.0F, 3, 4, 1),
      ModelTransform.pivot(0.0F, -10.0F, 0.0F)
    );

    ModelPartData dSegment3 = dSegment2.addChild("segment_3",
      ModelPartBuilder.create()
        .uv(28, 0)
        .cuboid(-2.0F, -8.0F, -1.5F, 4, 8, 3, new Dilation(0.01F))
        .uv(0, 0)
        .cuboid(-1.0F, -6.0F, -2.5F, 2, 3, 1),
      ModelTransform.pivot(0.0F, -8.0F, 0.0F)
    );

    dSegment3.addChild("segment_4",
      ModelPartBuilder.create()
        .uv(0, 39)
        .cuboid(-1.5F, -4.0F, -1.0F, 3, 5, 2),
      ModelTransform.pivot(0.0F, -8.0F, -0.5F)
    );

    return TexturedModelData.of(modelData, 64, 64);
  }

  @Override
  public void setAngles(SewersTentacleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    wiggleTentacle(this.segments, animationProgress, 15.0F * entity.wobble.getValue(), entity.getId(), entity.wobbleOffset.getValue() * 0.2F);
    var belowGround = entity.isForDisplay() ? 0.75f : entity.belowGround.getValue();
    this.root.pivotY = 24.0F - belowGround * 16.0F + 8.5F;
  }

  public static void wiggleTentacle(ModelPart[] segments, float animationProgress, float degrees, int id, float offset) {
    for (int i = 0; i < segments.length - 1; i++) {
      segments[i].yaw = (float) Math.sin(animationProgress * 0.125F) * 0.25F;
      segments[i].pitch = (float) Math.sin(animationProgress * 0.25F - i + id) * MathUtils.radians(degrees) - offset;
    }
  }

  @Override
  public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
    this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
  }
}
