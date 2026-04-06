package com.ryo.client.gui;

import com.ryo.client.module.Module;
import com.ryo.client.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClickGUI extends Screen {
    public ClickGUI() {
        super(Text.literal("RyoClient GUI"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta); 
        
        int x = 50;
        int y = 50;
        int width = 200;
        
        // Vẽ khung nền trong suốt mờ
        context.fill(x, y, x + width, y + 250, 0xAA000000);
        context.fill(x, y, x + width, y + 25, 0xFF0055AA); // Header
        context.drawText(this.textRenderer, "RyoClient Modules", x + 10, y + 8, 0xFFFFFF, true);

        // Render các module dưới dạng nút Toggle
        int modY = y + 35;
        for (Module m : ModuleManager.INSTANCE.getModules()) {
            boolean isHovered = mouseX >= x + 10 && mouseX <= x + width - 10 && mouseY >= modY && mouseY <= modY + 15;
            int bgColor = isHovered ? 0x55FFFFFF : 0x22FFFFFF;
            
            context.fill(x + 10, modY, x + width - 10, modY + 15, bgColor);
            int textColor = m.isToggled() ? 0x00FF00 : 0xFF5555;
            context.drawText(this.textRenderer, m.getName(), x + 15, modY + 4, textColor, true);
            
            modY += 20;
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Chuột trái
            int x = 50;
            int modY = 85; // Bắt đầu từ 50 + 35
            for (Module m : ModuleManager.INSTANCE.getModules()) {
                if (mouseX >= x + 10 && mouseX <= x + 190 && mouseY >= modY && mouseY <= modY + 15) {
                    m.toggle();
                    return true;
                }
                modY += 20;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
}
