package com.example.jwtdemo.Controller;

import com.example.jwtdemo.service.ProductService;
import com.example.jwtdemo.vo.Product;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService service;

    private long start() {
        return System.currentTimeMillis();
    }

    private void logTime(String api, long start) {
        System.out.println(api + " took " + (System.currentTimeMillis() - start) + " ms");
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        long start = start();
        Product result = service.create(product);
        logTime("CREATE", start);
        return result;
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable Long id) {
        log.info("come at controller layer");
        long start = start();
        log.info("come at controller layer2");
        Product result = service.getById(id);
        log.info("come at controller layer3");
        logTime("GET", start);
        log.info("come at controller layer4");
        return result;

    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product product) {
        long start = start();
        Product result = service.update(id, product);
        logTime("UPDATE", start);
        return result;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        long start = start();
        service.deleteUser(id);
        logTime("DELETE", start);
    }

    @GetMapping("/allProduct")
    public List<Product> getAllProduct(){
        return service.getAllProduct();
    }

}
