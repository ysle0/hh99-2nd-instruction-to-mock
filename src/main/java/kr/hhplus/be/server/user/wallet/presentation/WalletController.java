package kr.hhplus.be.server.user.wallet.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.shared.api.ApiResponse;
import kr.hhplus.be.server.user.wallet.app.WalletService;
import kr.hhplus.be.server.user.wallet.presentation.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.user.wallet.presentation.dto.ChargeBalanceResponse;
import kr.hhplus.be.server.user.wallet.presentation.dto.ShowBalanceRequest;
import kr.hhplus.be.server.user.wallet.presentation.dto.ShowBalanceResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/wallets")
@Tag(name = "wallet", description = "지갑 API")
public class WalletController {

    WalletService walletService;

    @PostMapping("/balance/charge")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저의 지갑에 일정 금액을 충전하기", description = "충전금액은 0보다 커야함.")
    public ApiResponse<ChargeBalanceResponse> chargeBalance(
            @RequestBody ChargeBalanceRequest r) {
        ChargeBalanceResponse resp = walletService.chargeBalance(r.userID(), r.newBalance());
        return resp.Ok();
    }

    @PostMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "유저의 지갑에 있는 잔액을 조회하기")
    public ApiResponse<ShowBalanceResponse> showBalance(
            @RequestBody ShowBalanceRequest r) {
        ShowBalanceResponse resp = walletService.showBalance(r.userID());

        return resp.Ok();
    }
}
