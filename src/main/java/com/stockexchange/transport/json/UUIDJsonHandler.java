package com.stockexchange.transport.json;

import java.util.UUID;

public class UUIDJsonHandler extends JsonHandler<UUID>{

	public String marshall(UUID uuid) {
		return uuid.toString();
	}

	public UUID unmarshall(String json) {
		// TODO Auto-generated method stub
		return UUID.fromString(json);
	}


}
