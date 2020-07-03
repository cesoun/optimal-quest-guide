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
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.util.LinkBrowser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j
public class QuestPanel extends JPanel {

    private JLabel qLabel;

    public QuestPanel(QuestInfo quest) {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(5, 15, 0, 15));

        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        container.setBackground(ColorScheme.DARKER_GRAY_COLOR);

        // Menu Item(s)
        JMenuItem openWiki = new JMenuItem("Open Wiki Guide");
        openWiki.addActionListener(e -> LinkBrowser.browse(quest.getUri()));

        // Popup Menu
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.setBorder(new EmptyBorder(5, 5, 5, 5));
        popupMenu.add(openWiki);

        qLabel = new JLabel();
        qLabel.setText(quest.getName());
        qLabel.setBorder(new EmptyBorder(10, 2, 10, 2));
        qLabel.setHorizontalAlignment(SwingConstants.CENTER);
        qLabel.setVerticalAlignment(SwingConstants.CENTER);

        container.add(qLabel, BorderLayout.NORTH);

        container.setComponentPopupMenu(popupMenu);

        add(container, BorderLayout.NORTH);
    }

    public void update(QuestInfo quest) {
        SwingUtilities.invokeLater(() -> {
            if (quest == null) return;
            if (quest.getQuestState() == null) return;

            if (quest.getQuestState() == QuestState.IN_PROGRESS)
                qLabel.setForeground(ColorScheme.GRAND_EXCHANGE_ALCH);
        });
    }
}
