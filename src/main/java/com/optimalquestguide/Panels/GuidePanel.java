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
package com.optimalquestguide.Panels;

import com.optimalquestguide.GuideConfig;
import com.optimalquestguide.Layouts.CollapsingGridLayout;
import com.optimalquestguide.QuestInfo;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.QuestState;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;

@Slf4j
public class GuidePanel extends PluginPanel {

    private final Client c;
    private final GuideConfig config;

    private final ErrorPanel ePanel = new ErrorPanel();
    private final IconTextField searchBar = new IconTextField();
    private final HashMap<String, QuestPanel> qMap = new HashMap<>();
    private final HashMap<String, QuestPanel> searchMap = new HashMap<>();

    @Inject
    public GuidePanel(Client c, GuideConfig config, QuestInfo[] infos) {
        super();

        this.c = c;
        this.config = config;

        setLayout(new CollapsingGridLayout(infos.length + 2, 1, 0, 2));

        ePanel.setContent("Optimal Quest Guide", "Quests will adjust after login.");
        add(ePanel);

        // Search Bar for filtering quests.
        searchBar.setIcon(IconTextField.Icon.SEARCH);
        searchBar.setPreferredSize(new Dimension(PluginPanel.WIDTH - 10, 30));
        searchBar.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        searchBar.setHoverBackgroundColor(ColorScheme.DARK_GRAY_HOVER_COLOR);
        searchBar.addClearListener(this::onSearch);
        searchBar.addKeyListener(e -> onSearch());

        add(searchBar);

        // Save the object to a HashMap because we want need to build all panels.
        for (QuestInfo info : infos) {
            QuestPanel qPanel = new QuestPanel(c, config, info);
            qMap.put(info.getName(), qPanel);
            add(qPanel);
        }
    }

    private void onSearch() {
        if (config.searchCompletedQuests() || searchMap.isEmpty()) {
            qMap.forEach((quest, panel) -> {
                panel.setVisible(quest.toLowerCase().contains(searchBar.getText().toLowerCase()));
                revalidate();
            });
        } else {
            searchMap.forEach((quest, panel) -> {
                panel.setVisible(quest.toLowerCase().contains(searchBar.getText().toLowerCase()));
                revalidate();
            });
        }
    }

    public void updateQuests(QuestInfo[] infos) {
        // Prevent updates while the search bar contains text.
        if (!searchBar.getText().isEmpty()) return;

        // Update panels.
        for (QuestInfo info : infos) {
            QuestPanel qPanel = qMap.get(info.getName());
            if (qPanel == null) continue;

            qPanel.update(info);

            if (config.showCompletedQuests()) { // Display all.
                if (qPanel.isVisible()) continue;

                qPanel.setVisible(true);
            } else if (info.getQuestState() == QuestState.NOT_STARTED || info.getQuestState() == QuestState.IN_PROGRESS) { // Display only not completed.
                if (qPanel.isVisible()) continue;

                qPanel.setVisible(true);
            } else { // Remove from the panel if it's not showing all and its finished.
                if (!qPanel.isVisible()) continue;

                qPanel.setVisible(false);
            }
        }

        // Clear the search map before updating.
        searchMap.clear();

        for (Component c : this.getComponents()) {
            if (!c.isVisible()) continue;

            if (c instanceof QuestPanel) {
                QuestPanel qPanel = (QuestPanel) c;
                searchMap.put(qPanel.getQuest().getName(), qPanel);
            }
        }

        ePanel.setContent("Optimal Quest Guide", "Happy questing.");

        revalidate();
    }
}
