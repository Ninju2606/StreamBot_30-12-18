package de.ninju.streambot.events;

import java.util.HashMap;
import java.util.Map;

import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.ninju.streambot.main.LoadStreamBot;

public class TextMessageEvent extends TS3EventAdapter{

	@Override
	public void onTextMessage(com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent e) {
		String message = e.getMessage();
		String[] args = message.split(" ");
		Client c = LoadStreamBot.api.getClientInfo(e.getInvokerId());
		if(args[0].equalsIgnoreCase("!ban") && (c.isInServerGroup(6))) { //!ban UUID LÄNGE GRUND
			if(args.length >= 4) {
				String uuid = args[1];
				long time = calculateTime(args[2]);
				String reason = message.replace("!ban " + args[1] + " " + args[2] + " ", "");
				if(time >= 1) {
					Client target = LoadStreamBot.api.getClientByUId(uuid);
					if(target != null) {
						LoadStreamBot.api.banClient(target.getId(), time, reason);
					}else {
						LoadStreamBot.api.sendPrivateMessage(c.getId(), "Der angegeben Client konnte nicht gefunden werden.");
					}
				}else {
					LoadStreamBot.api.sendPrivateMessage(c.getId(), "Es gab einen Fehler bei deiner Zeitangabe.");
				}
			}else {
				LoadStreamBot.api.sendPrivateMessage(c.getId(), "Nutze [B]!ban <UUID> <LÄNGE> <GRUND>[/B].");
			}
		}
		if(args[0].equals("!getuuid")) {
			if(args.length >= 2) {
				String name = message.replace("!getuuid ", "");
				Client target = LoadStreamBot.api.getClientByNameExact(name, true);
				if(target != null) {
					LoadStreamBot.api.sendPrivateMessage(c.getId(), "Die UUID von " + target.getNickname() + " ist: " + target.getUniqueIdentifier());
				}else {
					LoadStreamBot.api.sendPrivateMessage(c.getId(), "Der angegeben Client konnte nicht gefunden werden.");
				}
			}else {
				LoadStreamBot.api.sendPrivateMessage(c.getId(), "Nutze [B]!getuuid <name>[/B].");
			}
		}
		if(args[0].equalsIgnoreCase("!yes") && (c.isInServerGroup(9))) {
			if(c.getChannelId() != 5) {
				LoadStreamBot.api.moveClient(c.getId(), 5);
			}
		}
		if(args[0].equalsIgnoreCase("!sopen") && (c.isInServerGroup(9))) {
			Map<ChannelProperty, String> p = new HashMap<ChannelProperty, String>();
			p.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "1");
			p.put(ChannelProperty.CHANNEL_NAME, "Warte auf Support [OPEN]");
			LoadStreamBot.api.editChannel(5, p);
		}
		if(args[0].equalsIgnoreCase("!sclose")) {
			if(c.isInServerGroup(9)) {
				Map<ChannelProperty, String> p = new HashMap<ChannelProperty, String>();
				p.put(ChannelProperty.CHANNEL_FLAG_MAXCLIENTS_UNLIMITED, "0");
				p.put(ChannelProperty.CHANNEL_NAME, "Warte auf Support [CLOSED]");
				LoadStreamBot.api.editChannel(5, p);
			}else {
				LoadStreamBot.api.sendPrivateMessage(c.getId(), "Du hast keine Rechte diesen Command zu nutzen.");
			}
		}
	}


	private long calculateTime(String arg) {
		long time = -1;
		if(arg.toLowerCase().contains("h")) {
			String timeString = arg.substring(0, arg.length() - 1);
			if(isNumeric(timeString)) {
				int timeInt = Integer.valueOf(timeString);
				time = 3600*timeInt;
			}else {
				return -1;
			}
		}
		if(arg.toLowerCase().contains("d")) {
			String timeString = arg.substring(0, arg.length() - 1);
			if(isNumeric(timeString)) {
				int timeInt = Integer.valueOf(timeString);
				time = 86400*timeInt;
			}else {
				return -1;
			}
		}
		return time;
	}


	private boolean isNumeric(String arg) {
		try {
			int number = Integer.parseInt(arg);
			return number>-1;
		} catch (NumberFormatException e) {
			return false;
		}
	}



}
