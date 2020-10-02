package com.samples.paymentservice;

import com.samples.paymentservice.persistance.entity.PaymentClientName;
import com.samples.paymentservice.persistance.entity.TransactionStatus;
import com.samples.paymentservice.rest.model.CardAuthenticationInfo;
import com.samples.paymentservice.rest.model.CardModel;
import com.samples.paymentservice.rest.model.CardTransferRequestModel;
import com.samples.paymentservice.rest.model.TransactionReportModel;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.dto.TransactionReportDto;
import com.samples.paymentservice.service.inf.CardService;
import com.samples.paymentservice.service.inf.TransactionService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Elham
 * @since 10/2/2020
 */
@SpringBootTest
@AutoConfigureMockMvc
public class RestLayerUnitTests {
    /*todo: complete unit tests*/

    private static final String CARD_BASE_URL = "/api/card";
    private static final String TRANSACTION_BASE_URL = "/api/transaction";
    private static final String APPLICATION_HAL_JSON = "application/hal+json";
    private static final String DESTINATION_TEST_PAN = "1234567812345678";
    private static final String SOURCE_TEST_PAN_1 = "1234123412341234";
    private static final String SOURCE_TEST_PAN_2 = "6037123400000000";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CardService cardService;

    @MockBean
    private TransactionService transactionService;

    @Test
    @WithMockUser(username = "e.ghafarzadeh", authorities = {"ADMIN", "USER"})
    public void getAllCards() throws Exception {

        List<CardDto> cardDtos = new ArrayList<>();
        CardDto cardDto = new CardDto();
        cardDto.setId(1L);
        cardDto.setPan(SOURCE_TEST_PAN_1);
        cardDtos.add(cardDto);

        when(cardService.findAll(ArgumentMatchers.any())).thenReturn(cardDtos);
        this.mockMvc.perform(get(CARD_BASE_URL))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_HAL_JSON))
                .andExpect(content().string(containsString("\"pan\":\"" + SOURCE_TEST_PAN_1 + "\"")));
    }

    @Test
    @WithMockUser(username = "e.ghafarzadeh", authorities = {"ADMIN", "USER"})
    public void addCard() throws Exception {
        CardDto cardDto = new CardDto(1L, SOURCE_TEST_PAN_1);
        when(cardService.addCard(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(cardDto);
        CardModel cardModel = new CardModel();
        cardModel.setPan(SOURCE_TEST_PAN_1);
        this.mockMvc.perform(post(CARD_BASE_URL)
                .content(JsonUtil.toJson(cardModel))
                .contentType(APPLICATION_HAL_JSON)).andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString(SOURCE_TEST_PAN_1)));
    }

    @Test
    @WithMockUser(username = "e.ghafarzadeh", authorities = {"ADMIN", "USER"})
    public void deleteCard() throws Exception {
        doNothing().when(cardService).deleteById(ArgumentMatchers.any(), ArgumentMatchers.any());
        this.mockMvc.perform(delete(CARD_BASE_URL + "/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "e.ghafarzadeh", authorities = {"ADMIN", "USER"})
    public void transfer() throws Exception {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(1000));
        transactionDto.setTransactionDate(new Date());
        transactionDto.setDestination(DESTINATION_TEST_PAN);
        transactionDto.setPaymentClientName(PaymentClientName.PAYMENT1);
        transactionDto.setSource(SOURCE_TEST_PAN_1);
        transactionDto.setStatus(TransactionStatus.SUCCESSFUL);
        when(cardService.cardToCardTransfer(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(transactionDto);

        CardTransferRequestModel cardTransferRequestModel = new CardTransferRequestModel();
        cardTransferRequestModel.setAmount(BigDecimal.valueOf(1000));
        cardTransferRequestModel.setCardId(100L);
        cardTransferRequestModel.setDestinationPan(DESTINATION_TEST_PAN);
        CardAuthenticationInfo cardAuthenticationInfo = new CardAuthenticationInfo();
        cardAuthenticationInfo.setPan(SOURCE_TEST_PAN_2);
        cardAuthenticationInfo.setPin("123456");
        cardAuthenticationInfo.setCvv2("1234");
        cardAuthenticationInfo.setExpMonth(1);
        cardAuthenticationInfo.setExpMonth(99);
        cardTransferRequestModel.setCardAuthenticationInfo(cardAuthenticationInfo);
        this.mockMvc.perform(post(CARD_BASE_URL + "/transfer")
                .content(JsonUtil.toJson(cardTransferRequestModel))
                .contentType(APPLICATION_HAL_JSON)).andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString(SOURCE_TEST_PAN_1))).
                andExpect(content().string(containsString(PaymentClientName.PAYMENT1.name())));
    }

    @Test
    @WithMockUser(username = "e.ghafarzadeh", authorities = {"ADMIN", "USER"})
    public void transactionReport() throws Exception {
        List<TransactionReportDto> transactionReportDtos = new ArrayList<>();
        TransactionReportDto transactionReportDto = new TransactionReportDto(SOURCE_TEST_PAN_1, 10L, 5L);
        transactionReportDtos.add(transactionReportDto);
        when(transactionService.getTransactionReports(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(transactionReportDtos);

        String from = "2000-10-31T01:30:00.000-05:00";
        String to = "2021-10-31T01:30:00.000-05:00";
        TransactionReportModel transactionReportModel = new TransactionReportModel(SOURCE_TEST_PAN_1, 10, 5);
        this.mockMvc.perform(get(TRANSACTION_BASE_URL + "/" + from + "/" + to))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_HAL_JSON))
                .andExpect(content().string(containsString("2000-10-31T06:30:00.000+00:00")))
                .andExpect(content().string(containsString("2021-10-31T06:30:00.000+00:00")))
                .andExpect(content().string(containsString(JsonUtil.toJsonString(transactionReportModel))));
    }
}
