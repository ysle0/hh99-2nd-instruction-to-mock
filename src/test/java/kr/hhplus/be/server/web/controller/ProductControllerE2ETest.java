package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.domain.Messages;
import kr.hhplus.be.server.domain.product.ProductMessages;
import kr.hhplus.be.server.web.dto.ApiResponse;
import kr.hhplus.be.server.web.dto.ShowProductResponse;
import kr.hhplus.be.server.web.dto.ShowTopNProductsWithinMDaysResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class ProductControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_showProducts() {
        UUID productId = UUID.randomUUID();

        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                "/api/v1/products/" + productId,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        
        if (response.getBody().getCode().equals("ok")) {
            assertEquals(ProductMessages.PRODUCT_QUERY_SUCCESS, response.getBody().getMessage());
            assertEquals(Messages.CODE_OK, response.getBody().getCode());
            assertNotNull(response.getBody().getData());
            
            ObjectMapper mapper = new ObjectMapper();
            ShowProductResponse data = mapper.convertValue(response.getBody().getData(), ShowProductResponse.class);
            assertNotNull(data.productID());
            assertNotNull(data.name());
            assertTrue(data.price() > 0);
            assertTrue(data.quantityLeft() > 0);
        } else {
            assertEquals(ProductMessages.PRODUCT_QUERY_FAILED, response.getBody().getMessage());
            assertEquals(Messages.CODE_NO, response.getBody().getCode());
        }
    }

    @Test
    public void ok_showRangedProducts() {
        int daysWithin = 3;
        int top = 5;

        String url = "/api/v1/products/range?days-within=" + daysWithin + "&top=" + top;
        ResponseEntity<ApiResponse> response = restTemplate.getForEntity(
                url,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.CODE_OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());
        
        ObjectMapper mapper = new ObjectMapper();
        ShowTopNProductsWithinMDaysResponse data = mapper.convertValue(response.getBody().getData(), ShowTopNProductsWithinMDaysResponse.class);
        assertNotNull(data.products());
        assertTrue(data.products().size() <= top);

        for (var p : data.products()) {
            assertNotNull(p.productID());
            assertNotNull(p.name());
            assertTrue(p.price() > 0);
            assertTrue(p.quantityLeft() > 0);
        }
    }


    @Test
    public void okAll_showRangedProducts_multipleRequests() {
        String url1 = "/api/v1/products/range?days-within=5&top=3";
        String url2 = "/api/v1/products/range?days-within=5&top=3";

        ResponseEntity<ApiResponse> response1 = restTemplate.getForEntity(
                url1,
                ApiResponse.class
        );

        ResponseEntity<ApiResponse> response2 = restTemplate.getForEntity(
                url2,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertEquals(ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS, response1.getBody().getMessage());
        assertEquals(Messages.CODE_OK, response1.getBody().getCode());
        ObjectMapper mapper = new ObjectMapper();
        ShowTopNProductsWithinMDaysResponse data1 = mapper.convertValue(response1.getBody().getData(), ShowTopNProductsWithinMDaysResponse.class);
        assertTrue(data1.products().size() <= 3);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals(ProductMessages.PRODUCT_RANGE_QUERY_SUCCESS, response2.getBody().getMessage());
        assertEquals(Messages.CODE_OK, response2.getBody().getCode());
        ShowTopNProductsWithinMDaysResponse data2 = mapper.convertValue(response2.getBody().getData(), ShowTopNProductsWithinMDaysResponse.class);
        assertTrue(data2.products().size() <= 3);
    }

    @Test
    public void ok_ShowProducts_multipleRequests() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        ResponseEntity<ApiResponse> response1 = restTemplate.getForEntity(
                "/api/v1/products/" + productId1,
                ApiResponse.class
        );

        ResponseEntity<ApiResponse> response2 = restTemplate.getForEntity(
                "/api/v1/products/" + productId2,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        
        if (response1.getBody().getCode().equals("ok")) {
            ObjectMapper mapper = new ObjectMapper();
            ShowProductResponse data1 = mapper.convertValue(response1.getBody().getData(), ShowProductResponse.class);
            assertTrue(data1.price() > 0);
        }

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        
        if (response2.getBody().getCode().equals("ok")) {
            ObjectMapper mapper = new ObjectMapper();
            ShowProductResponse data2 = mapper.convertValue(response2.getBody().getData(), ShowProductResponse.class);
            assertTrue(data2.price() > 0);
        }
    }
}