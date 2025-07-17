package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.web.dto.ShowProductReply;
import kr.hhplus.be.server.web.dto.ShowTopNProductsWithinMDaysReply;
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

        ResponseEntity<ShowProductReply> response = restTemplate.getForEntity(
                "/api/v1/products/" + productId,
                ShowProductReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().productID());
        assertNotNull(response.getBody().name());
        assertTrue(response.getBody().price() > 0);
        assertTrue(response.getBody().quantityLeft() > 0);
    }

    @Test
    public void ok_showRangedProducts() {
        int daysWithin = 3;
        int top = 5;

        String url = "/api/v1/products/range?days-within=" + daysWithin + "&top=" + top;
        ResponseEntity<ShowTopNProductsWithinMDaysReply> response = restTemplate.getForEntity(
                url,
                ShowTopNProductsWithinMDaysReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().products());
        assertTrue(response.getBody().products().size() <= top);

        for (var p : response.getBody().products()) {
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

        ResponseEntity<ShowTopNProductsWithinMDaysReply> response1 = restTemplate.getForEntity(
                url1,
                ShowTopNProductsWithinMDaysReply.class
        );

        ResponseEntity<ShowTopNProductsWithinMDaysReply> response2 = restTemplate.getForEntity(
                url2,
                ShowTopNProductsWithinMDaysReply.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertTrue(response1.getBody().products().size() <= 3);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().products().size() <= 7);
    }

    @Test
    public void ok_ShowProducts_multipleRequests() {
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        ResponseEntity<ShowProductReply> response1 = restTemplate.getForEntity(
                "/api/v1/products/" + productId1,
                ShowProductReply.class
        );

        ResponseEntity<ShowProductReply> response2 = restTemplate.getForEntity(
                "/api/v1/products/" + productId2,
                ShowProductReply.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertTrue(response1.getBody().price() > 0);

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().price() > 0);
    }
}