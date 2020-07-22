package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:51
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Signature {

    @JsonProperty("pub_key")
    @SerializedName("pub_key")
    private Pubkey pubkey;

    private String signature;

    public Pubkey getPubkey() {
        return pubkey;
    }

    public String getSignature() {
        return signature;
    }

    public void setPubkey(Pubkey pubkey) {
        this.pubkey = pubkey;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Signature{" +
                "pubkey=" + pubkey +
                ", signature='" + signature + '\'' +
                '}';
    }
}
