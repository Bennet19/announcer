package net.madgamble.bennet.bungeeannouncer;

import java.io.IOException;
import java.util.Timer;
import java.util.UUID;
import java.util.logging.Level;

import com.flowpowered.network.Message;
import com.flowpowered.network.MessageHandler;

import lombok.Getter;
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceAdd;
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceList;
import net.madgamble.bennet.bungeeannouncer.cmd.AnnounceRemove;
import net.madgamble.bennet.bungeeannouncer.manager.AnnounceManager;
import net.madgamble.bennet.bungeeannouncer.task.AnnouncerTask;
import net.madgamble.core.api.common.Core;
import net.madgamble.core.api.common.transport.ISubscriptionManager;
import net.madgamble.core.api.common.transport.ISubscriptionManager.SubscriptionListener;
import net.madgamble.core.api.common.transport.ITransportManager;
import net.madgamble.core.db.mysql.IMysqlConnector;
import net.madgamble.mgs.net.DynamicSession;
import net.madgamble.mgs.net.client.MgsClient;
import net.madgamble.mgs.net.messages.CustomPayloadMessage;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeAnnouncer extends Plugin {
	private MainConfig config;
	private AnnouncerTask task;
	private Timer timer = new Timer();
	
	@Getter
	private AnnounceManager announceManager;
	@Getter
	private IMysqlConnector mysqlConnector;
	
	private ITransportManager transportManager;

	private ISubscriptionManager<UUID, UpdatePacket> subscriptions;
	private SubscriptionListener<UUID, UpdatePacket> listener;
	
	private MgsClient client;
	private String tag = "bungeeAnnouncer";
	private MessageHandler<DynamicSession, Message> handler;
	
	public void onEnable() {
		try {
			this.config = new MainConfig(this);
		} catch (Exception ex) {
			ProxyServer.getInstance().getLogger().log(Level.SEVERE, "FAILED TO LOAD CONFIG!!!", ex);
			return;
		}
		
		mysqlConnector = Core.getApi().getMysqlConnector("core");
		
		announceManager = new AnnounceManager(this);
		client = MgsClient.getInstance();
		client.addCustomPayloadTags(tag);
		
		MgsClient.addMessageHandler(client, handler = new MessageHandler<DynamicSession, Message>() {
			
			@Override
			public void handle(DynamicSession session, Message rawMessage) {
				getLogger().info("[IN] <- " + rawMessage.toString());
				if (rawMessage instanceof CustomPayloadMessage) {
					CustomPayloadMessage mess = (CustomPayloadMessage) rawMessage;
					if (isIn(mess.getTags(), tag)) {
						String s = (String) mess.getPayload().get(0, String.class);
						if (s.equalsIgnoreCase("update")) {
							announceManager.load();
						}
					}
				}
			}
		});
		
		/*transportManager = BungeeCore.getApi().getTransportManager();
		if (transportManager.getCodec(UpdatePacket.class) == null) {
			transportManager.registerCodec(UpdatePacket.class, UPDATE_USER_CODEC);
			transportManager.registerSubscribable(534, UUID.class, UpdatePacket.class);
		}
		subscriptions = transportManager.getSubscriptionHandler(534);
		subscriptions.setTimeoutAfterWrite(24, TimeUnit.HOURS);
		try {
			subscriptions.subscribe(null);
		} catch (IllegalArgumentException | IOException e) {
			getLogger().log(Level.WARNING, "Could not subscribe to reports:", e);
		}
		
		subscriptions.addListener(listener = new SubscriptionListener<UUID, UpdatePacket>() {
			
			public void onUpdate(UUID uuid, UpdatePacket packet) {
				getLogger().info("[IN] -> " + packet.toString());
				announceManager.load();
			}
			
			@Override
			public void onDrop(UUID key) {
			}
		});*/
		
		registerCommands();
		sendAnnouncement();
	}
	
	public boolean isIn(String[] array, String tag) {
		for (String s : array) {
			if (s.equals(tag)) {
				return true;
			}
		}
		return false;
	}
	
	/*final IObjectCodec<UpdatePacket> UPDATE_USER_CODEC = new IObjectCodec<UpdatePacket>() {

		@Override
		public void encode(ByteBuf buf, UpdatePacket object) throws IOException {
			PacketUtil.writeUUID(buf, object.getId());
		}

		@Override
		public UpdatePacket decode(ByteBuf buf) throws IOException {
			UUID id = PacketUtil.readUUID(buf);
			return new UpdatePacket(id);
		}
	};*/
	
	public void sendUpdate() throws IOException, IllegalArgumentException {
		/*UUID uuid = UUID.randomUUID();
		UpdatePacket packet = new UpdatePacket(uuid);
		subscriptions.update(uuid, packet);*/
		CustomPayloadMessage packet = new CustomPayloadMessage(new String[] {tag});
		packet.getPayload().put(0, "update", String.class);
		client.controll().sendMessage(packet);
		getLogger().info("[OUT] -> " + packet.toString());
	}

	public void onDisable() {
		this.timer.cancel();
		this.task.cancel();
		MgsClient.removeMessageHandler(client, handler);
	}

	public void sendAnnouncement() {
		this.task = new AnnouncerTask(this);
		this.timer.schedule(this.task, getConfigStorage().getBroadcastIntervall() * 1000, getConfigStorage().getBroadcastIntervall() * 1000);
	}

	public MainConfig getConfigStorage() {
		return this.config;
	}

	public void registerCommands() {
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceAdd(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceRemove(this));
		ProxyServer.getInstance().getPluginManager().registerCommand(this, new AnnounceList(this));
	}

	public AnnouncerTask getAnnounceTask() {
		return this.task;
	}
}
