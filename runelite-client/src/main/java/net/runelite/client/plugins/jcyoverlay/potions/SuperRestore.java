/*
 * Copyright (c) 2016-2018, Adam <Adam@sigterm.info>
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
package net.runelite.client.plugins.jcyoverlay.potions;

import lombok.RequiredArgsConstructor;
import net.runelite.api.Client;
import net.runelite.client.plugins.jcyoverlay.Effect;
import net.runelite.client.plugins.jcyoverlay.SimpleStatBoost;
import net.runelite.client.plugins.jcyoverlay.StatChange;
import net.runelite.client.plugins.jcyoverlay.StatsChanges;
import net.runelite.client.plugins.jcyoverlay.stats.Stat;

import java.util.Comparator;
import java.util.stream.Stream;

import static net.runelite.client.plugins.jcyoverlay.Builders.perc;
import static net.runelite.client.plugins.jcyoverlay.stats.Stats.*;

@RequiredArgsConstructor
public class SuperRestore implements Effect
{
	private static final Stat[] superRestoreStats = new Stat[]
	{
		ATTACK, DEFENCE, STRENGTH, RANGED, MAGIC, COOKING,
		WOODCUTTING, FLETCHING, FISHING, FIREMAKING, CRAFTING, SMITHING, MINING,
		HERBLORE, AGILITY, THIEVING, SLAYER, FARMING, RUNECRAFT, HUNTER,
		CONSTRUCTION
	};

	private final int delta;

	@Override
	public StatsChanges calculate(Client client)
	{
		StatsChanges changes = new StatsChanges(0);

		SimpleStatBoost calc = new SimpleStatBoost(null, false, perc(.25, delta));
		PrayerPotion prayer = new PrayerPotion(delta);
		changes.setStatChanges(Stream.concat(
			Stream.of(prayer.effect(client)),
			Stream.of(superRestoreStats)
				.filter(stat -> stat.getValue(client) < stat.getMaximum(client))
				.map(stat ->
				{
					calc.setStat(stat);
					return calc.effect(client);
				})
			).toArray(StatChange[]::new));
		changes.setOverlayColor(Stream.of(changes.getStatChanges()).map(sc -> sc.getOverlayColor()).max(Comparator.comparing(Enum::ordinal)).get());
		return changes;
	}

}
