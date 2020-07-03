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

import com.optimalquestguide.QuestInfo;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.QuestState;
import net.runelite.client.ui.PluginPanel;

import javax.inject.Inject;
import java.util.HashMap;

@Slf4j
public class GuidePanel extends PluginPanel {

    private ErrorPanel ePanel = new ErrorPanel();
    private HashMap<String, QuestPanel> qMap = new HashMap<>();

    @Inject
    public GuidePanel(QuestInfo[] infos) {
        super();

        ePanel.setContent("Optimal Quest Guide", "Quests will adjust after login.");
        add(ePanel);


        // Save the object to a HashMap because we want need to build all panels.
        for (QuestInfo info : infos) {
            QuestPanel qPanel = new QuestPanel(info);
            qMap.put(info.getName(), qPanel);
            add(qPanel);
        }
    }

    public void updateQuests(QuestInfo[] infos) {
        removeAll();

        // Set the header.
        ePanel.setContent("Optimal Quest Guide", "Happy questing.");
        add(ePanel);

        // Update the panels.
        int pCount = 0;
        for (QuestInfo info : infos) {
            QuestPanel qPanel = qMap.get(info.getName());
            if (qPanel == null) return;

            qPanel.update(info);

            if (info.getQuestState() == QuestState.NOT_STARTED || info.getQuestState() == QuestState.IN_PROGRESS) {
                add(qPanel);
                ++pCount;
            }
        }

        // If there were no quests then just show the message.
        if (pCount == 0) {
            remove(ePanel);
            ePanel.setContent("Optimal Quest Guide", "Optimal Quest Guide completed!");
            add(ePanel);
        }

        revalidate();
    }
}
