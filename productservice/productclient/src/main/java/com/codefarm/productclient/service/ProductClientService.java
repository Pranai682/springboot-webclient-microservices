package com.codefarm.productclient.service;

import com.codefarm.productclient.exception.MyServiceException;
import com.codefarm.productclient.exception.MyCustomExceptionServerIssue;
import com.codefarm.productclient.exception.ProductNotFoundException;
import com.codefarm.productclient.model.Product;
import com.codefarm.productclient.model.ProductClientCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Service
public class ProductClientService {
    private WebClient productWebClient;
    private Logger logger = LoggerFactory.getLogger(ProductClientService.class);

    public ProductClientService(WebClient.Builder webclientBuilder) {
        productWebClient = webclientBuilder.baseUrl("http://localhost:8081")
                .filter((request, next) -> {
                    logger.info("request method {}, url - {}", request.method(), request.url());
                    Mono<ClientResponse> response = next.exchange(request);
                    logger.info("response - {}", response.block().statusCode());
                    return response;
                })
                .filter(((request, next) -> {
                    logger.info("request1 method {}, url - {}", request.method(), request.url());
                    Mono<ClientResponse> response = next.exchange(request);
                    logger.info("response1 - {}", response.block().statusCode());
                    return response;
                }))
                .build();
    }
    public ProductClientCreateResponse createProduct(Product product){
            return productWebClient.post()
                    .uri("/product")
                    .body(BodyInserters.fromValue(product))
                    .exchangeToMono(clientResponse -> {
                        if(clientResponse.statusCode().is2xxSuccessful()){
                            logger.info("Got success response");
                            return clientResponse.bodyToMono(ProductClientCreateResponse.class);
                        }else{
                            logger.error("error occured while creating product");
                            return Mono.error(new RuntimeException("Some Exception.."));
                        }
                    })
                    .block();
    }

    public String updateProduct(Product product){
        return productWebClient.put()
                .uri("/product")
                .body(BodyInserters.fromValue(product))
                .retrieve()
                .onRawStatus(
                        status -> status == 403,
                        response -> Mono.error(
                                new MyServiceException("custom message")
                        )
                )
                .bodyToMono(String.class)
                .block();
    }

    public Product getProductById(int id){
        return productWebClient.get()
                .uri("/product/id/{id}",id)
                .retrieve()
                .onStatus(HttpStatusCode::is5xxServerError,response ->{
                    logger.error("Internal Server Error",response);
                   return  Mono.error(new MyCustomExceptionServerIssue("Internal server Error"));
                })
                .onStatus(HttpStatusCode::is4xxClientError, response ->{
                    logger.error("Internal Server Error",response);
                    return  Mono.error(new ProductNotFoundException("Product Not Found"));
                })
                .bodyToMono(Product.class)
                .log()
                .doOnSubscribe(subscription -> logger.info("subscribed .."))
                .doOnSuccess(succ -> logger.info("suceess.. {} ", succ))
                .doOnError(erro -> logger.error("errored out ", erro))
//                .retry(3)
                .retryWhen(Retry.fixedDelay(10, Duration.ofMillis(1000))
                            .doBeforeRetry(x ->logger.info("Retrying ..-{}",x.totalRetries()))
                            .doAfterRetry(x ->logger.info("after retry ..-{}",x.totalRetriesInARow()))
                )
                .block();
    }


    public List<Product> getProductByName(String name){
        return productWebClient.get()
                .uri("/product/name/{name}", name)
                .retrieve()
                .bodyToFlux(Product.class)
                .log()
                .collectList()
                .block();
    }


    public String deleteProduct(int id){
        return productWebClient.delete()
                .uri("/product/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofMillis(700))
                .onErrorResume(TimeoutException.class,erro->{
                    return Mono.error(new TimeoutException("timeout exception"));
                })
                .block();
    }


}
