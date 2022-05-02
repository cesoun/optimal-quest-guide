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

import com.optimalquestguide.GuideConfig;
import com.optimalquestguide.models.Activity;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ActivityPanel extends JPanel {

    private Client client;

    private GuideConfig config;

    private Activity activity;

    public ActivityPanel(Client client, GuideConfig config, Activity activity) {
        this.client = client;
        this.config = config;
        this.activity = activity;

        // Set layout, border and background.
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(5, 5, 5, 5));
        setBackground(ColorScheme.DARKER_GRAY_COLOR);

        // Setup constraints.
        // TODO: Determine if we can better calculate ipadx
        GridBagConstraints c = new GridBagConstraints();
        c.ipadx = 135;
        c.ipady = 10;

        // Label
        JLabel label = new JShadowedLabel();
        label.setFont(FontManager.getRunescapeBoldFont());
        label.setHorizontalAlignment(SwingConstants.CENTER);

        // Centering text with html since some activities are extra long.
        String text = String.format("<html><body style='text-align: center;'><p>%s</p></body></html>", activity.Name);

        label.setText(text);
        add(label, c);

        // Increment gridx
        c.gridx++;

        // Task Icons
        if (activity.IsTask) {
            setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
            TaskPanel task = new TaskPanel(config, activity);
            add(task, c);
        } else {
            // TODO: Add Requirements
            RequirementsPanel requirementsPanel = new RequirementsPanel(client, config, activity);
            add(requirementsPanel, c);

            // TODO: Add Rewards config opt.
            // Check config setting to determine render
            RewardsPanel rewardsPanel = new RewardsPanel(config, activity);
            add(rewardsPanel);
        }
        /*
            ****************************
           |        Activity Name
           |     Req Req Req Req ...
           |     Req Req Req Req ...
            ****************************
         */
    }
}
