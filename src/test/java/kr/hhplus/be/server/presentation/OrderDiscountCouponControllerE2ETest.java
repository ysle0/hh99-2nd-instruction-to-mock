package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.order.domain.misc.OrderMessages;
import kr.hhplus.be.server.order.presentation.dto.OrderProductRequest;
import kr.hhplus.be.server.order.presentation.dto.OrderProductResponse;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class OrderDiscountCouponControllerE2ETest {
    private static final long TEST_USER_ID = 100L;
    private static final long TEST_PRODUCT_ID = 10L;


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_orderProduct() {
        var request = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, 5);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderMessages.ORDER_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        OrderProductResponse data = mapper.convertValue(response.getBody().getData(), OrderProductResponse.class);
        assertTrue(data.isOrdered());
    }

    @Test
    public void fail_orderProduct_withZeroQuantity() {
        var request = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, 0);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderMessages.ORDER_INVALID_QUANTITY, response.getBody().getMessage());
        assertEquals(Messages.NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        OrderProductResponse data = mapper.convertValue(response.getBody().getData(), OrderProductResponse.class);
        assertFalse(data.isOrdered());
    }

    @Test
    public void fail_orderProduct_withNegativeQuantity() {
        var request = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, -1);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/orders/",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderMessages.ORDER_INVALID_QUANTITY, response.getBody().getMessage());
        assertEquals(Messages.NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        OrderProductResponse data = mapper.convertValue(response.getBody().getData(), OrderProductResponse.class);
        assertFalse(data.isOrdered());
    }

    @Test
    public void okAll_orderProduct_multipleOrders() {
        var request1 = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, 2);
        var request2 = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, 4);

        ResponseEntity<ApiResponse> response1 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request1,
                ApiResponse.class
        );

        ResponseEntity<ApiResponse> response2 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request2,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertEquals(OrderMessages.ORDER_SUCCESS, response1.getBody().getMessage());
        assertEquals(Messages.OK, response1.getBody().getCode());
        ObjectMapper mapper = new ObjectMapper();
        OrderProductResponse data1 = mapper.convertValue(response1.getBody().getData(), OrderProductResponse.class);
        assertTrue(data1.isOrdered());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals(OrderMessages.ORDER_SUCCESS, response2.getBody().getMessage());
        assertEquals(Messages.OK, response2.getBody().getCode());
        OrderProductResponse data2 = mapper.convertValue(response2.getBody().getData(), OrderProductResponse.class);
        assertTrue(data2.isOrdered());
    }

    @Test
    public void okPartially_orderProduct_multipleOrders() {
        var request1 = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, 2);
        var request2 = new OrderProductRequest(TEST_USER_ID, TEST_PRODUCT_ID, -234);

        ResponseEntity<ApiResponse> response1 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request1,
                ApiResponse.class
        );

        ResponseEntity<ApiResponse> response2 = restTemplate.postForEntity(
                "/api/v1/orders/",
                request2,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertEquals(OrderMessages.ORDER_SUCCESS, response1.getBody().getMessage());
        assertEquals(Messages.OK, response1.getBody().getCode());
        ObjectMapper mapper = new ObjectMapper();
        OrderProductResponse data1 = mapper.convertValue(response1.getBody().getData(), OrderProductResponse.class);
        assertTrue(data1.isOrdered());

        assertNotNull(response2.getBody());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(OrderMessages.ORDER_INVALID_QUANTITY, response2.getBody().getMessage());
        assertEquals(Messages.NO, response2.getBody().getCode());
        OrderProductResponse data2 = mapper.convertValue(response2.getBody().getData(), OrderProductResponse.class);
        assertFalse(data2.isOrdered());
    }
}