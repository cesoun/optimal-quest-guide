/*
 * Copyright (c) 2020, Christopher Oswald <https://github.com/cesoun>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.optimalquestguide;

import com.google.gson.Gson;
import com.google.inject.Provides;
import com.optimalquestguide.Panels.GuidePanel;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Quest;
import net.runelite.api.events.GameTick;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
@PluginDescriptor(
        name = "Optimal Quest Guide",
        description = "I wanted this so now you get this as well. OSRS Wiki Optimal Quest Guide.",
        tags = {"quest", "guide", "optimal"},
        loadWhenOutdated = true
)
public class GuidePlugin extends Plugin {

    @Inject
    private Client c;

    @Inject
    private GuideConfig config;

    @Inject
    private ClientThread cThread;

    @Inject
    private ClientToolbar cToolbar;

    private NavigationButton nBtn;
    private GuidePanel gPanel;
    private QuestInfo[] infos;

    @Override
    protected void startUp() throws Exception {
        // Parse the quests.json to be loaded into the panel.
        InputStream questDataFile = GuidePlugin.class.getResourceAsStream("/quests.json");
        infos = new Gson().fromJson(new InputStreamReader(questDataFile), QuestInfo[].class);

        gPanel = new GuidePanel(c, config, infos);

        // Setup the icon.
        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/panel_icon.png");

        // Build the navigation button that shows on the sidebar.
        nBtn = NavigationButton.builder()
                .tooltip("Optimal Quest Guide")
                .icon(icon)
                .priority(7)
                .panel(gPanel)
                .build();

        // Add the button to the sidebar.
        cToolbar.addNavigation(nBtn);
    }

    @Override
    protected void shutDown() throws Exception {
        cToolbar.removeNavigation(nBtn);
    }

    @Provides
    GuideConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(GuideConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged e) {
        // Check the group firing the event & if we are logged in.
        if (!e.getGroup().equalsIgnoreCase("optimal-quest-guide")) return;
        if (!c.getGameState().equals(GameState.LOGGED_IN)) return;

        cThread.invokeLater(this::updateQuestList);
    }

    @Subscribe
    public void onGameTick(GameTick e) {
        /*
            Replacing onWidgetLoaded with onGameTick should streamline the panel updates better.

            There were some occasions where the quest dialog gets 1 ticked and closed without updating the panel.

            If this turns out to be to heavy a task within the GameTick i'll like revert it back and look for another
                solution that is potentially more lightweight.
         */
        updateQuestList();
    }

    private void updateQuestList() {
        for (Quest quest : Quest.values()) {
            for (QuestInfo info : infos) {
                if (quest.getName().equalsIgnoreCase(info.getName())) {
                    info.setWidget(quest);
                    info.setQuestState(quest.getState(c));
                    break;
                }
            }
        }

        gPanel.updateQuests(infos);
    }
}
