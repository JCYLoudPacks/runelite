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

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.plugins.jcyoverlay.stats.Stats;
import net.runelite.client.ui.overlay.*;
import net.runelite.client.ui.overlay.tooltip.Tooltip;
import net.runelite.client.ui.overlay.tooltip.TooltipManager;
import net.runelite.client.util.QueryRunner;

import java.awt.*;
import java.util.Arrays;
import java.util.NoSuchElementException;

public class JCYOverlay extends Overlay
{

	private final QueryRunner queryRunner;
	private final Client client;
	private final ItemStatChanges statChanges;
	private final JCYOverlayConfig config;

	private long lastFlash = 0;
	private boolean lowRender = true;

	@Inject
	private JCYOverlay(QueryRunner queryRunner, Client client, ItemStatChanges statChanges, JCYOverlayConfig config) {
		this.queryRunner = queryRunner;
		this.client = client;
		this.statChanges = statChanges;
		this.config = config;

		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.LOW);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		System.out.println("CONS");
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (client.isMenuOpen() || (!config.showPotions() && !config.showOutOfCmb()))
		{
			return null;
		}

		if(System.currentTimeMillis() - lastFlash >= 500) {
			lowRender = !lowRender;
			lastFlash = System.currentTimeMillis();
		}

			WidgetItem[] inventory = queryRunner.runQuery(new InventoryWidgetItemQuery());
			for (WidgetItem item : inventory) {
				Effect change = statChanges.get(item.getId());
				if (change != null) {
					StatsChanges statsChanges = change.calculate(client);
					try {
						PotionType potionType = PotionType.get(item.getId());
						switch (potionType) {
							case COMBAT: {
								StatChange sc = Arrays.asList(statsChanges.getStatChanges()).stream().filter(c -> c.getStat().equals(Stats.ATTACK)).findFirst().get();
								int realBoost = Integer.parseInt(sc.getRelative().replace("+", ""));
								if (realBoost >= config.combatThresh()) {
									Color c = config.colorPotion();
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 160 : 255));
									graphics.draw(item.getCanvasBounds());
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 40 : 80));
									graphics.fill(item.getCanvasBounds());
								}
							}
							break;
							case OVERLOAD: {
								StatChange sc = Arrays.asList(statsChanges.getStatChanges()).stream().filter(c -> c.getStat().equals(Stats.ATTACK)).findFirst().get();
								int realBoost = Integer.parseInt(sc.getRelative().replace("+", ""));
								if (realBoost >= config.overloadThresh()) {
									Color c = config.colorPotion();
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 160 : 255));
									graphics.draw(item.getCanvasBounds());
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 40 : 80));
									graphics.fill(item.getCanvasBounds());
								}
							}
							break;
							case SUPER_MAGIC: {
								StatChange sc = Arrays.asList(statsChanges.getStatChanges()).stream().filter(c -> c.getStat().equals(Stats.MAGIC)).findFirst().get();
								int realBoost = Integer.parseInt(sc.getRelative().replace("+", ""));
								if (realBoost >= config.mageThresh()) {
									Color c = config.colorPotion();
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 160 : 255));
									graphics.draw(item.getCanvasBounds());
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 40 : 80));
									graphics.fill(item.getCanvasBounds());
								}
							}
							break;
							case SUPER_RANGE: {
								StatChange sc = Arrays.asList(statsChanges.getStatChanges()).stream().filter(c -> c.getStat().equals(Stats.RANGED)).findFirst().get();
								int realBoost = Integer.parseInt(sc.getRelative().replace("+", ""));
								if (realBoost >= config.rangeThresh()) {
									Color c = config.colorPotion();
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 160 : 255));
									graphics.draw(item.getCanvasBounds());
									graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 40 : 80));
									graphics.fill(item.getCanvasBounds());
								}
							}
							break;
						}
					} catch (NoSuchElementException e) {
						e.printStackTrace();
					}
				}
			}

			if(config.showOutOfCmb() && client.getLocalPlayer().getInteracting() == null) {
				Color c = config.colorCombat();
				graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 160 : 255));
				graphics.draw(client.getCanvas().getBounds());
				graphics.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), (lowRender) ? 40 : 80));
				graphics.fill(client.getCanvas().getBounds());
			}

		return null;
	}

}
