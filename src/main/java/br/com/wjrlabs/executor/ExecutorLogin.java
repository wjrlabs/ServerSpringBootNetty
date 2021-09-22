package br.com.wjrlabs.executor;

import java.util.ArrayList;
import java.util.List;

import br.com.wjrlabs.commom.exceptions.CommandRuntimeException;
import br.com.wjrlabs.commom.session.DeviceSession;
import br.com.wjrlabs.commom.session.DeviceSessionManager;
import br.com.wjrlabs.core.Message;
import br.com.wjrlabs.core.SimpleExecutor;
import br.com.wjrlabs.messages.MessageEcho;
import lombok.extern.slf4j.Slf4j;

/**
 * Executor for {@link MessageEcho}.
 *
 */
@Slf4j
public class ExecutorLogin extends SimpleExecutor<MessageEcho, DeviceSession> {

    private DeviceSessionManager sessions;

    public ExecutorLogin() {
        super(MessageEcho.class);
        try {
            sessions	= DeviceSessionManager.getInstance();
        } catch (Exception e) {
            String error = "Erro ao recupear o objeto remoto";
            log.error(error, e);
            throw new CommandRuntimeException(error, e);
        }
    }

    @Override
    public Message[] execute(MessageEcho message, DeviceSession session) {
        List<Message> result = new ArrayList<Message>();

        try {
            log.info("Device authentication - execute");

        } catch (Exception e) {
            log.error("Device authentication error", e);
        }

        return result.toArray(new Message[result.size()]);
    }
}
