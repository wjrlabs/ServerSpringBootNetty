package br.com.wjrlabs.commom.session;

/*
 * Device Session Manager
 */
public class DeviceSessionManager implements ManageableSession {
	
	private static final DeviceSessionManager instance = new DeviceSessionManager();


	//private SimpleSessionManager<ModuleSession> delegate;
	

	private DeviceSessionManager() {
		//delegate = new SimpleSessionManager<ModuleSession>(ModuleSession.class);
	}
	
	public static DeviceSessionManager getInstance() {
		return instance;
	}

	public DeviceSession get(Short value) {
		DeviceSession result	= null;
		/*
		 * Session[] sessions = delegate.array(); for (Session session : sessions) {
		 * Short id = session.getParam(ModuleSession.MODULE_ID); if (id.equals(value)) {
		 * result = (ModuleSession) session; break; } } if (result == null) { result =
		 * delegate.get(ModuleSession.MODULE_ID, value, true); }
		 */
		return result;
	}

	public DeviceSession getSessionID(Integer value) {
		return null;//delegate.get(Session.ID, value);
	}

	public DeviceSession remove(Short value) {
		return null;//delegate.remove(ModuleSession.MODULE_ID, value);
	}

	public void clear() {
		//delegate.clear();
	}

	public int size() {
		return 0;//delegate.size();
	}

	public DeviceSession[] array() {
		/*
		 * Session[] array = delegate.array(); ModuleSession[] result = new
		 * ModuleSession[array.length]; for (int i = 0; i < array.length; i++) {
		 * result[i] = (ModuleSession) array[i]; }
		 */
		return null;//result;
	}
}
