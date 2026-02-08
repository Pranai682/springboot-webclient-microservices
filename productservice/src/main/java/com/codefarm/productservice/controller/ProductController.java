package com.codefarm.productservice.controller;

import com.codefarm.productservice.model.Product;
import com.codefarm.productservice.model.ProductCreateResponse;
import com.codefarm.productservice.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping ("")
    ProductCreateResponse createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("")
   String updateProduct(@RequestBody Product product){
        return productService.updateProduct(product);
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable int id){
        return productService.getProductById(id);
    }
    //http:/localhost:8080/product?name='java'
    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name){
        return productService.getProductByName(name);
    }

    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable int id) throws InterruptedException {
        return productService.deleteProduct(id);
    }




}

