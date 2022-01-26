package com.nomitri.cart.dataaccessobject;

import com.nomitri.cart.domainobject.ProductDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CrudRepository<ProductDO, Long> {

}
