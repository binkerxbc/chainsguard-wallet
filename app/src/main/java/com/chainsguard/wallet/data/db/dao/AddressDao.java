package com.chainsguard.wallet.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.chainsguard.wallet.data.db.entity.Address;

import java.util.List;


/**
 * @author i11m20n
 */
@Dao
public interface AddressDao {

    /**
     * 获取所有地址实体
     *
     * @return 所有的地址实体
     */
    @Query("SELECT * FROM addresses")
    List<Address> getAll();

    /**
     * 根据指定地址查找地址实体
     *
     * @param address 待查找地址
     * @return 命中的地址实体
     */
    @Query("SELECT * FROM addresses WHERE address LIKE :address LIMIT 1")
    Address findByAddress(String address);

    /**
     * 插入地址实体
     *
     * @param address 新的地址实体
     * @return 新插入的 rowid，如果存在冲突则返回 -1。
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertAddress(Address address);

    /**
     * 删除地址实体
     *
     * @param address 待删除实体
     * @return 删除的行数
     */
    @Delete
    int deleteAddress(Address address);
}
