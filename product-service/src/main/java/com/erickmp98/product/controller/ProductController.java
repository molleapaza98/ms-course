package com.erickmp98.product.controller;

import com.erickmp98.product.entity.Category;
import com.erickmp98.product.entity.Product;
import com.erickmp98.product.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "{categoryId}/categories")
    public ResponseEntity<List<Product>> listProduct(@PathVariable Long categoryId){
        List<Product> products = new ArrayList<>();

        if (null == categoryId){
            products = productService.listAllProducts();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
            return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id){
        Product product = productService.getProduct(id);
        if (null == product){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product, BindingResult result){
        if(result.hasErrors()){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Product productCreate = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product){
        product.setId(id);
        Product productDB = productService.updateProduct(product);
        if (productDB==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDB);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id){
        Product productDelete = productService.deleteProduct(id);
        if(productDelete==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDelete);
    }

    @PostMapping(value = "/{id}/stock/{quantity}")
    public ResponseEntity<Product> updateStockProduct(@PathVariable Long id, @PathVariable Double quantity){
        Product product = productService.updateStock(id,quantity);
        if(null==product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    public String formatMessage(BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err->{
                    Map<String,String> error = new HashMap<>();
                    error.put(err.getField(),err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString=mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }

}
