package com.optimalquestguide;

import com.optimalquestguide.models.Activity;
import com.optimalquestguide.models.Guide;
import com.optimalquestguide.utils.DataLoader;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataLoaderTest {

    @Test
    public void shouldLoadJSON() {
        Guide guide = DataLoader.loadJSON();

        assertFalse(guide.isDidError());
        assertTrue(guide.isReady());

        Activity[] activities = guide.getActivities();
        assertTrue(activities.length != 0);

        HashMap<String, Activity> activityMap = guide.getActivityMap();
        assertTrue(activityMap.containsKey("Tutorial Island"));
    }
}
