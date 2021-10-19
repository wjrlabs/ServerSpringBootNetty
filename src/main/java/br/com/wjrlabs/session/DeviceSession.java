package br.com.wjrlabs.session;

import lombok.Getter;

@Getter
public class DeviceSession {
	private Integer sessionId;

	public DeviceSession(int sessionId) {
		this.sessionId = sessionId;
	}
}
