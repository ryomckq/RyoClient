package com.ryo.client;

import com.ryo.client.features.HitManager;
import com.ryo.client.features.HudRenderer;
import com.ryo.client.gui.ClickGUI;
import com.ryo.client.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class RyoClient implements ClientModInitializer {
    private static KeyBinding guiKeybind;

    @Override
    public void onInitializeClient() {
        System.out.println("Starting RyoClient for 1.21.x...");

        // Phím mở GUI (Right Shift)
        guiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ryoclient.gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.ryoclient"
        ));

        // Khởi tạo các Manager
        HudRenderCallback.EVENT.register(new HudRenderer());
        HitManager.init();

        // Xử lý Tick Event (Module + Keybinds)
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            // Mở GUI
            while (guiKeybind.wasPressed()) {
                client.setScreen(new ClickGUI());
            }

            // Quét phím để bật/tắt module
            long windowHandle = client.getWindow().getHandle();
            for (int i = 32; i <= 348; i++) {
                if (InputUtil.isKeyPressed(windowHandle, i)) {
                    ModuleManager.INSTANCE.onKeyPressed(i);
                }
            }

            // Chạy Logic của các Module đang bật
            ModuleManager.INSTANCE.onTick();
        });
    }
}
