package com.github.mim1q.minecells.particle;

import com.github.mim1q.minecells.util.ParticleUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

public class ExplosionParticle extends SpriteBillboardParticle {

  private final float targetRadius;
  private float currentRadius;

  protected ExplosionParticle(ClientWorld clientWorld, double x, double y, double z) {
    super(clientWorld, x, y, z, 0.0D, 0.0D, 0.0D);
    this.setVelocity(0.0D, 0.0D, 0.0D);
    this.setMaxAge(5);
    this.targetRadius = 2.0F;
    this.currentRadius = 0.0F;
  }

  @Override
  public float getSize(float tickDelta) {
    return this.currentRadius;
  }

  @Override
  protected int getBrightness(float tint) {
    return 0xF000F0;
  }

  @Override
  public void tick() {
    super.tick();
    if (this.age < 3) {
      this.currentRadius = MathHelper.clampedLerp(0.0F, this.targetRadius, this.age / 2.0F);
    } else {
      this.currentRadius = MathHelper.clampedLerp(this.targetRadius, 0.0F, (this.age - 2) / 6.0F);
    }
    this.setAlpha(this.currentRadius / this.targetRadius);
  }

  @Override
  public ParticleTextureSheet getType() {
    return ParticleUtils.getTranslucentParticleType();
  }

  @Environment(EnvType.CLIENT)
  public static class Factory implements ParticleFactory<DefaultParticleType> {
    private final SpriteProvider spriteProvider;

    public Factory(SpriteProvider spriteProvider) {
      this.spriteProvider = spriteProvider;
    }

    public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
      ExplosionParticle explosionParticle = new ExplosionParticle(clientWorld, d, e, f);
      explosionParticle.setSprite(this.spriteProvider);
      return explosionParticle;
    }
  }
}
