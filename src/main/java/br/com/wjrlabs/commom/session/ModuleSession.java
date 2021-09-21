package br.com.wjrlabs.commom.session;

import java.util.Date;

import br.com.digicon.scap.comm.common.FileReceiving;
import br.com.digicon.scap.comm.common.FileSending;
import br.com.digicon.scap.model.Arquivo;
import br.com.digicon.scap.model.types.MontagemTipoEnum;
import br.com.digicon.scap.monitor.ModuleType;
import br.com.digicon.scap.monitor.ParamType;
import br.com.digicon.session.SessionParam;
import br.com.digicon.session.SimpleSession;
import br.com.digicon.util.MacAddress;
import io.netty.channel.Channel;


/**
 * Representa a sessão de módulo de equipamento.
 *
 */
public class ModuleSession extends SimpleSession {

	/**
	 * Identificador único de versão da classe.
	 */
	private static final long serialVersionUID = 4836198610898247339L;

	/**
	 * Parâmetro de identificação do Equipamento
	 */
	public static final SessionParam<Short> MODULE_ID = new SessionParam<Short>("MODULE_ID");
	
	/**
	 * Indica o estado do Equipamento.
	 */
	public static final SessionParam<Status> STATUS = new SessionParam<Status>("STATUS");
	
	/**
	 * Canal de comunicação com o módulo do equipamento.
	 */
	public static final SessionParam<Channel> CHANNEL = new SessionParam<Channel>("CHANNEL");

	/**
	 * Marca o horário em que um parâmetro foi solicitado para o monitoramento saber
	 * por quanto tempo o monitoramento deve aguardar pela resposta.
	 */
	public static final SessionParam<Date> PARAM_REQUEST = new SessionParam<Date>("PARAM_REQUEST");
	
	/**
	 * Guarda o último tipo de parâmetro requisitado, para o monitoramento poder suspendê-lo 
	 * quando não houver resposta adequada.
	 */
	public static final SessionParam<ParamType> PARAM_TYPE_REQUEST = new SessionParam<ParamType>("PARAM_TYPE_REQUEST"); 
	
	/**
	 * Informações sobre o último arquivo ofertado para o módulo.
	 */
	public static final SessionParam<Arquivo> FILE_OFFER = new SessionParam<Arquivo>("FILE_OFFER");
	
	/**
	 * Arquivo sendo encaminhado para o módulo.
	 */
	public static final SessionParam<FileSending> FILE_SENDING = new SessionParam<FileSending>("FILE_SENDING");

	/**
	 * Arquivio sendo recebido do módulo.
	 */
	public static final SessionParam<FileReceiving>	FILE_RECEIVING	= new SessionParam<FileReceiving>("FILE_RECEIVING");

	/**
	 * Guarda a informação da sigla do bloqueio para o recebimento de aruqivos.
	 */
	public static final SessionParam<String>	SYMBOL	= new SessionParam<String>("SYMBOL");
	
	/**
	 * Guarda a informação de tipo de módulo.
	 */
	public static final SessionParam<ModuleType> MODULE_TYPE 	= new SessionParam<ModuleType>("MODULE_TYPE");
	
	/**
	 * Momento da última troca de mensagens, recebida ou enviada.
	 */
	public static final SessionParam<Date> LAST_HEARTBEAT	= new SessionParam<Date>("LAST_HEARTBEAT");
	
	/**
	 * Momento da última notificação de ajuste de relógio.
	 */
	public static final SessionParam<Date> LAST_TIMER		= new SessionParam<Date>("LAST_TIMER");

	/**
	 * Guarda o Mac Address do módulo para verificar duplicidade de comunicação.
	 */
	public static final SessionParam<MacAddress> MAC_ADDRESS = new SessionParam<MacAddress>("MAC_ADDRESS");
	
	/**
	 * Informa o tipo de montagem utilizacao para a sinalização do equipamento.
	 */
	public static final SessionParam<MontagemTipoEnum> MONTAGEM_SINALIZACAO = new SessionParam<>("MONTAGEM_SINALIZACAO");
	
	/**
	 * Informa o tipo de montagem utilizada para a operação do equipamento.
	 */
	public static final SessionParam<MontagemTipoEnum> MONTAGEM_OPERACAO= new SessionParam<>("MONTAGEM_OPERACAO");
	
	/**
	 * Construtor padrão.
	 */
	public ModuleSession() {
		super();
		setParam(MODULE_ID, Short.MAX_VALUE);
		setParam(STATUS, Status.OFF);
		setParam(PARAM_REQUEST, new Date(0));
		setParam(LAST_HEARTBEAT, new Date(0));
		setParam(LAST_TIMER, new Date(0));
		setParam(MAC_ADDRESS, new MacAddress());
		setParam(SYMBOL, "<UNKNOWN>");
		setParam(MODULE_TYPE, ModuleType.NONE);
		setParam(PARAM_TYPE_REQUEST, ParamType.IP);
		setParam(MONTAGEM_SINALIZACAO, MontagemTipoEnum.NORMAL);
		setParam(MONTAGEM_OPERACAO, MontagemTipoEnum.NORMAL);
	}
	
	/* Utilizado apenas para depuração.
	@Override
	public <T> void setParam(SessionParam<T> param, T value) {
		T old = super.getParam(param);
		if (old instanceof Status) {
			if (((Status) old) == Status.ACK_WAITING) {
				System.out.println("==================================================");
				System.out.print("De ");
				System.out.print(old);
				System.out.print(" para ");
				System.out.println(value);
				StackTraceElement[] elements = Thread.currentThread().getStackTrace();
				for (StackTraceElement element : elements) {
					System.out.println("\t" + element.toString());
				}
				System.out.println("==================================================");
			}
		}
		super.setParam(param, value);
	}
	*/
}
