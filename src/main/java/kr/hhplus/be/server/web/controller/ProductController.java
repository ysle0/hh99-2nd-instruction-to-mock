package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.web.dto.ShowProductReply;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("/{product_id}")
    public RequestEntity<ShowProductReply>
    showProducts(
            @PathVariable(value = "product_id") String productID
    ) {
        return null;
    }
}
