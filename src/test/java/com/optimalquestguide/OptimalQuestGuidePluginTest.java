package com.optimalquestguide;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

@Slf4j
public class OptimalQuestGuidePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(GuidePlugin.class);
		RuneLite.main(args);
	}
}