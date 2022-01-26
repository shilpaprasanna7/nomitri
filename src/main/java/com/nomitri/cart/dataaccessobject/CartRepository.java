package com.nomitri.cart.dataaccessobject;

import com.nomitri.cart.domainobject.CartDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface CartRepository extends CrudRepository<CartDO, Long> {

    CartDO findByUserName(String userName);
}
