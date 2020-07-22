package com.chainsguard.wallet.data.db.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * @author i11m20n
 */
@Entity(tableName = "addresses", indices = {@Index(value = {"address"}, unique = true)})
public final class Address implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Integer id;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "token_symbol")
    private String tokenSymbol;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "desc")
    private String desc;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTokenSymbol() {
        return tokenSymbol;
    }

    public void setTokenSymbol(String tokenSymbol) {
        this.tokenSymbol = tokenSymbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", tokenSymbol='" + tokenSymbol + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.address);
        dest.writeString(this.tokenSymbol);
        dest.writeString(this.name);
        dest.writeString(this.desc);
    }

    public Address() {
    }

    protected Address(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.address = in.readString();
        this.tokenSymbol = in.readString();
        this.name = in.readString();
        this.desc = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
