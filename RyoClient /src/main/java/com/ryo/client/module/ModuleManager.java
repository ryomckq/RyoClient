package com.ryo.client.module;

import org.lwjgl.glfw.GLFW;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    public static final ModuleManager INSTANCE = new ModuleManager();
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        // Đăng ký các module
        modules.add(new Module("ToggleSprint", GLFW.GLFW_KEY_R) {
            @Override
            public void onTick() {
                if (mc.player != null && !mc.player.horizontalCollision && mc.player.input.movementForward > 0) {
                    mc.player.setSprinting(true);
                }
            }
        });
        modules.add(new Module("HitEffect", GLFW.GLFW_KEY_UNKNOWN) {});
        modules.add(new Module("HUD", GLFW.GLFW_KEY_UNKNOWN) {
            { setToggled(true); } // Bật HUD mặc định
        });
        modules.add(new Module("Zoom", GLFW.GLFW_KEY_C) {});
        modules.add(new Module("CustomCrosshair", GLFW.GLFW_KEY_UNKNOWN) {});
    }

    public List<Module> getModules() { return modules; }

    public Module getModuleByName(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public void onTick() {
        for (Module m : modules) {
            if (m.isToggled()) m.onTick();
        }
    }

    public void onKeyPressed(int keyCode) {
        for (Module m : modules) {
            if (m.getKeybind() == keyCode) m.toggle();
        }
    }
}
