package org.ddd.fundamental.app.repository.user;

import org.ddd.fundamental.app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("productRepository")
public interface ProductRepository extends JpaRepository<Product,String> {

    Optional<Product> findById(String id);

    @Modifying
    @Query(value = "update product set count = count-1 where id = ?1 ", nativeQuery = true)
    int decrement(String id);

    @Modifying
    @Query(value = "update product set count = count-1 where id = ?1 and count > 0 ", nativeQuery = true)
    int decrementByOptimistic(String id);
}
