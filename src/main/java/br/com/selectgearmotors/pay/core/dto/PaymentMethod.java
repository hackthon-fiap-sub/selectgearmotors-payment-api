package br.com.selectgearmotors.pay.core.dto;

public enum PaymentMethod {
    PIX("pix"),
    CREDIT_CARD("credit_card"),
    BOLETO("boleto");

    private String method;

    PaymentMethod(String method) {
        this.method = method;
    }
    public String getMethod() {
        return method;
    }


}
