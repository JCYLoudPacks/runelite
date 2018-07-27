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
package net.runelite.client.plugins.jcyoverlay;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Client;
import net.runelite.client.plugins.jcyoverlay.stats.Stat;

public abstract class StatBoost extends SingleEffect
{
	@Getter
	@Setter
	private Stat stat;
	@Getter
	@Setter
	private boolean boost;

	public StatBoost(Stat stat, boolean boost)
	{
		this.stat = stat;
		this.boost = boost;
	}

	public abstract int heals(Client client);

	@Override
	public StatChange effect(Client client)
	{
		int value = stat.getValue(client);
		int max = stat.getMaximum(client);

		boolean hitCap = false;

		int calcedDelta = heals(client);
		if (boost && calcedDelta > 0)
		{
			max += calcedDelta;
		}
		if (value > max)
		{
			max = value;
		}
		int newValue = value + calcedDelta;
		if (newValue > max)
		{
			newValue = max;
			hitCap = true;
		}
		if (newValue < 0)
		{
			newValue = 0;
		}
		int delta = newValue - value;
		StatChange out = new StatChange();
		out.setStat(stat);
		if (delta > 0)
		{
			out.setOverlayColor(hitCap ? OverlayColor.POTION : OverlayColor.POTION);
		}
		else if (delta == 0)
		{
			out.setOverlayColor(OverlayColor.POTION);
		}
		else
		{
			out.setOverlayColor(OverlayColor.POTION);
		}
		out.setAbsolute(Integer.toString(newValue));
		out.setRelative(String.format("%+d", delta));
		out.setTheoretical(String.format("%+d", calcedDelta));
		return out;
	}

}
