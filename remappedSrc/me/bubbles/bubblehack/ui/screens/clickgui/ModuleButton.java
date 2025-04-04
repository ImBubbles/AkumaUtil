package me.bubbles.bubblemod.ui.screens.clickgui;

import me.bubbles.bubblemod.module.Mod;
import me.bubbles.bubblemod.module.settings.BooleanSetting;
import me.bubbles.bubblemod.module.settings.ModeSetting;
import me.bubbles.bubblemod.module.settings.NumberSetting;
import me.bubbles.bubblemod.module.settings.Setting;
import me.bubbles.bubblemod.ui.screens.clickgui.setting.CheckBox;
import me.bubbles.bubblemod.ui.screens.clickgui.setting.Component;
import me.bubbles.bubblemod.ui.screens.clickgui.setting.ModeBox;
import me.bubbles.bubblemod.ui.screens.clickgui.setting.Slider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton {

    public Mod module;
    public Frame parent;
    public int offset;
    public List<Component> components;
    public boolean extended;

    private final MinecraftClient mc = MinecraftClient.getInstance();

    public ModuleButton(Mod module, Frame parent, int offset) {
        this.module=module;
        this.parent=parent;
        this.offset=offset;
        this.extended=false;
        this.components=new ArrayList<>();

        int setOffset=parent.height;
        for(Setting setting : module.getSettings()) {
            if(setting instanceof BooleanSetting) {
                components.add(new CheckBox(setting, this, setOffset));
            } else if(setting instanceof ModeSetting) {
                components.add(new ModeBox(setting, this, setOffset));
            } else if(setting instanceof NumberSetting) {
                components.add(new Slider(setting, this, setOffset));
            }
            setOffset+=parent.height;
        }
    }

    public void render(MatrixStack matrices, double mouseX, double mouseY, float delta) {

        DrawableHelper.fill(matrices,parent.x,parent.y+offset,parent.x+parent.width,parent.y+offset+parent.height, module.isEnabled() ? new Color(0,255,255,120).getRGB() : new Color(0,0,0,120).getRGB());
        if(isHovered(mouseX,mouseY)) {
            DrawableHelper.fill(matrices,parent.x,parent.y+offset,parent.x+parent.width,parent.y+offset+parent.height, module.isEnabled() ? new Color(0,0,0,120).getRGB() : new Color(0,255,255,120).getRGB());
        }

        int textOffset = ((parent.height/2)-mc.textRenderer.fontHeight/2);

        parent.mc.textRenderer.drawWithShadow(matrices,module.getName(),parent.x+textOffset,parent.y+offset+textOffset, Color.WHITE.getRGB());

        if(extended) {
            for(Component component : components) {
                component.render(matrices, mouseX, mouseY, delta);
            }
        }

    }

    public void mouseClicked(double mouseX, double mouseY, int button) {
        if(isHovered(mouseX,mouseY)) {
            if(button==0) {
                module.toggle();
            } else if (button==1) {
                extended=!extended;
                parent.updateButtons();
            }
        }

        for(Component component : components) {
            component.mouseClicked(mouseX, mouseY, button);
        }

    }

    public void mouseReleased(double mouseX, double mouseY, int button) {
        for(Component component : components) {
            component.mouseReleased(mouseX, mouseY, button);
        }
    }

    public boolean isHovered(double mouseX, double mouseY) {
        return mouseX > parent.x && mouseX < parent.x+parent.width && mouseY > parent.y + offset && mouseY < parent.y + offset + parent.height;
    }

}
