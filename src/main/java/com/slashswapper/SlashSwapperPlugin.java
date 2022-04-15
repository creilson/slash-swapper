package com.slashswapper;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.ScriptID;
import net.runelite.api.events.ScriptPreFired;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

@Slf4j
@PluginDescriptor(
	name = "Slash Swapper"
)
public class SlashSwapperPlugin extends Plugin
{
	@Inject
	private Client client;

	private static final int FRIENDS_CHANNEL = 4;
	private static final int CLAN_CHANNEL = 5;

	@Subscribe
	public void onScriptPreFired(ScriptPreFired event)
	{
		if (event.getScriptId() != ScriptID.CHAT_SEND)
		{
			return;
		}

		if (client.getVarbitValue(4394) == 1)
		{
			return;
		}

		final String[] stringStack = client.getStringStack();
		final int[] intStack = client.getIntStack();
		int stringStackCount = client.getStringStackSize();
		int intStackCount = client.getIntStackSize();

		String msg = stringStack[stringStackCount - 1];

		int channelSelected = client.getVarcIntValue(41); // Selected channel button
		if (intStack[intStackCount - 4] == 3 && channelSelected != CLAN_CHANNEL)
		{
			intStack[intStackCount - 4] = 2;
			intStack[intStackCount - 3] = -1;
			stringStack[stringStackCount - 1] = "/" + msg;
		}
		else if (intStack[intStackCount - 4] == 2 && channelSelected != FRIENDS_CHANNEL)
		{
			intStack[intStackCount - 4] = 3;
			intStack[intStackCount - 3] = 0;
			stringStack[stringStackCount - 1] = msg.substring(1);
		}
	}
}
