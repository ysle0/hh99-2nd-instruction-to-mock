package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.web.dto.OrderProductReply;
import kr.hhplus.be.server.web.dto.OrderProductRequest;
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
public class OrderControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_orderProduct() {
        var request = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), 5);

        ResponseEntity<OrderProductReply> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                OrderProductReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isOrdered());
    }

    @Test
    public void fail_orderProduct_withZeroQuantity() {
        var request = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), 0);

        ResponseEntity<OrderProductReply> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                OrderProductReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isOrdered());
    }

    @Test
    public void fail_orderProduct_withNegativeQuantity() {
        var request = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), -1);

        ResponseEntity<OrderProductReply> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                OrderProductReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isOrdered());
    }

    @Test
    public void okAll_orderProduct_multipleOrders() {
        var request1 = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), 2);
        var request2 = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), 4);

        ResponseEntity<OrderProductReply> response1 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request1,
                OrderProductReply.class
        );

        ResponseEntity<OrderProductReply> response2 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request2,
                OrderProductReply.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertTrue(response1.getBody().isOrdered());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().isOrdered());
    }

    @Test
    public void okPartially_orderProduct_multipleOrders() {
        var request1 = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), 2);
        var request2 = new OrderProductRequest(UUID.randomUUID(), UUID.randomUUID(), -234);

        ResponseEntity<OrderProductReply> response1 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request1,
                OrderProductReply.class
        );

        ResponseEntity<OrderProductReply> response2 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request2,
                OrderProductReply.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertTrue(response1.getBody().isOrdered());

        assertNotNull(response2.getBody());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertFalse(response2.getBody().isOrdered());
    }
}