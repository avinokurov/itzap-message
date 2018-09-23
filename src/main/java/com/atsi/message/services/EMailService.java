package com.atsi.message.services;

import com.atsi.api.message.model.Message;
import com.atsi.api.message.model.MessageStatus;
import com.atsi.api.message.model.MessageType;
import com.atsi.api.message.model.ResponseStatus;
import com.atsi.api.message.services.MessageService;
import com.atsi.core.command.AbstractCommand;
import com.atsi.exceptions.ATSIEmailError;
import com.atsi.exceptions.ATSIException;
import com.google.common.base.Joiner;
import com.typesafe.config.Config;
import org.apache.commons.io.IOUtils;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Pattern;

public class EMailService implements MessageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EMailService.class);

    private final Config config;

    public EMailService(Config config) {
        this.config = config;
    }

    @Override
    public Observable<ResponseStatus> send(Message message) {
        return new AbstractCommand<ResponseStatus, AbstractCommand>("cmd-emailCommand") {
            @Override
            protected AbstractCommand getThis() {
                return this;
            }

            @Override
            protected ResponseStatus run() {
                Email email = new SimpleEmail();

                try {
                    if (message.getMessageType() == MessageType.HTML) {
                        HtmlEmail htmlEmail = new HtmlEmail();
                        htmlEmail.setHtmlMsg(buildEmailBody(message));
                        email = htmlEmail;
                    } else {
                        email.setMsg(buildEmailBody(message));
                    }
                    email.setFrom(config.getString("email.from"));
                    email.addTo(message.getAddresses().toArray(new String[0]));
                    email.setSubject(message.getSubject());
                    email.setHostName(config.getString("email.host"));
                    if (config.hasPath("email.smtp.port")) {
                        email.setSmtpPort(config.getInt("email.smtp.port"));
                    }
                    email.send();
                } catch (Exception e) {
                    throw new ATSIException(ATSIEmailError.EMAIL_ERROR,
                            String.format("Failed to send message: [%s]. config: [%s:%s]",
                                    message, email.getHostName(), email.getSmtpPort()), e);
                }
                return ResponseStatus.builder()
                        .setAddress(Joiner.on(';').join(message.getAddresses()))
                        .setStatus(MessageStatus.SENT)
                        .build();
            }
        }.toObservable();
    }

    private String buildEmailBody(Message message) throws IOException {
        InputStream template = this.getClass().getResourceAsStream(
                String.format("/templates/%s.template", message.getMessageId()));
        String body = IOUtils.toString(template);
        for (Map.Entry<String, String> e: message.getParameters().entrySet()) {
            body = body.replaceAll("(?i)" + Pattern.quote(String.format("${%s}", e.getKey())),
                    e.getValue());
        }

        return body;
    }
}
