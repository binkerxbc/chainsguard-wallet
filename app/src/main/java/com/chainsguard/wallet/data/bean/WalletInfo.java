package com.chainsguard.wallet.data.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author i11m20n
 */
public final class WalletInfo implements Parcelable {

    private transient String privateKey;

    private String publicKey;

    private String address;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.publicKey);
        dest.writeString(this.address);
    }

    public WalletInfo() {
    }

    protected WalletInfo(Parcel in) {
        this.publicKey = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<WalletInfo> CREATOR = new Parcelable.Creator<WalletInfo>() {
        @Override
        public WalletInfo createFromParcel(Parcel source) {
            return new WalletInfo(source);
        }

        @Override
        public WalletInfo[] newArray(int size) {
            return new WalletInfo[size];
        }
    };
}
