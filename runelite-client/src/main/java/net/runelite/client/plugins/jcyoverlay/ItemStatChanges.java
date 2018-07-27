/*
 * Copyright (c) 2016-2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Jordan Atwood <jordan.atwood423@gmail.com>
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

import com.google.inject.Singleton;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static net.runelite.api.ItemID.*;
import static net.runelite.client.plugins.jcyoverlay.Builders.*;
import static net.runelite.client.plugins.jcyoverlay.stats.Stats.*;

@Singleton
@Slf4j
public class ItemStatChanges
{

	ItemStatChanges()
	{
		init();
	}

	private void init()
	{

		// JCY Supported Potions
		add(combo(2, boost(ATTACK, perc(.10, 3)), boost(STRENGTH, perc(.10, 3))), COMBAT_POTION1, COMBAT_POTION2, COMBAT_POTION3, COMBAT_POTION4);
		add(boost(RANGED, perc(.15, 5)), SUPER_RANGING_1, SUPER_RANGING_2, SUPER_RANGING_3, SUPER_RANGING_4);
		add(boost(MAGIC, perc(.15, 5)), SUPER_MAGIC_POTION_1, SUPER_MAGIC_POTION_2, SUPER_MAGIC_POTION_3, SUPER_MAGIC_POTION_4);
		add(combo(5, boost(ATTACK, perc(.15, 5)), boost(STRENGTH, perc(.15, 5)), boost(DEFENCE, perc(.15, 5)), boost(RANGED, perc(.15, 5)), boost(MAGIC, perc(.15, 5)), heal(HITPOINTS, -50)), OVERLOAD_1, OVERLOAD_2, OVERLOAD_3, OVERLOAD_4);


		log.debug("{} items; {} behaviours loaded", effects.size(), new HashSet<>(effects.values()).size());
	}

	private final Map<Integer, Effect> effects = new HashMap<>();

	private void add(Effect effect, int... items)
	{
		assert items.length > 0;
		for (int item : items)
		{
			Effect prev = effects.put(item, effect);
			assert prev == null : "Item already added";
		}
	}

	public Effect get(int id)
	{
		return effects.get(id);
	}

}
