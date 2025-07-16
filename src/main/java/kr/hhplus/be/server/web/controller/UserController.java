package kr.hhplus.be.server.web.controller;

import kr.hhplus.be.server.domain.wallet.WalletService;
import kr.hhplus.be.server.web.dto.ChargeBalanceReply;
import kr.hhplus.be.server.web.dto.ChargeBalanceRequest;
import kr.hhplus.be.server.web.dto.ShowBalanceReply;
import kr.hhplus.be.server.web.dto.ShowBalanceRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    final WalletService walletService;

    public UserController(
            WalletService ws
    ) {
        this.walletService = ws;

    }

    @PostMapping("/wallet")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ChargeBalanceReply>
    chargeBalance(
            @RequestBody ChargeBalanceRequest r
    ) {
        var reply = walletService.chargeBalance(r);

        return ResponseEntity.ok(reply);
    }

    @GetMapping("/wallet")
    @ResponseStatus(HttpStatus.OK)
    public ShowBalanceReply
    showBalance(
            @RequestBody ShowBalanceRequest r
    ) {
        var reply = walletService.showBalance(r);

        return reply;
    }
}
