package br.com.wjrlabs.commom.session;

/**
 * 
 * @author Wagner Alves
 *
 */
public interface ManageableSession {
	public DeviceSession getSessionID(Integer value);

	public DeviceSession remove(Short value);

	public void clear();

	public int size();

	public DeviceSession[] array();
}
