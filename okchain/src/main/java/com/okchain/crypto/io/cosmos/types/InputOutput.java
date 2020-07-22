package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-22 10:28
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class InputOutput {

    private String address;
    private List<Token> coins;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Token> getCoins() {
        return coins;
    }

    public void setCoins(List<Token> coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "InputOutput{" +
                "address='" + address + '\'' +
                ", coins=" + coins +
                '}';
    }
}
