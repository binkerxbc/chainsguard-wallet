package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:11
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferMessage {

    private String type;

    private Value value;

    public String getType() {
        return type;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TransferMessage{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
