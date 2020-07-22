package com.chainsguard.wallet.data.bean;

import java.util.List;

/**
 * @author i11m20n
 */
public final class AccountBalanceInfo {

    /**
     * 地址
     */
    private String address;

    /**
     * 代币列表
     */
    private List<Currencies> currencies;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Currencies> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currencies> currencies) {
        this.currencies = currencies;
    }

    public static final class Currencies {

        /**
         * 代币符号
         */
        private String symbol;

        /**
         * 可用余额
         */
        private String available;

        /**
         * 未知
         * 注意：应该是是否锁仓的标记
         */
        private String locked;

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        public String getLocked() {
            return locked;
        }

        public void setLocked(String locked) {
            this.locked = locked;
        }

        @Override
        public String toString() {
            return "Currencies{" +
                    "symbol='" + symbol + '\'' +
                    ", available='" + available + '\'' +
                    ", locked='" + locked + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AccountBalanceInfo{" +
                "address='" + address + '\'' +
                ", currencies=" + currencies +
                '}';
    }
}
