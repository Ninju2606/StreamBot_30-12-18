package de.ninju.streambot.events;

import java.util.HashMap;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.ninju.streambot.main.LoadStreamBot;

public class ClientJoinEvent extends TS3EventAdapter{
	
	@Override
	public void onClientJoin(com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent e) {
		Client c = LoadStreamBot.api.getClientInfo(e.getClientId());
		int rekord = Integer.parseInt(LoadStreamBot.api.getChannelInfo(35).getName().replace("[spacer0] Rekord: ", ""));
		int currentAmount = LoadStreamBot.api.getClients().size() - 1;
		if(currentAmount > rekord) {
			Map<ChannelProperty, String> p = new HashMap<ChannelProperty, String>();
			p.put(ChannelProperty.CHANNEL_NAME, "[spacer0] Rekord: " + currentAmount);
			LoadStreamBot.api.editChannel(35, p);
			LoadStreamBot.api.sendServerMessage("Wir haben einen neuen Userrekord von " + currentAmount);
		}
		for(String word : LoadStreamBot.badnames) {
			if(c.getNickname().toLowerCase().contains(word)) {
				LoadStreamBot.api.kickClientFromServer("Deine Name enthält verbotene Wörter!", c.getId());
			}
		}
	}

}
