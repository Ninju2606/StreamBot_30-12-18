package de.ninju.streambot.main;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;
import com.github.theholywaffle.teamspeak3.api.ServerInstanceProperty;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

import de.ninju.streambot.events.ChanneEvents;
import de.ninju.streambot.events.ClientJoinEvent;
import de.ninju.streambot.events.ClientLeaveEvent;
import de.ninju.streambot.events.ClientMovedEvent;
import de.ninju.streambot.events.TextMessageEvent;

public class LoadStreamBot {
	
	public static final TS3Config config = new TS3Config();
	public static TS3Query query;
	public static TS3Api api;
	
	public static String[] badnames = {"lorenz", "blocklab"};
	
	public static void main(String[] args) {
		config.setHost("localhost");
		config.setFloodRate(FloodRate.UNLIMITED);
		//config.setDebugLevel(java.util.logging.Level.ALL);
		query = new TS3Query(config);
		query.connect();
		api = new TS3Api(query);
		api.login("Login", "kkvw2Gd6");
		api.selectVirtualServerByPort(9987); //9987 ist Standard
		api.setNickname("StreamBot");
		registerEvents();
		nameCheck();
		System.out.println("Der Bot ist gestartet!");
	}
	
	private static void registerEvents() {
		api.registerAllEvents();
		api.addTS3Listeners(new ClientMovedEvent());
		api.addTS3Listeners(new ClientJoinEvent());
		api.addTS3Listeners(new ClientLeaveEvent());
		api.addTS3Listeners(new TextMessageEvent());
		api.addTS3Listeners(new ChanneEvents());
	}
	
	private static void nameCheck() {
		Executors.newScheduledThreadPool(1).scheduleAtFixedRate(()->{
			for(Client c : api.getClients()) {
				for(String word : badnames) {
					if(c.getNickname().toLowerCase().contains(word)) {
						api.kickClientFromServer("Deine Name enthält verbotene Wörter!", c.getId());
					}
				}
			}
		}, 0, 5L, TimeUnit.SECONDS);
	}

}
