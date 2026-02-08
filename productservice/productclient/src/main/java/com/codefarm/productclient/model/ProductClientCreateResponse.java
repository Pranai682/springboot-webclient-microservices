package com.codefarm.productclient.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductClientCreateResponse {
    private String status;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Service
    public static class ProductService {
        List<Product> products;

        public ProductService() {
            this.products = new ArrayList<>();

        }

        public ProductClientCreateResponse createProduct(Product product){
            product.setId(products.size()+1);
            products.add(product);
            ProductClientCreateResponse response = new ProductClientCreateResponse();
            response.setMessage("Success");
            response.setStatus("200");
            return response;
        }

        public Product getProductById(int id) {
            return products.stream().filter(product -> product.getId()==id).findFirst().get();
        }

        public List<Product> getProductByName(String name) {
            return products.stream().filter(product -> product.getName().equalsIgnoreCase(name)).collect(Collectors.toList());
        }

        public String deleteProduct(int id) {
            products.remove(getProductById(id));
            return "Product deleted";
        }

        public String updateProduct(Product product) {
            Product existingProduct = getProductById(product.getId());
            if (existingProduct == null){
                throw new RuntimeException("Product not found");
            }
            products.set(products.indexOf(existingProduct), product );
            return "Product updated";
        }
    }
}
