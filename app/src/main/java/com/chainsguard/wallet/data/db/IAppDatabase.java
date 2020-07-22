package com.chainsguard.wallet.data.db;

import com.chainsguard.wallet.data.db.entity.Address;

import java.util.List;


/**
 * @author i11m20n
 */
public interface IAppDatabase {

    /**
     * 从数据库中获取所有地址实体
     *
     * @return 地址实体列表
     */
    List<Address> getAllAddress();

    /**
     * 根据指定地址查找数据库中已存在的地址实体
     *
     * @param address 待查找的地址
     * @return 返回查找到的地址实体，如果查找不到则返回 null。
     */
    Address findAddressByAddress(String address);

    /**
     * 插入指定地址实体到数据库
     *
     * @param address 待插入地址实体
     * @return 如果返回 true 则插入成功，反之亦然。
     */
    boolean insertAddress(Address address);

    /**
     * 从数据库中删除指定地址实体
     *
     * @param address 待删除地址实体
     * @return 如果返回 true 则删除成功，反之亦然。
     */
    boolean deleteAddress(Address address);
}
