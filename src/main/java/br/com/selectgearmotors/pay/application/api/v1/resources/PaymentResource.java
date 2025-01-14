package br.com.selectgearmotors.pay.application.api.v1.resources;

import br.com.selectgearmotors.pay.application.api.v1.dto.request.PaymentRequest;
import br.com.selectgearmotors.pay.application.api.v1.dto.response.PaymentResponse;
import br.com.selectgearmotors.pay.application.api.v1.mapper.PaymentApiMapper;
import br.com.selectgearmotors.pay.commons.util.RestUtils;
import br.com.selectgearmotors.pay.core.domain.PaymentDomain;
import br.com.selectgearmotors.pay.core.ports.in.PaymentPort;
import br.com.selectgearmotors.pay.core.ports.out.PaymentRepositoryPort;
import br.com.selectgearmotors.pay.gateway.payment.dto.PaymentApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/payments")
public class PaymentResource {

    private static final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private final PaymentPort paymentService;
    private final PaymentRepositoryPort paymentRepositoryPort;
    private final PaymentApiMapper paymentApiMapper;

    @Autowired
    public PaymentResource(PaymentPort paymentService, PaymentRepositoryPort paymentRepositoryPort, PaymentApiMapper paymentApiMapper) {
        this.paymentService = paymentService;
        this.paymentRepositoryPort = paymentRepositoryPort;
        this.paymentApiMapper = paymentApiMapper;
    }

    @Operation(summary = "Create a payment by Order", tags = {"payment", "post"})
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> save(@Valid @RequestBody PaymentRequest request) {
        log.info("Objeto Recebido na requisição: {}", request);
        Optional<PaymentResponse> paymentResponse = paymentService.processPayment(request);
        if (paymentResponse.isPresent()) {
            URI location = RestUtils.getUri(paymentResponse.get().id());
            return ResponseEntity.created(location).body(paymentResponse.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Retrieve a Order by Id",
            description = "Get a Order object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"orders", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> findOne(@PathVariable("id") Long id) {
        PaymentDomain orderSaved = paymentRepositoryPort.findById(id);
        if (orderSaved != null) {
            PaymentResponse orderResponse = paymentApiMapper.toPaymentResponse(orderSaved);
            return ResponseEntity.ok(orderResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve a Order by Id",
            description = "Get a Order object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"orders", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping("/{id}/update-status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentResponse> findByIdAndChangeStatus(@PathVariable("id") String id) { //TODO - Remover quando for para Produção
        PaymentDomain orderSaved = paymentRepositoryPort.findByPaymentId(id);
        orderSaved.setPaymentStatus("approved");
        paymentRepositoryPort.update(orderSaved.getId(), orderSaved);
        if (orderSaved != null) {
            PaymentResponse orderResponse = paymentApiMapper.toPaymentResponse(orderSaved);
            return ResponseEntity.ok(orderResponse);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Retrieve a Order by Id",
            description = "Get a Order object by specifying its id. The response is Association object with id, title, description and published status.",
            tags = {"orders", "get"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("/payment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PaymentApiResponse> findByPaymentId(@PathVariable("id") String id) {
        PaymentApiResponse paymentSaved = paymentService.getPaymentById(id);
        if (paymentSaved != null) {
           // PaymentResponse orderResponse = paymentApiMapper.toPaymentResponse(orderSaved);
            return ResponseEntity.ok(paymentSaved);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve all Payment", tags = {"clients", "get", "filter"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = PaymentResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "204", description = "There are no Payment", content = {
                    @Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<PaymentResponse>> findAll() {
        List<PaymentDomain> paymentDomainList = paymentRepositoryPort.findAll();
        List<PaymentResponse> clientResponse = paymentApiMapper.map(paymentDomainList);
        return clientResponse.isEmpty() ?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(clientResponse);
    }
}
