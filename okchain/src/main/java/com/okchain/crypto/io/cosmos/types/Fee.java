package com.okchain.crypto.io.cosmos.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * @program: cosmos-java-sdk
 * @description:
 * @author: liqiang
 * @create: 2019-03-19 18:51
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder(alphabetic = true)
public class Fee {

    private List<Token> amount;

    private String gas;

    public List<Token> getAmount() {
        return amount;
    }

    public String getGas() {
        return gas;
    }

    public void setAmount(List<Token> amount) {
        this.amount = amount;
    }

    public void setGas(String gas) {
        this.gas = gas;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "amount=" + amount +
                ", gas='" + gas + '\'' +
                '}';
    }
}
