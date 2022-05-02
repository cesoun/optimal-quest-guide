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
package com.optimalquestguide.utils;

import com.google.gson.Gson;
import com.optimalquestguide.GuidePlugin;
import com.optimalquestguide.models.Activity;
import com.optimalquestguide.models.Guide;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class DataLoader {
    public static Guide loadJSON() {
        log.debug("loading quests.json");

        // Attempt to load quests.
        try (InputStream questFile = GuidePlugin.class.getResourceAsStream("/quests.json")) {
            // If we didn't load anything, debug error and return.
            if (questFile == null) {
                log.warn("quest.json was null");
                return new Guide(true);
            }

            // Load the activities.
            Activity[] activities = new Gson().fromJson(new InputStreamReader(questFile), Activity[].class);

            log.debug("successfully loaded quests.json");
            return new Guide(activities);
        } catch (IOException ex) {
            log.error("failed to load quests.json with exception: {}", ex.toString());
            return new Guide(true);
        }
    }
}
