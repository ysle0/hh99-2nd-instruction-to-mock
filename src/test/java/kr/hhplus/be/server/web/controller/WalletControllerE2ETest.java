package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.web.dto.ChargeBalanceReply;
import kr.hhplus.be.server.web.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.web.dto.ShowBalanceReply;
import kr.hhplus.be.server.web.dto.ShowBalanceRequest;
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
public class WalletControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_chargeBalance() {
        var request = new ChargeBalanceRequest(UUID.randomUUID(), 10000);

        ResponseEntity<ChargeBalanceReply> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ChargeBalanceReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isCharged());
    }

    @Test
    public void fail_chargeBalance_withZeroAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(UUID.randomUUID(), 0);

        ResponseEntity<ChargeBalanceReply> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ChargeBalanceReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isCharged());
    }

    @Test
    public void fail_chargeBalance_withNegativeAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(UUID.randomUUID(), -5000);

        ResponseEntity<ChargeBalanceReply> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ChargeBalanceReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isCharged());
    }


    @Test
    public void ok_showBalance_success() {
        ShowBalanceRequest request = new ShowBalanceRequest(UUID.randomUUID());

        ResponseEntity<ShowBalanceReply> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance",
                request,
                ShowBalanceReply.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().balance() >= 0);
    }

    @Test
    public void ok_chargeBalance_multipleRequest() {
        ChargeBalanceRequest request1 = new ChargeBalanceRequest(UUID.randomUUID(), 5000);
        ChargeBalanceRequest request2 = new ChargeBalanceRequest(UUID.randomUUID(), 3000);

        ResponseEntity<ChargeBalanceReply> response1 = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request1,
                ChargeBalanceReply.class
        );

        ResponseEntity<ChargeBalanceReply> response2 = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request2,
                ChargeBalanceReply.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertTrue(response1.getBody().isCharged());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertTrue(response2.getBody().isCharged());
    }

}