/*
 * Copyright (c) 2022, Christopher Oswald <https://github.com/cesoun>
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
package com.optimalquestguide.panels;

import com.optimalquestguide.GuidePlugin;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.PluginPanel;
import net.runelite.client.ui.components.IconTextField;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GuidePanel extends PluginPanel {

    private GuidePlugin plugin;

    private HeaderPanel header;

    private IconTextField search;

    private ActivityPanelWrapper activityWrapper;

    public GuidePanel(GuidePlugin plugin) {
        super();

        this.plugin = plugin;

        setLayout(new DynamicGridLayout(3, 1, 0, 2));

        // Header
        header = new HeaderPanel("Optimal Quest Guide", "Quests will adjust after login");
        add(header);

        // Search bar
        search = new IconTextField();

        search.setPreferredSize(new Dimension(PluginPanel.PANEL_WIDTH - 20, 30));
        search.setIcon(IconTextField.Icon.SEARCH);
        search.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        search.setHoverBackgroundColor(ColorScheme.DARKER_GRAY_HOVER_COLOR);

        search.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onSearchChanged();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onSearchChanged();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onSearchChanged();
            }
        });
        add(search);

        // Activities (Quests+Tasks)
        activityWrapper = new ActivityPanelWrapper(plugin);
        add(activityWrapper);
    }

    private void onSearchChanged() {
        String text = search.getText();

        // TODO: Filtering
    }

    // TODO: Updating, etc...
}
