package com.ryo.client.module;

import net.minecraft.client.MinecraftClient;

public abstract class Module {
    protected MinecraftClient mc = MinecraftClient.getInstance();
    private final String name;
    private int keybind;
    private boolean toggled;

    public Module(String name, int keybind) {
        this.name = name;
        this.keybind = keybind;
        this.toggled = false;
    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (toggled) onEnable();
        else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}

    public String getName() { return name; }
    public boolean isToggled() { return toggled; }
    public void setToggled(boolean toggled) { this.toggled = toggled; }
    public int getKeybind() { return keybind; }
    public void setKeybind(int keybind) { this.keybind = keybind; }
}
