package com.ryo.client.features;

import com.ryo.client.module.Module;
import com.ryo.client.module.ModuleManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

public class HudRenderer implements HudRenderCallback {
    // API 1.21+ sử dụng Identifier.of
    private static final Identifier LOGO = Identifier.of("ryoclient", "logo.png");

    @Override
    public void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.options.hudHidden || !ModuleManager.INSTANCE.getModuleByName("HUD").isToggled()) return;

        int width = mc.getWindow().getScaledWidth();
        int height = mc.getWindow().getScaledHeight();

        // 1. Render Logo (Góc trái trên)
        context.drawTexture(LOGO, 5, 5, 0, 0, 32, 32, 32, 32);

        // 2. Render Info (FPS, Ping)
        context.drawText(mc.textRenderer, "RyoClient Lite", 42, 8, 0x00FFCC, true);
        context.drawText(mc.textRenderer, "FPS: " + mc.getCurrentFps(), 42, 20, 0xFFFFFF, true);
        
        int ping = 0;
        if (mc.getNetworkHandler() != null && mc.player != null) {
            var playerInfo = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
            if (playerInfo != null) ping = playerInfo.getLatency();
        }
        context.drawText(mc.textRenderer, "Ping: " + ping + "ms", 42, 32, 0xFFFFFF, true);

        // 3. Render Module List (Góc phải màn hình)
        int y = 5;
        for (Module m : ModuleManager.INSTANCE.getModules()) {
            if (m.isToggled()) {
                int textWidth = mc.textRenderer.getWidth(m.getName());
                context.drawText(mc.textRenderer, m.getName(), width - textWidth - 5, y, 0x55FF55, true);
                y += 12;
            }
        }

        // 4. Render Hit Marker (Dấu X giữa màn hình)
        if (HitManager.hitMarkerTime > 0) {
            int centerX = width / 2;
            int centerY = height / 2;
            context.drawText(mc.textRenderer, "X", centerX - 3, centerY - 4, 0xFF0000, true);
            HitManager.hitMarkerTime--;
        }
    }
}
