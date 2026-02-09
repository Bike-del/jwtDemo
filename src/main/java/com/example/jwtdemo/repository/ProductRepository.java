package com.example.jwtdemo.repository;

import com.example.jwtdemo.vo.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long>{

}
