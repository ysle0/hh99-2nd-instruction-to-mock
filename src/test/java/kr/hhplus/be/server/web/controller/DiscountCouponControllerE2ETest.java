package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalRequest;
import kr.hhplus.be.server.web.dto.IssueInOrderOfArrivalReply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public class DiscountCouponControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_issueInOrderOfArrival() {
        var request = new IssueInOrderOfArrivalRequest();
        
        ResponseEntity<IssueInOrderOfArrivalReply> response = restTemplate.postForEntity(
                "/api/v1/discount-coupons/issue", 
                request, 
                IssueInOrderOfArrivalReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}