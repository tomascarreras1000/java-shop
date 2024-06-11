package persistance;

import exceptions.PersistanceException;

public interface ShopDAO {
    public void checkStatus() throws PersistanceException;
}
