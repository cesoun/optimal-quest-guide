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
import net.runelite.client.ui.ColorScheme;

import javax.swing.*;
import java.awt.*;

public class TaskPanel extends JPanel {

    public TaskPanel(GuideConfig config, Activity activity) {
        setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);
        setLayout(new GridBagLayout());

        // Set constraints to center items.s
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 5;

        // Icon & Level
        Requirement req = activity.Requirements[0];

        JLabel skillIcon = new JLabel(new ImageIcon(activity.getIconForRequirement(req)));
        JLabel skillLevel = new JLabel(Integer.toString(req.Level));

        skillIcon.setPreferredSize(new Dimension(25, 25));
        skillLevel.setForeground(config.getRequirementUnmetColor());

        add(skillIcon, c);
        add(skillLevel, c);
    }
}
