
package com.eeker.UsersService.query;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import com.appsdeveloperblog.estore.core.model.PaymentDetails;
import com.appsdeveloperblog.estore.core.model.User;
import com.appsdeveloperblog.estore.core.query.FetchUserPaymentDetailsQuery;

@Component
public class UserEventsHandler {

    @QueryHandler
    public User findUserPaymentDetails(FetchUserPaymentDetailsQuery query) {

        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("4242424242424242")
                .cvv("123")
                .name("Ali Veli")
                .validUntilMonth(12)
                .validUntilYear(2045)
                .build();

        User user = User.builder()
                .firstName("Ali")
                .lastName("Veli")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();

        return user;
    }

}
