package de.ninju.streambot.events;

import java.util.ArrayList;
import java.util.Random;

import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.ninju.streambot.main.LoadStreamBot;

public class ClientMovedEvent extends TS3EventAdapter{
	
	@Override
	public void onClientMoved(com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent e) {
		Client c = LoadStreamBot.api.getClientInfo(e.getClientId());
		if(e.getTargetChannelId() == 5 && !c.isInServerGroup(9)) {
			//ArrayList<Client> sups = new ArrayList<>();
			for(Client clients : LoadStreamBot.api.getClients()) {
				if(clients.isInServerGroup(9)) {
					//sups.add(clients);
					LoadStreamBot.api.sendPrivateMessage(clients.getId(), c.getNickname() + " braucht Support. Schreibe [B]!yes[/B] um dich moven zu lassen.");
				}
			}
			/*
			Random r = new Random();
			Client random = sups.get(r.nextInt(sups.size()));
			LoadStreamBot.api.moveClient(random.getId(), 5);*/
		}
	}

}
