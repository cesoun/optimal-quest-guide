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

        // Create task panel & layouts
        setLayout(new GridBagLayout());

        // Set constraints to center items.s
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 5;

        setBackground(ColorScheme.DARKER_GRAY_HOVER_COLOR);

        // Icon & Level
        Requirement req = activity.Requirements[0];

        JLabel skillIcon = new JLabel(new ImageIcon(activity.getIconForRequirement(req)));
        JLabel skillLevel = new JLabel(Integer.toString(req.Level));

        skillIcon.setPreferredSize(new Dimension(25, 25));
        skillLevel.setForeground(config.getRequirementUnmetColor());

        // Add to task panel
        add(skillIcon, c);
        add(skillLevel, c);
    }
}
