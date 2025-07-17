package kr.hhplus.be.server.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.domain.Messages;
import kr.hhplus.be.server.domain.wallet.WalletMessages;
import kr.hhplus.be.server.web.dto.*;
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

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.CODE_OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceReply data = mapper.convertValue(response.getBody().getData(), ChargeBalanceReply.class);
        assertTrue(data.isCharged());
    }

    @Test
    public void fail_chargeBalance_withZeroAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(UUID.randomUUID(), 0);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_INVALID_AMOUNT, response.getBody().getMessage());
        assertEquals(Messages.CODE_NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceReply data = mapper.convertValue(response.getBody().getData(), ChargeBalanceReply.class);
        assertFalse(data.isCharged());
    }

    @Test
    public void fail_chargeBalance_withNegativeAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(UUID.randomUUID(), -5000);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_INVALID_AMOUNT, response.getBody().getMessage());
        assertEquals(Messages.CODE_NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceReply data = mapper.convertValue(response.getBody().getData(), ChargeBalanceReply.class);
        assertFalse(data.isCharged());
    }


    @Test
    public void ok_showBalance_success() {
        ShowBalanceRequest request = new ShowBalanceRequest(UUID.randomUUID());

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.BALANCE_QUERY_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.CODE_OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ShowBalanceReply data = mapper.convertValue(response.getBody().getData(), ShowBalanceReply.class);
        assertTrue(data.balance() >= 0);
    }

    @Test
    public void ok_chargeBalance_multipleRequest() {
        ChargeBalanceRequest request1 = new ChargeBalanceRequest(UUID.randomUUID(), 5000);
        ChargeBalanceRequest request2 = new ChargeBalanceRequest(UUID.randomUUID(), 3000);

        ResponseEntity<ApiResponse> response1 = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request1,
                ApiResponse.class
        );

        ResponseEntity<ApiResponse> response2 = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request2,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertNotNull(response1.getBody());
        assertEquals("금액 충전을 완료했습니다.", response1.getBody().getMessage());
        assertEquals("ok", response1.getBody().getCode());
        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceReply data1 = mapper.convertValue(response1.getBody().getData(), ChargeBalanceReply.class);
        assertTrue(data1.isCharged());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals("금액 충전을 완료했습니다.", response2.getBody().getMessage());
        assertEquals("ok", response2.getBody().getCode());
        ChargeBalanceReply data2 = mapper.convertValue(response2.getBody().getData(), ChargeBalanceReply.class);
        assertTrue(data2.isCharged());
    }

}