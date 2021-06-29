package com.lyni.lockit.model.entity.message;

/**
 * @author Liangyong Ni
 * description EventBus使用到的Message
 * @date 2021/6/19
 */
public class Message {
    /**
     * 消息类型 {@link MessageType}
     */
    private final MessageType messageType;
    /**
     * 消息传递的对象
     */
    private final Object object;

    public Message(MessageType messageType, Object object) {
        this.messageType = messageType;
        this.object = object;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public Object getObject() {
        return object;
    }
}
