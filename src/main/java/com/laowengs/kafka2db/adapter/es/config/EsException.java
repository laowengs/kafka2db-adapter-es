package com.laowengs.kafka2db.adapter.es.config;

import java.text.MessageFormat;

public class EsException extends Exception{

    public EsException() {
    }

    public EsException(String message) {
        super(message);
    }

    public EsException(Throwable cause,String messagePattern, Object ... arguments) {
        super(MessageFormat.format(messagePattern,arguments), cause);
    }
    public EsException(String messagePattern, Object ... arguments) {
        super(MessageFormat.format(messagePattern,arguments));
    }
    public EsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EsException(Throwable cause) {
        super(cause);
    }
}
