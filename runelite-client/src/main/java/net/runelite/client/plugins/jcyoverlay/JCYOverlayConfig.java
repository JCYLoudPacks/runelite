/*
 * Copyright (c) 2018 Abex
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
package net.runelite.client.plugins.jcyoverlay;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

import java.awt.*;

@ConfigGroup("jcyoverlay")
public interface JCYOverlayConfig extends Config
{
	@ConfigItem(
			keyName = "showPotions",
			name = "Enable Potion Overlay",
			description = "Highlight potions when they should be sipped",
			position = 1
	)
	default boolean showPotions()
	{
		return true;
	}

	@ConfigItem(
		keyName = "colorPotion",
		name = "Potion Color",
		description = "Color to show when potion should be sipped",
		position = 2
	)
	default Color colorPotion()
	{
		return new Color(0xEE3333);
	}

	@ConfigItem(
			keyName = "overloadThreshold",
			name = "Overload Boost Threshold",
			description = "Boost to trigger overlay for overload",
			position = 3
	)
	default int overloadThresh()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "combatThreshold",
			name = "Combat Boost Threshold",
			description = "Boost to trigger overlay for combat potion",
			position = 4
	)
	default int combatThresh()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "rangeThreshold",
			name = "Super Range Boost Threshold",
			description = "Boost to trigger overlay for super range potion",
			position = 5
	)
	default int rangeThresh()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "mageThreshold",
			name = "Super Magic Boost Threshold",
			description = "Boost to trigger overlay for super magic potion",
			position = 6
	)
	default int mageThresh()
	{
		return 5;
	}

	@ConfigItem(
			keyName = "showOutOfCmb",
			name = "Out of Combat Overlay",
			description = "Enable the out of combat overlay",
			position = 7
	)
	default boolean showOutOfCmb()
	{
		return true;
	}

	@ConfigItem(
			keyName = "colorCombat",
			name = "Combat Overlay Color",
			description = "Color for the out of combat overlay",
			position = 8
	)
	default Color colorCombat()
	{
		return new Color(0xEE3333);
	}

	@ConfigItem(
			keyName = "frameTitle",
			name = "Custom Frame Title",
			description = "Title to display on runelite frame",
			position = 9
	)
	default String frameTitle() { return new String("JCY RuneLite"); }
}
