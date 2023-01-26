package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.User;
/**
 * Interface {@code TagService} describes abstract behavior for working with {@link User} objects.
 * @author Oleksandr Myronenko
 */

public interface UserService extends CRUDService<User>{

    /**
     * Method for making order.
     * @param  user that bought certificate
     * @param giftCertificate gift certificate that was bought by user
     */
    void makeOrder(User user,GiftCertificate giftCertificate);

    /**
     * Method for remove order from user.
     * @param user user
     * @param orderId order's id
     */
    void removedOrder(User user, Long orderId);
}
