package br.com.wjrlabs.commom;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * Arquivo de configuração baseado em propriedades.
 * 
 */
public final class Config extends Properties {

	/**
	 * Identificador único de versão da classe.
	 */
	private static final long serialVersionUID = 4762714592610727047L;
	
	/**
	 * Referência de única instância do objeto.
	 */
	private static Config instance = null;
	
	/**
	 * Máscara para a propriedade de configuração de decisão para ofertar o arquivo
	 * com a versão 01 da mensagem. O parâemtro {0} deve ser substituído com a 
	 * sigla do equipamento.
	 */
	private static final String FILE_OFFER_FORCE_V01 = "file.offer.force.v01.{0}";

	/**
	 * Construtor oculto.
	 */
	private Config() {
	}
	
	/**
	 * Recupera a única instância do objeto de configuração.
	 * @return Config
	 */
	public static Config getInstance() {
		if (Config.instance == null) {
			Config.instance = new Config();
		}
		return Config.instance;
	}
	
	/**
	 * Verifica se é necessário utilizar a versão 01 da mensagem de oferta de arquivo.
	 * @param symbol Sigla do equipamento.
	 * @return (boolean) Verdadeiro se for para utilizar a versão 01.
	 */
	public boolean isFileOfferV01(String symbol) {
		boolean result = false;
		String key = MessageFormat.format(FILE_OFFER_FORCE_V01, symbol);
		if (containsKey(key)) {
			String value = getProperty(key, "false");
			result = Boolean.parseBoolean(value);
		}
		return result;
	}
}
