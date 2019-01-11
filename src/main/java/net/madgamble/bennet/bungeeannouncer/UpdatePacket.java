package net.madgamble.bennet.bungeeannouncer;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import net.madgamble.core.api.common.transport.ISubscribableObject;

@Getter
@ToString
@AllArgsConstructor
public class UpdatePacket implements ISubscribableObject<UUID> { 
	
	private UUID uuid;

	@Override
	public UUID getId() {
		return uuid;
	}
	
}
