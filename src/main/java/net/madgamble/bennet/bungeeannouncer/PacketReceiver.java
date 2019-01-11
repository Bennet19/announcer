package net.madgamble.bennet.bungeeannouncer;

import lombok.AllArgsConstructor;
import net.madgamble.core.api.common.transport.IPacketHandler;

@AllArgsConstructor
public class PacketReceiver implements IPacketHandler {
	
	private final BungeeAnnouncer plugin;
	
	@Override
	public void onPacketReceived(IPacketEvent event) {
		try {
			//CustomPacketMessage packet = event.getPacket().readObject(CustomPacketMessage.class);
			plugin.getLogger().info("[IN2] <- "/* + packet*/);
			plugin.getAnnounceManager().load();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
