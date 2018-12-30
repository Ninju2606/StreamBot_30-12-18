package de.ninju.streambot.events;

import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.wrapper.ChannelInfo;

import de.ninju.streambot.main.LoadStreamBot;

public class ChanneEvents extends TS3EventAdapter{
	
	@Override
	public void onChannelCreate(ChannelCreateEvent e) {
		checkChannelName(e.getChannelId());
	}
	
	@Override
	public void onChannelEdit(ChannelEditedEvent e) {
		checkChannelName(e.getChannelId());
	}
	
	private void checkChannelName(int channelId) {
		ChannelInfo ch = LoadStreamBot.api.getChannelInfo(channelId);
		for(String word : LoadStreamBot.badnames) {
			if(ch.getName().toLowerCase().contains(word)) {
				LoadStreamBot.api.deleteChannel(channelId);
			}
		}
	}

}
