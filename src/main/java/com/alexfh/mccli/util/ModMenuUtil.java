package com.alexfh.mccli.util;

import com.alexfh.mccli.mixin.ConfigScreenFactoriesAccessor;
import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.util.mod.Mod;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuUtil
{

    public static List<String> getModMenuConfigNames()
    {
        return ConfigScreenFactoriesAccessor.getConfigScreenFactories().keySet().stream()
            .filter(ModMenu.MODS::containsKey).map(modID -> ModMenu.MODS.get(modID).getName())
            .collect(Collectors.toList());
    }

    public static boolean openConfigScreenFromModName(String modName)
    {
        Minecraft minecraftClient = Minecraft.getInstance();
        Map.Entry<String, Mod> modIDEntry = ModMenu.MODS.entrySet().stream()
            .filter(entry -> entry.getValue().getName().equals(modName)).findFirst().orElse(null);
        if (modIDEntry == null)
        {
            return false;
        }
        String modID = modIDEntry.getKey();
        Screen configScreen = ModMenu.getConfigScreen(modID, minecraftClient.gui.screen());
        if (configScreen == null)
        {
            return false;
        }
        minecraftClient.gui.setScreen(configScreen);
        return true;
    }

}
