package net.hexagoat49.evangelion.entity.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class Eva01 extends Mob implements GeoEntity {
    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private float maxUpStep = 2.5f;
    private float speed = 0.9f;
    private float airSpeed = 4.5f;
    private float jumpForce = 3.5f;
    private boolean jumpTrigger = false;
    private boolean isJumping = false;
    private boolean isMoving = false;
    private boolean isActive = true;

    public Eva01(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.setMaxUpStep(this.maxUpStep);
    }

    public static AttributeSupplier setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200D)
                .add(Attributes.ATTACK_DAMAGE, 20.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 1.0f).build();
    }

    public LivingEntity getPilot() {
        return this.isVehicle() ? (LivingEntity)this.getFirstPassenger() : null;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        this.isMoving = moving;
    }

    public boolean isJumping() {
        return isJumping;
    }

    public void setJumping(boolean jumping) {
        this.isJumping = jumping;
    }

    public void doJump() {
        this.jumpTrigger = true;
    }

    @Override
    protected int calculateFallDamage(float f1, float f2) {
        return 0;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    // The Eva is invisible to the pilot in first person view
    @Override
    public boolean isInvisibleTo(Player player) {
        return this.getPilot() == player &&
                Minecraft.getInstance().options.getCameraType().isFirstPerson();
    }

    // TEMPORARY
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.isVehicle()) {
            player.setYRot(this.getYRot());
            player.startRiding(this);
            this.isInvisibleTo(player);
            return super.mobInteract(player, hand);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.onGround()) {
            if(this.jumpTrigger) {
                Vec3 vec3 = this.getDeltaMovement();
                this.setDeltaMovement(
                        vec3.x * this.airSpeed,
                        this.jumpForce,
                        vec3.z * this.airSpeed);
            }
            this.setJumping(false);
            this.jumpTrigger = false;
        }
        this.setJumping(this.isJumping() || this.getDeltaMovement().y > 0d);
    }

    // Clamp the player rotation when the Eva is inactive
    protected void clampRotation(Entity player) {
        player.setYBodyRot(this.getYRot());

        float f = Mth.wrapDegrees(player.getYRot() - this.getYRot());
        float f1 = Mth.clamp(f, -105.0F, 105.0F);

        player.yRotO += f1 - f;
        player.setYRot(player.getYRot() + f1 - f);
        player.setYHeadRot(player.getYRot());
    }

    // Movement
    @Override
    public void travel(Vec3 pos) {
        this.setMoving(false);
        if (this.isVehicle() && this.isActive()) {
            LivingEntity pilot = this.getPilot();
            if(pilot.xxa != 0 || pilot.zza != 0) {
                this.yRotO = getYRot();
                setYRot(pilot.getYRot());
                this.yBodyRot = this.getYRot();
                this.yHeadRot = this.yBodyRot;

                float x = pilot.xxa * 0.5F;
                float z = pilot.zza;
                if (z <= 0.0F) {
                    z *= 0.25F;
                }
                this.setMoving(true);
                this.setSpeed(this.speed);
                super.travel(new Vec3(x, pos.y, z));
            } else {
                super.travel(pos);
            }
        } else {
            super.travel(pos);
        }
    }

    // Change the player position inside the Eva
    public void positionRider(Entity player, Entity.MoveFunction moveFunction) {
        super.positionRider(player, moveFunction);

        Vec3 vec3 = (new Vec3(0.0D, 54.5D, 4.0D))
                .yRot(-this.yBodyRot * ((float)Math.PI / 180F));
        Entity.MoveFunction move = Entity::setPos;
        move.accept(player,
                this.getX() + vec3.x,
                this.getY() + vec3.y,
                this.getZ() + vec3.z);

        if(this.isActive()) {
            this.setYHeadRot(player.getYHeadRot());
            this.setXRot(-player.getXRot());
        } else {
            this.clampRotation(player);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<>(this, "controller",
                0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> tAnimationState) {
        if(this.isJumping) {
            tAnimationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.eva_base.jump", Animation.LoopType.HOLD_ON_LAST_FRAME));
            return PlayState.CONTINUE;
        }

        if(this.isMoving && !this.isJumping) {
            tAnimationState.getController().setAnimation(RawAnimation.begin()
                    .then("animation.eva_base.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        tAnimationState.getController().setAnimation(RawAnimation.begin()
                .then("animation.eva_base.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
