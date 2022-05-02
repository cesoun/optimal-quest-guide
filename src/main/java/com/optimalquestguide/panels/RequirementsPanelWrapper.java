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
import com.optimalquestguide.models.Requirement;
import net.runelite.api.Client;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.DynamicGridLayout;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;

public class RequirementsPanelWrapper extends JPanel {

    private Client client;

    private GuideConfig config;

    private Activity activity;

    public RequirementsPanelWrapper(Client client, GuideConfig config, Activity activity) {
        this.client = client;
        this.config = config;
        this.activity = activity;

        Requirement[] requirements = activity.Requirements;

        setBackground(ColorScheme.DARKER_GRAY_COLOR);
        setLayout(new DynamicGridLayout(0, Math.min(requirements.length, 4), 1, 1));

        for (int i = 0; i < requirements.length; i++) {
            // Add the requirement with the constraint
            Requirement req = requirements[i];

            JLabel icon = new JLabel(new ImageIcon(activity.getIconForRequirement(req)));
            JLabel level = new JShadowedLabel(Integer.toString(req.Level));

            SkillPanel reqPanel = new SkillPanel(icon, level, ColorScheme.DARKER_GRAY_COLOR, config.getCompletedColor());
            add(reqPanel);
        }
    }
}
