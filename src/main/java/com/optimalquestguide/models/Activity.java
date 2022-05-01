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
package com.optimalquestguide.models;

import com.optimalquestguide.Panels.GuidePanel;
import lombok.ToString;
import net.runelite.api.Skill;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.util.ImageUtil;

import java.awt.image.BufferedImage;

@ToString
public class Activity {
    public int Index;
    public int QuestPoints;
    public int TotalQuestPoints;

    public boolean IsTask;
    public boolean HasQuickGuide;

    public String Name;
    public String GuideURL;
    public String Location;

    public Reward[] Rewards;

    public Requirement[] Requirements;

    /**
     * Get icon for Reward
     *
     * @param rew Reward to find the icon for
     * @return BufferedImage icon for Reward
     */
    public BufferedImage getIconForReward(Reward rew) {
        return getIconForSkill(rew.Skill);
    }

    /**
     * Get icon for Requirement
     *
     * @param req Requirement to find the icon for
     * @return BufferedImage icon for Requirement
     */
    public BufferedImage getIconForRequirement(Requirement req) {
        return getIconForSkill(req.Skill);
    }

    /**
     * Returns a BufferedImage from the given Skill string.
     *
     * @param skill Skill to look for. Includes 'quest points' and 'combat level'
     * @return BufferedImage icon for Skill
     */
    private BufferedImage getIconForSkill(String skill) {
        if (skill.equalsIgnoreCase("quest points")) {
            return ImageUtil.loadImageResource(GuidePanel.class, "/quest_point.png");
        } else if (skill.equalsIgnoreCase("combat level")) {
            return ImageUtil.loadImageResource(GuidePanel.class, "/combat_level.png");
        }

        return new SkillIconManager().getSkillImage(Skill.valueOf(skill.toUpperCase()), true);
    }
}
