package br.com.wjrlabs.commom;

import javax.naming.NamingException;

import br.com.digicon.scap.client.ServiceLocator;
import br.com.digicon.scap.ejb.session.MonitorFacade;
import br.com.digicon.scap.ejb.session.MonitorFacadeRemote;
import br.com.digicon.scap.model.types.ArquivoTipoEnum;
import br.com.digicon.scap.monitor.Param;
import br.com.digicon.scap.monitor.ParamType;
import br.com.digicon.scap.monitor.validator.ParamValidator;
import lombok.extern.slf4j.Slf4j;

/**
 * Utilitário para usar os validadores de parâmetros do monitoramento.
 * 
 */
@Slf4j
public class ParamUtil {

	
	/**
	 * Auto referência para uma única instância.
	 */
	private static ParamUtil instance = null;
	
	/**
	 * Acesso ao objeto de negócio da camada de monitoramento.
	 */
	private MonitorFacade monitor;

	/**
	 * Construtor oculto.
	 * @throws NamingException 
	 */
	private ParamUtil() throws NamingException {
		log.trace("Objeto de apoio para os validadores de parâmetros de monitoramento criado.");
		ServiceLocator locator 	= ServiceLocator.getInstance();
		monitor					= locator.getService(MonitorFacadeRemote.class);
	}
	
	public static ParamUtil getInstance() {
		if (instance == null) {
			try {
				instance = new ParamUtil();
			} catch (NamingException e) {
				log.error("Erro ao criar o utilitário de validação de parâmetros de monitoramento.", e);
			}
		}
		return instance;
	}
	
	public static ParamValidator getValidator(short id, ParamType type) {
		return getInstance().monitor.getValidator(id, type);
	}

	/**
	 * Localiza o tipo de parâmetro associado ao tipo de arquivo.
	 * @param id Identificador único do módulo.
	 * @param arquivoTipo Tipo de Arquivo.
	 * @return Tipo de Parâmetro de monitoramento.
	 */
	public static ParamType getParambyArquivoTipo(short id, ArquivoTipoEnum arquivoTipo) {
		ParamType result = null;
		Param<?>[] params = getInstance().monitor.getModuleParams(id);
		for (Param<?> param : params) {
			if (param.getFileType() == arquivoTipo) {
				result = param.getType();
				break;
			}
		}
		return result;
	}
}
