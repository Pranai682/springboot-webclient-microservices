package com.codefarm.productservice.service;

import com.codefarm.productservice.model.Product;
import com.codefarm.productservice.model.ProductCreateResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    List<Product> products;

    public ProductService() {
        this.products = new ArrayList<>();

    }

    public ProductCreateResponse createProduct(Product product){
        if(product.getName()==null){
            throw new RuntimeException("naame cant be null");
        }
        product.setId(products.size()+1);
        products.add(product);
        ProductCreateResponse response = new ProductCreateResponse();
        response.setMessage("Success");
        response.setStatus("200");
        return response;
    }

    public Product getProductById(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Product not found with id " + id));
    }



    public List<Product> getProductByName(String name) {
        return products.stream().filter(product -> product.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
    }

    public String deleteProduct(int id) throws InterruptedException {
        Thread.sleep(800);
        Product product = getProductById(id);
        products.remove(product);
        return "Product deleted";
    }


    public String updateProduct(Product product) {
        Product existingProduct = getProductById(product.getId());
        int index = products.indexOf(existingProduct);
        products.set(index, product);
        return "Product updated";
    }

}

