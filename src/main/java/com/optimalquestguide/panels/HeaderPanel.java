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

import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.components.shadowlabel.JShadowedLabel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HeaderPanel extends JPanel {
    private final JLabel title;
    private final JLabel description;

    public HeaderPanel() {
        title = new JShadowedLabel();
        description = new JShadowedLabel();

        setBorder(new EmptyBorder(5, 10, 10, 10));
        setLayout(new BorderLayout());

        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        description.setForeground(Color.GRAY);
        description.setFont(FontManager.getRunescapeSmallFont());
        description.setHorizontalAlignment(SwingConstants.CENTER);

        add(title, BorderLayout.NORTH);
        add(description, BorderLayout.CENTER);
    }

    /**
     * Construct a Header Panel with a title and description.
     * @param title String
     * @param description String
     */
    public HeaderPanel(String title, String description) {
        this();
        this.setContent(title, description);
    }

    /**
     * Set the title and description on the panel.
     * @param title String
     * @param description String
     */
    public void setContent(String title, String description) {
        this.title.setText(title);
        this.description.setText(description);
    }

    /**
     * Set the title on the panel.
     * @param title String
     */
    public void setTitle(String title) {
        this.title.setText(title);
    }

    /**
     * Set the description on the panel.
     * @param description String
     */
    public void setDescription(String description) {
        this.description.setText(description);
    }
}
