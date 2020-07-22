package com.okchain.crypto.io.cosmos.types.transferMulti;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.okchain.crypto.io.cosmos.types.ValueMulti;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-22 10:26
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class TransferMessageMulti {

    public TransferMessageMulti() {

    }

    public TransferMessageMulti(String type, ValueMulti value) {
        this.type = type;
        this.value = value;
    }

    private String type;

    private ValueMulti value;

    public String getType() {
        return type;
    }

    public void setValue(ValueMulti value) {
        this.value = value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ValueMulti getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "TransferMessageMulti{" +
                "type='" + type + '\'' +
                ", value=" + value +
                '}';
    }
}
