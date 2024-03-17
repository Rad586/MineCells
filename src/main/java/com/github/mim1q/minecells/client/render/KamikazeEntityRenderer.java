package com.github.mim1q.minecells.client.render;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.client.render.model.KamikazeEntityModel;
import com.github.mim1q.minecells.entity.KamikazeEntity;
import com.github.mim1q.minecells.registry.MineCellsRenderers;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class KamikazeEntityRenderer extends MineCellsEntityRenderer<KamikazeEntity, KamikazeEntityModel> {
  private static final Identifier TEXTURE = MineCells.createId("textures/entity/kamikaze.png");

  public KamikazeEntityRenderer(EntityRendererFactory.Context ctx) {
    super(ctx, new KamikazeEntityModel(ctx.getPart(MineCellsRenderers.KAMIKAZE_LAYER)), 0.3F);
  }

  @Override
  public Identifier getTexture(KamikazeEntity entity) {
    return TEXTURE;
  }
}
