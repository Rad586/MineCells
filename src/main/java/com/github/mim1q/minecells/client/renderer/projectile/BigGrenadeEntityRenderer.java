package com.github.mim1q.minecells.client.renderer.projectile;

import com.github.mim1q.minecells.MineCells;
import com.github.mim1q.minecells.client.model.projectile.BigGrenadeEntityModel;
import com.github.mim1q.minecells.entity.projectile.BigGrenadeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class BigGrenadeEntityRenderer extends AbstractGrenadeEntityRenderer<BigGrenadeEntity> {

    private static final Identifier TEXTURE = new Identifier(MineCells.MOD_ID, "textures/entity/grenades/big_grenade.png");
    private static final BigGrenadeEntityModel MODEL = new BigGrenadeEntityModel(BigGrenadeEntityModel.getTexturedModelData().createModel());

    public BigGrenadeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, TEXTURE, TEXTURE, MODEL);
    }
}