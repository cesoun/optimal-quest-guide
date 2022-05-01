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
            // Add the requirements here.

            // TODO: Add Rewards config opt.
            // Add the rewards?
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
