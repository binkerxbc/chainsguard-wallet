package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:52
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Pubkey {

    public Pubkey() {

    }
    public Pubkey(String value) {
        this.type = "tendermint/PubKeySecp256k1";
        this.value = value;
    }

    private String type;

    private String value;

    public void setType(String type) {
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Pubkey{" +
                "type='" + type + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
