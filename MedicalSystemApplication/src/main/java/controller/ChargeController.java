package controller;

import com.mks.api.response.APIException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import model.ChargeRequest;
import model.ChargeRequest.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.StripeService;

@Slf4j
@RestController
@RequestMapping(value = "api/charge")
@Api
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    @ApiOperation("Оформление оплаты заказов")
    public String charge(ChargeRequest chargeRequest, Model model) throws StripeException, APIException {
        log.info("Making payment for an order in the amount of '{}'.", chargeRequest.getAmount());
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(Currency.EUR);
        chargeRequest.setCurrency(Currency.USD);
        chargeRequest.setCurrency(Currency.RUB);
        chargeRequest.setCurrency(Currency.CNY);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}

