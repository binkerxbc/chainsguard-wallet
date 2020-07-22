package com.chainsguard.wallet.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.chainsguard.wallet.data.db.dao.AddressDao;
import com.chainsguard.wallet.data.db.entity.Address;

/**
 * @author i11m20n
 */
@Database(entities = {Address.class}, version = 1, exportSchema = false)
abstract class AppDatabase extends RoomDatabase {
    public abstract AddressDao addressDao();
}
