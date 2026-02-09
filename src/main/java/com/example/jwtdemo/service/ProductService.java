package com.example.jwtdemo.service;

import com.example.jwtdemo.GlobalException.ResourceNotFoundException;
import com.example.jwtdemo.repository.ProductRepository;
import com.example.jwtdemo.vo.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    //create
    //#result is the object that is return by the method

    @CachePut(value = "products", key = "'PRODUCT:'+ #result.id")
    public Product create(Product product) {
        simulateSlow();
        return productRepository.save(product);
    }



    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    //get by id
    @Cacheable(
            value = "products",
            key = "'PRODUCT:' + #id",
            unless = "#result == null"
    )
    public Product getById(Long id){
        log.info("come here at service...");
        simulateSlow();
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found product for this id:"+id));
    }

    //update
    @CachePut(
            value = "products",
            key = "'PRODUCT:'+ #id"

    )
    public Product update(Long id,Product product) {
        simulateSlow();
        Product oldProduct = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found product for this id:"+id));
        oldProduct.setProductName(product.getProductName());
        oldProduct.setProductPrice(product.getProductPrice());

        return productRepository.save(oldProduct);
    }

    //delete
    @CacheEvict(
            value = "products",
            key = "'PRODUCT:' + #id",
            beforeInvocation = true //remove from the cache before method execution

    )
    public void deleteUser(Long id){
        simulateSlow();
//        productRepository.deleteById(id);
    }

    private void simulateSlow(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



}
