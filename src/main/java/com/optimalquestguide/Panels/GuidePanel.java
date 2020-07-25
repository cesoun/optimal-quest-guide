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
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import java.awt.*;
import java.util.HashMap;

@Slf4j
public class GuidePanel extends PluginPanel {

    private final Client c;
    private final GuideConfig config;

    private final ErrorPanel ePanel = new ErrorPanel();
    private final HashMap<String, QuestPanel> qMap = new HashMap<>();

    @Inject
    public GuidePanel(Client c, GuideConfig config, QuestInfo[] infos) {
        super();

        this.c = c;
        this.config = config;

        setLayout(new CollapsingGridLayout(infos.length + 1, 1, 0, 2));

        ePanel.setContent("Optimal Quest Guide", "Quests will adjust after login.");
        add(ePanel);

        // TODO: Add a search bar and functionality for filtering quests.

        // Save the object to a HashMap because we want need to build all panels.
        for (QuestInfo info : infos) {
            QuestPanel qPanel = new QuestPanel(c, config, info);
            qMap.put(info.getName(), qPanel);
            add(qPanel);
        }
    }

    public void updateQuests(QuestInfo[] infos) {
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

        int count = 0;
        for (Component c : this.getComponents()) {
            if (c.isVisible())
                count++;
        }

        if (count == 1) {
            ePanel.setContent("Optimal Quest Guide", "Optimal Quest Guide Completed!");
        } else {
            ePanel.setContent("Optimal Quest Guide", "Happy questing.");
        }

        revalidate();
    }
}
