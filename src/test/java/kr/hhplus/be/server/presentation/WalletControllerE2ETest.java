package kr.hhplus.be.server.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.TestcontainersConfiguration;
import kr.hhplus.be.server.shared.Messages;
import kr.hhplus.be.server.shared.api.ApiResponse;
import kr.hhplus.be.server.user.domain.misc.WalletMessages;
import kr.hhplus.be.server.user.presentation.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.user.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.presentation.dto.ShowBalanceRequest;
import kr.hhplus.be.server.user.presentation.dto.ShowBalanceResponse;
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
public class WalletControllerE2ETest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ok_chargeBalance() {
        var request = new ChargeBalanceRequest(100L, 10000);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceResponse data = mapper.convertValue(response.getBody().getData(), ChargeBalanceResponse.class);
        assertTrue(data.isCharged());
    }

    @Test
    public void fail_chargeBalance_withZeroAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(100L, 0);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_INVALID_AMOUNT, response.getBody().getMessage());
        assertEquals(Messages.NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceResponse data = mapper.convertValue(response.getBody().getData(), ChargeBalanceResponse.class);
        assertFalse(data.isCharged());
    }

    @Test
    public void fail_chargeBalance_withNegativeAmount() {
        ChargeBalanceRequest request = new ChargeBalanceRequest(100L, -5000);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance/charge",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.CHARGE_INVALID_AMOUNT, response.getBody().getMessage());
        assertEquals(Messages.NO, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceResponse data = mapper.convertValue(response.getBody().getData(), ChargeBalanceResponse.class);
        assertFalse(data.isCharged());
    }


    @Test
    public void ok_showBalance_success() {
        ShowBalanceRequest request = new ShowBalanceRequest(100L);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
                "/api/v1/wallets/balance",
                request,
                ApiResponse.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(WalletMessages.BALANCE_QUERY_SUCCESS, response.getBody().getMessage());
        assertEquals(Messages.OK, response.getBody().getCode());
        assertNotNull(response.getBody().getData());

        ObjectMapper mapper = new ObjectMapper();
        ShowBalanceResponse data = mapper.convertValue(response.getBody().getData(), ShowBalanceResponse.class);
        assertTrue(data.balance() >= 0);
    }

    @Test
    public void ok_chargeBalance_multipleRequest() {
        ChargeBalanceRequest request1 = new ChargeBalanceRequest(100L, 5000);
        ChargeBalanceRequest request2 = new ChargeBalanceRequest(100L, 3000);

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
        assertEquals(WalletMessages.CHARGE_SUCCESS, response1.getBody().getMessage());
        assertEquals(Messages.OK, response1.getBody().getCode());
        ObjectMapper mapper = new ObjectMapper();
        ChargeBalanceResponse data1 = mapper.convertValue(response1.getBody().getData(), ChargeBalanceResponse.class);
        assertTrue(data1.isCharged());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertNotNull(response2.getBody());
        assertEquals(WalletMessages.CHARGE_SUCCESS, response2.getBody().getMessage());
        assertEquals(Messages.OK, response2.getBody().getCode());
        ChargeBalanceResponse data2 = mapper.convertValue(response2.getBody().getData(), ChargeBalanceResponse.class);
        assertTrue(data2.isCharged());
    }

}