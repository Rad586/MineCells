package com.github.mim1q.minecells.entity.nonliving.projectile;

import com.github.mim1q.minecells.misc.MineCellsExplosion;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrenadeEntity extends ProjectileEntity {
  private Vec3d shootVector;
  private boolean shouldResetVelocity = false;
  private static final TrackedData<Integer> FUSE = DataTracker.registerData(GrenadeEntity.class, TrackedDataHandlerRegistry.INTEGER);
  protected float damage = 10.0F;
  protected float radius = 6.0F;

  public GrenadeEntity(EntityType<? extends GrenadeEntity> type, World world) {
    super(type, world);
  }

  public void shoot(Vec3d v) {
    this.shouldResetVelocity = true;
    this.shootVector = v;
  }

  public int getMaxFuse() {
    return 10 + this.random.nextInt(5);
  }

  @Override
  protected void initDataTracker() {
    this.dataTracker.startTracking(FUSE, this.getMaxFuse());
  }

  protected MoveEffect getMoveEffect() {
    return MoveEffect.NONE;
  }

  @Override
  public void tick() {
    if (this.shouldResetVelocity) {
      this.shouldResetVelocity = false;
      this.setVelocity(shootVector);
    }

    int fuse = this.getFuse() - 1;
    if (this.isOnGround()) {
      this.setVelocity(this.getVelocity().multiply(0.7D, 0.0D, 0.7D));
      this.setFuse(fuse);
    }
    if (!getWorld().isClient) {
      if (fuse <= 0) {
        this.explode();
        this.discard();
      }
      this.addVelocity(0.0D, -0.04D, 0.0D);
    }
    this.move(MovementType.SELF, this.getVelocity());
  }

  public void explode() {
    MineCellsExplosion.explode(
      (ServerWorld) getWorld(),
      (LivingEntity) this.getOwner(),
      this.getPos(),
      this.damage,
      this.radius
    );
  }

  public int getFuse() {
    return this.dataTracker.get(FUSE);
  }

  public void setFuse(int fuse) {
    this.dataTracker.set(FUSE, fuse);
  }

  @Override
  public void readCustomDataFromNbt(NbtCompound nbt) {
    super.readCustomDataFromNbt(nbt);
    this.setFuse(nbt.getInt("fuse"));
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putInt("fuse", this.getFuse());
  }
}
