package br.com.wjrlabs.session;

public class SessionManager {
	private static DeviceSession deviceSession;

	private SessionManager() {

	}

	public static DeviceSession getSessionID(int sessionId) {
		if (deviceSession == null) {
			deviceSession = new DeviceSession(sessionId);
		}
		return deviceSession;
	}

}
