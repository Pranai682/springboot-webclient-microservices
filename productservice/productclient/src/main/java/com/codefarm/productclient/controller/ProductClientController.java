package com.codefarm.productclient.controller;

import com.codefarm.productclient.model.ProductClientCreateResponse;
import com.codefarm.productclient.service.ProductClientService;
import com.codefarm.productclient.model.Product;
;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/product")
public class ProductClientController {
    private ProductClientService productClientService;

    public ProductClientController(ProductClientService productClientService) {
        this.productClientService = productClientService;
    }

    @PostMapping ("")
    ProductClientCreateResponse createProduct(@RequestBody Product product){
        return productClientService.createProduct(product);
    }

    @PutMapping("")
    String updateProduct(@RequestBody Product product){
        return productClientService.updateProduct(product);
    }

    @GetMapping("/id/{id}")
    public Product getProductById(@PathVariable int id){
        return productClientService.getProductById(id);
    }
    //http:/localhost:8080/product?name='java'
    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name){
        return productClientService.getProductByName(name);
    }

    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable int id){
        return productClientService.deleteProduct(id);
    }
}

