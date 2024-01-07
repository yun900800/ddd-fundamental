package org.ddd.fundamental.message.kafka.serdes;

import java.io.Serializable;

public class MessageDTO implements Serializable {
    private String message;
    private String version;

    public MessageDTO() {

    }

    public MessageDTO(String message, String version) {
        this.message = message;
        this.version = version;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "message='" + message + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
