package kr.hhplus.be.server.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.util.RandomUtil;
import kr.hhplus.be.server.web.dto.ChargeBalanceReply;
import kr.hhplus.be.server.web.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.web.dto.ShowBalanceReply;
import kr.hhplus.be.server.web.dto.ShowBalanceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(name = "wallet", description = "지갑 API")
public class WalletController {
    @PostMapping("/balance/charge")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저의 지갑에 일정 금액을 충전하기", description = "충전금액은 0보다 커야함.")
    public ChargeBalanceReply chargeBalance(
            @RequestBody ChargeBalanceRequest r) {
        if (r.newBalance() <= 0) {
            return new ChargeBalanceReply(false);
        }

        return new ChargeBalanceReply(true);
    }

    @PostMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저의 지갑에 있는 잔액을 조회하기")
    public ShowBalanceReply showBalance(
            @RequestBody ShowBalanceRequest r) {
        final int rndBalance = RandomUtil.Range(300_000, 0);
        return new ShowBalanceReply(rndBalance);
    }
}
