package controller;

import com.mks.api.response.APIException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import model.ChargeRequest;
import model.ChargeRequest.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import service.StripeService;

@RestController
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @PostMapping(value ="/charge")
    public String charge(ChargeRequest chargeRequest, Model model)
            throws StripeException, APIException {
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

