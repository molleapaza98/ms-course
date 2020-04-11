package com.erickmp98.product.service;

import com.erickmp98.product.entity.Category;
import com.erickmp98.product.entity.Product;

import java.util.List;

public interface ProductService {

     List<Product> listAllProducts();
     Product getProduct(Long id);
     Product createProduct(Product product);
     Product updateProduct(Product product);
     Product deleteProduct(Long id);
     List<Product> findByCategory(Category category);
     Product updateStock(Long id, Double quantity);
}
