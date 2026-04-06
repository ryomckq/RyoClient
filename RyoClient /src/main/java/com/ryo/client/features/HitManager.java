package com.ryo.client.features;

import com.ryo.client.module.ModuleManager;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;

public class HitManager {
    public static int hitMarkerTime = 0;

    public static void init() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient && entity instanceof LivingEntity) {
                if (ModuleManager.INSTANCE.getModuleByName("HitEffect").isToggled()) {
                    MinecraftClient mc = MinecraftClient.getInstance();
                    
                    // 1. Sinh Particle tùy chỉnh số lượng (Ví dụ: 15 hạt)
                    int particleCount = 15;
                    for (int i = 0; i < particleCount; i++) {
                        world.addParticle(ParticleTypes.CRIT, 
                            entity.getX(), entity.getBodyY(0.5D), entity.getZ(), 
                            (Math.random() - 0.5), (Math.random() - 0.5), (Math.random() - 0.5));
                    }

                    // 2. Âm thanh Hit Sound
                    player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5F, 1.0F);

                    // 3. Kích hoạt Hit Marker (Dấu X)
                    hitMarkerTime = 15; // Hiện trong 15 tick
                }
            }
            return ActionResult.PASS;
        });
    }
}
