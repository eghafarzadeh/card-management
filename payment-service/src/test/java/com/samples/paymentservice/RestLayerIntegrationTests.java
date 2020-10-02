package com.samples.paymentservice;

import com.samples.paymentservice.persistance.entity.*;
import com.samples.paymentservice.persistance.repository.CardJpaRepository;
import com.samples.paymentservice.persistance.repository.TransactionJpaRepository;
import com.samples.paymentservice.persistance.repository.UserJpaRepository;
import com.samples.paymentservice.rest.model.CardAuthenticationInfo;
import com.samples.paymentservice.rest.model.CardModel;
import com.samples.paymentservice.rest.model.CardTransferRequestModel;
import com.samples.paymentservice.rest.model.TransactionReportModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.Matchers.containsString;
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
public class RestLayerIntegrationTests {

    private static final String CARD_BASE_URL = "/api/card";
    private static final String TRANSACTION_BASE_URL = "/api/transaction";
    private static final String APPLICATION_HAL_JSON = "application/hal+json";
    private static final String DESTINATION_TEST_PAN = "1234567812345678";
    private static final String SOURCE_TEST_PAN_1 = "1234123412341234";
    private static final String SOURCE_TEST_PAN_2 = "6037123400000000";
    private static final String AUTHORIZATION_HEADER_TEST_VALUE = "Basic ZS5naGFmYXJ6YWRlaDpwYXNzd29yZA==";
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CardJpaRepository cardRepository;
    @Autowired
    private UserJpaRepository userRepository;
    @Autowired
    private TransactionJpaRepository transactionRepository;

    @AfterEach
    @BeforeEach
    public void resetDb() {
        transactionRepository.deleteAll();
        cardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void listCards() throws Exception {
        createTestCard();
        this.mockMvc.perform(get(CARD_BASE_URL).header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_HAL_JSON))
                .andExpect(content().string(containsString("\"pan\":\"" + SOURCE_TEST_PAN_1 + "\"")));
    }

    @Test
    public void addCard() throws Exception {
        createTestUser();
        CardModel cardModel = new CardModel();
        cardModel.setPan(SOURCE_TEST_PAN_1);
        this.mockMvc.perform(post(CARD_BASE_URL).header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE)
                .content(JsonUtil.toJson(cardModel))
                .contentType(APPLICATION_HAL_JSON)).andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString(SOURCE_TEST_PAN_1)));
    }

    @Test
    public void deleteCard() throws Exception {
        Card card = createTestCard();
        this.mockMvc.perform(delete(CARD_BASE_URL + "/" + card.getId()).header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE))
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteNotFoundCard() throws Exception {
        this.mockMvc.perform(delete(CARD_BASE_URL + "/2").header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void transferWithPayment1() throws Exception {
        Card card = createTestCard(SOURCE_TEST_PAN_2);
        CardTransferRequestModel cardTransferRequestModel = new CardTransferRequestModel();
        cardTransferRequestModel.setAmount(BigDecimal.valueOf(1000));
        cardTransferRequestModel.setCardId(card.getId());
        cardTransferRequestModel.setDestinationPan(DESTINATION_TEST_PAN);
        CardAuthenticationInfo cardAuthenticationInfo = new CardAuthenticationInfo();
        cardAuthenticationInfo.setPan(SOURCE_TEST_PAN_2);
        cardAuthenticationInfo.setPin("123456");
        cardAuthenticationInfo.setCvv2("1234");
        cardAuthenticationInfo.setExpMonth(1);
        cardAuthenticationInfo.setExpMonth(99);
        cardTransferRequestModel.setCardAuthenticationInfo(cardAuthenticationInfo);
        this.mockMvc.perform(post(CARD_BASE_URL + "/transfer").header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE)
                .content(JsonUtil.toJson(cardTransferRequestModel))
                .contentType(APPLICATION_HAL_JSON)).andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString(SOURCE_TEST_PAN_2))).
                andExpect(content().string(containsString(PaymentClientName.PAYMENT1.name())));
    }

    @Test
    public void transferWithPayment2() throws Exception {
        String pan = SOURCE_TEST_PAN_1;
        Card card = createTestCard(pan);
        CardTransferRequestModel cardTransferRequestModel = new CardTransferRequestModel();
        cardTransferRequestModel.setAmount(BigDecimal.valueOf(1000));
        cardTransferRequestModel.setCardId(card.getId());
        cardTransferRequestModel.setDestinationPan(DESTINATION_TEST_PAN);
        CardAuthenticationInfo cardAuthenticationInfo = new CardAuthenticationInfo();
        cardAuthenticationInfo.setPan(pan);
        cardAuthenticationInfo.setPin("123456");
        cardAuthenticationInfo.setCvv2("1234");
        cardAuthenticationInfo.setExpMonth(1);
        cardAuthenticationInfo.setExpMonth(99);
        cardTransferRequestModel.setCardAuthenticationInfo(cardAuthenticationInfo);
        this.mockMvc.perform(post(CARD_BASE_URL + "/transfer").header("Authorization", AUTHORIZATION_HEADER_TEST_VALUE).content(JsonUtil.toJson(cardTransferRequestModel))
                .contentType(APPLICATION_HAL_JSON)).andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString(pan))).
                andExpect(content().string(containsString(PaymentClientName.PAYMENT2.name())));
    }

    @Test
    public void transactionReport() throws Exception {
        createTestTransfer();
        String from = "2000-10-31T01:30:00.000-05:00";
        String to = "2021-10-31T01:30:00.000-05:00";
        TransactionReportModel transactionReportModel = new TransactionReportModel(SOURCE_TEST_PAN_2, 1, 0);
        this.mockMvc.perform(get(TRANSACTION_BASE_URL + "/" + from + "/" + to).header(AUTHORIZATION_HEADER_NAME, AUTHORIZATION_HEADER_TEST_VALUE))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(APPLICATION_HAL_JSON))
                .andExpect(content().string(containsString("2000-10-31T06:30:00.000+00:00")))
                .andExpect(content().string(containsString("2021-10-31T06:30:00.000+00:00")))
                .andExpect(content().string(containsString(JsonUtil.toJsonString(transactionReportModel))));
    }

    private Card createTestCard(String pan) {
        User user = createTestUser();
        Card card = new Card(100L, pan, user);
        return cardRepository.save(card);
    }

    private Card createTestCard() {
        return createTestCard(SOURCE_TEST_PAN_1);
    }

    private User createTestUser() {
        User user = new User(100L, "Test", "Test_lastName", "e.ghafarzadeh", "{scrypt}$e0801$gDHgl1QxdziQMyLqGQwsgp93aKRavAMxsOYKxKTXy1YqBey2y71P445xwV6Wi2CyAJjaSmfXz3yn987ITDxQCA==$nwZ2/k2TLowY7hcybKv22TVqG2d6+EMFTz0u+cp8TTo=", "09121111111", "0312345678", new Date(), null);
        return userRepository.save(user);
    }

    private void createTestTransfer() {
        Card card = createTestCard(SOURCE_TEST_PAN_2);
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(1000));
        transaction.setDestination(DESTINATION_TEST_PAN);
        transaction.setSource(SOURCE_TEST_PAN_2);
        transaction.setPaymentClientName(PaymentClientName.PAYMENT1);
        transaction.setReferenceNumber("123456");
        transaction.setStatus(TransactionStatus.SUCCESSFUL);
        transaction.setTransactionDate(new Date());
        transaction.setUser(card.getUser());
        transaction = transactionRepository.save(transaction);
    }
}
