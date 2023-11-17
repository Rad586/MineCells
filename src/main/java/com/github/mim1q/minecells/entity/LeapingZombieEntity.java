package com.github.mim1q.minecells.entity;

import com.github.mim1q.minecells.entity.ai.goal.LeapGoal;
import com.github.mim1q.minecells.entity.interfaces.ILeapEntity;
import com.github.mim1q.minecells.registry.MineCellsSounds;
import com.github.mim1q.minecells.util.animation.AnimationProperty;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;


public class LeapingZombieEntity extends MineCellsEntity implements ILeapEntity {

  public AnimationProperty additionalRotation = new AnimationProperty(0.0F);

  private static final TrackedData<Integer> LEAP_COOLDOWN = DataTracker.registerData(LeapingZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);
  private static final TrackedData<Boolean> LEAP_CHARGING = DataTracker.registerData(LeapingZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
  private static final TrackedData<Boolean> LEAP_RELEASING = DataTracker.registerData(LeapingZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


  public LeapingZombieEntity(EntityType<? extends HostileEntity> type, World world) {
    super(type, world);
    this.ignoreCameraFrustum = true;
  }

  @Override
  protected void initDataTracker() {
    super.initDataTracker();
    this.dataTracker.startTracking(LEAP_COOLDOWN, 50);
    this.dataTracker.startTracking(LEAP_CHARGING, false);
    this.dataTracker.startTracking(LEAP_RELEASING, false);
  }

  @Override
  public void tick() {
    super.tick();
    if (getWorld().isClient()) {
      clientTick();
    } else {
      this.decrementCooldown(LEAP_COOLDOWN);
    }
  }

  protected void clientTick() {
    if (this.isLeapCharging()) {
      this.additionalRotation.setupTransitionTo(90.0F, 15);
    } else {
      this.additionalRotation.setupTransitionTo(0.0F, 15);
    }
  }

  @Override
  public void initGoals() {
    super.initGoals();

    this.goalSelector.add(0, new LeapGoal<>(this, 15, 20, 0.1F));
    this.goalSelector.add(1, new MeleeAttackGoal(this, 1.3D, false));
  }

  @Override
  public boolean isLeapCharging() {
    return this.dataTracker.get(LEAP_CHARGING);
  }

  @Override
  public void setLeapCharging(boolean charging) {
    this.dataTracker.set(LEAP_CHARGING, charging);
  }

  @Override
  public boolean isLeapReleasing() {
    return this.dataTracker.get(LEAP_RELEASING);
  }

  @Override
  public void setLeapReleasing(boolean releasing) {
    this.dataTracker.set(LEAP_RELEASING, releasing);

  }

  @Override
  public int getLeapCooldown() {
    return this.dataTracker.get(LEAP_COOLDOWN);
  }

  @Override
  public void setLeapCooldown(int ticks) {
    this.dataTracker.set(LEAP_COOLDOWN, ticks);
  }

  @Override
  public int getLeapMaxCooldown() {
    return 20 + this.getRandom().nextInt(60);
  }

  @Override
  public float getLeapDamage() {
    return 6.0F;
  }

  @Override
  public double getLeapRange() {
    return 15.0F;
  }

  @Override
  public SoundEvent getLeapChargeSoundEvent() {
    return MineCellsSounds.LEAPING_ZOMBIE_CHARGE;
  }

  @Override
  public SoundEvent getLeapReleaseSoundEvent() {
    return MineCellsSounds.LEAPING_ZOMBIE_RELEASE;
  }

  @Override
  public void writeCustomDataToNbt(NbtCompound nbt) {
    super.writeCustomDataToNbt(nbt);
    nbt.putInt("leapCooldown", this.getLeapCooldown());
  }

  @Override
  public void readCustomDataFromNbt(NbtCompound nbt) {
    super.readCustomDataFromNbt(nbt);
    this.setLeapCooldown(nbt.getInt("leapCooldown"));
  }

  public static DefaultAttributeContainer.Builder createLeapingZombieAttributes() {
    return createLivingAttributes()
      .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
      .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0D)
      .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
      .add(EntityAttributes.GENERIC_ARMOR, 3.0D)
      .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
      .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 1.0D);
  }
}
