package com.erickmp98.product.repository;

import com.erickmp98.product.entity.Category;
import com.erickmp98.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
   public List<Product> findByCategory(Category category);

}
