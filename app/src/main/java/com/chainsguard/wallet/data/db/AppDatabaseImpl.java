package com.chainsguard.wallet.data.db;


import androidx.room.Room;

import com.chainsguard.wallet.WalletApplication;
import com.chainsguard.wallet.data.db.dao.AddressDao;
import com.chainsguard.wallet.data.db.entity.Address;

import java.util.List;

/**
 * @author i11m20n
 */
public final class AppDatabaseImpl implements IAppDatabase {

    private AddressDao addressDao;

    private AppDatabaseImpl() {
        WalletApplication application = WalletApplication.getApplication();
        AppDatabase db = Room.databaseBuilder(application, AppDatabase.class, "wallet").build();
        addressDao = db.addressDao();
    }

    private static final class SingletonHolder {
        private static final AppDatabaseImpl INSTANCE = new AppDatabaseImpl();
    }

    public static AppDatabaseImpl getInstance() {
        return AppDatabaseImpl.SingletonHolder.INSTANCE;
    }

    @Override
    public List<Address> getAllAddress() {
        return addressDao.getAll();
    }

    @Override
    public Address findAddressByAddress(String address) {
        return addressDao.findByAddress(address);
    }

    @Override
    public boolean insertAddress(Address address) {
        return addressDao.insertAddress(address) != -1;
    }

    @Override
    public boolean deleteAddress(Address address) {
        return addressDao.deleteAddress(address) != 0;
    }
}
