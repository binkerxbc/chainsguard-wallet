package com.chainsguard.wallet.data.bean;

import com.okchain.common.ConstantIF;

/**
 * @author i11m20n
 */
public final class TransferInfo {

    /**
     * 转账接收地址
     */
    private String toAddress;

    /**
     * 转账金额
     */
    private String amount;

    /**
     * 转账代币符号
     * 目前已知的符号有：TBTC、TUSDK、TOKB、TOKT。
     */
    private String symbol;

    /**
     * 转账备注
     * 注意：长度不能超过 256 位。
     *
     * @see ConstantIF#MAX_MEMO_LEN
     */
    private String memorandum;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMemorandum() {
        return memorandum;
    }

    public void setMemorandum(String memorandum) {
        this.memorandum = memorandum;
    }

    @Override
    public String toString() {
        return "TransferInfo{" +
                "toAddress='" + toAddress + '\'' +
                ", amount='" + amount + '\'' +
                ", symbol='" + symbol + '\'' +
                ", memorandum='" + memorandum + '\'' +
                '}';
    }
}
