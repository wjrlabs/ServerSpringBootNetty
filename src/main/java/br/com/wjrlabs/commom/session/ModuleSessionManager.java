package br.com.wjrlabs.commom.session;

import br.com.digicon.session.Session;
import br.com.digicon.session.SimpleSessionManager;

/**
 * Gerenciamento de sessão para os módulos dos equipamentos do SCAP.
 *

 */
public class ModuleSessionManager {
	
	private static final ModuleSessionManager instance = new ModuleSessionManager();

	/**
	 * Gerenciamento básico de objetos de sessão.
	 */
	private SimpleSessionManager<ModuleSession> delegate;
	
	/**
	 * Construtor oculto.
	 */
	private ModuleSessionManager() {
		delegate = new SimpleSessionManager<ModuleSession>(ModuleSession.class);
	}
	
	/**
	 * Recupera a única instância do gerenciador de sessão.
	 * @return {@link ModuleSessionManager}
	 */
	public static ModuleSessionManager getInstance() {
		return instance;
	}

	/**
	 * Recupera ou cria uma nova sessão de módulo de equipamento.
	 * @param value Identificador único do Módulo
	 * @return {@link ModuleSession}.
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#get(br.com.digicon.scap.comm.common.SessionParam, java.lang.Object)
	 */
	public ModuleSession get(Short value) {
		ModuleSession result	= null;
		Session[] sessions		= delegate.array();
		for (Session session : sessions) {
			Short id = session.getParam(ModuleSession.MODULE_ID);
			if (id.equals(value)) {
				result = (ModuleSession) session;
				break;
			}
		}
		if (result == null) {
			result = delegate.get(ModuleSession.MODULE_ID, value, true);
		}
		return result;
	}

	/**
	 * Cada sessão terá um identificador único de sessão, gerenciado pelo sistema e de modo incremental.
	 * @param value Valor do ID da Sessão.
	 * @return {@link ModuleSession}
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#get(br.com.digicon.scap.comm.common.SessionParam, java.lang.Object)
	 */
	public ModuleSession getSessionID(Integer value) {
		return delegate.get(Session.ID, value);
	}

	/**
	 * @param value
	 * @return {@link ModuleSession}
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#remove(br.com.digicon.scap.comm.common.SessionParam, java.lang.Object)
	 */
	public ModuleSession remove(Short value) {
		return delegate.remove(ModuleSession.MODULE_ID, value);
	}

	/**
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#clear()
	 */
	public void clear() {
		delegate.clear();
	}

	/**
	 * @return int
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#size()
	 */
	public int size() {
		return delegate.size();
	}

	/**
	 * @return Matriz de {@link ModuleSession}
	 * @see br.com.digicon.scap.comm.common.SimpleSessionManager#array()
	 */
	public ModuleSession[] array() {
		Session[] array			= delegate.array();
		ModuleSession[] result	= new ModuleSession[array.length];
		for (int i = 0; i < array.length; i++) {
			result[i] = (ModuleSession) array[i];
		}
		return result;
	}
}
