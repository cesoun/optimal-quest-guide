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

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class Guide {
    @Getter
    @Setter
    private Activity[] activities;

    @Getter
    @Setter
    private HashMap<String, Activity> activityMap;

    @Getter
    @Setter
    private boolean didError;

    @Getter
    @Setter
    private boolean isReady;

    /**
     * Set up a new Guide with default initialization.
     */
    private Guide() {
        this.activities = new Activity[0];
        this.activityMap = new HashMap<>();

        this.didError = false;
        this.isReady = false;
    }

    /**
     * Set up a new Guide with an error value
     *
     * @param didError boolean
     */
    public Guide(boolean didError) {
        this();

        this.didError = didError;
    }

    /**
     * Set up a new Guide with activities.
     * Will also populate the activityMap.
     *
     * @param activities Activity[] of Activities
     */
    public Guide(Activity[] activities) {
        this();

        // Set activity and populate map for lookup later.
        this.activities = activities;
        for (Activity act : activities) {
            this.activityMap.put(act.Name, act);
        }

        this.isReady = true;
    }
}
