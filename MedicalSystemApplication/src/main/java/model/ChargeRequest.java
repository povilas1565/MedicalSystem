package model;

import lombok.Data;

import javax.persistence.*;


@Entity
public class ChargeRequest {

    public enum Currency {
        EUR, USD, RUB, CNY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "currency", nullable = false)
    private Currency currency;

    @Column(name = "stripeEmail", nullable = false)
    private String stripeEmail;

    @Column(name = "stripeToken", nullable = false)
    private String stripeToken;

    public ChargeRequest() {
        super();
    }

    public ChargeRequest(String description, int amount, Currency currency, String stripeEmail, String stripeToken) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.stripeEmail = stripeEmail;
        this.stripeToken = stripeToken;
    }

    public ChargeRequest(ChargeRequest req)
    {
        this.description = req.getDescription();
        this.amount = req.getAmount();
        this.currency = req.getCurrency();
        this.stripeEmail = req.getStripeEmail();
        this.stripeToken = req.getStripeToken();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }


    public String getStripeEmail() {
        return stripeEmail;
    }

    public void setStripeEmail(String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }


    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

}
