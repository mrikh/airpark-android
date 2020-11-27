package com.example.airpark.activities.Payments;

import androidx.annotation.NonNull;

import com.stripe.android.PaymentSessionConfig;
import com.stripe.android.model.Address;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.ShippingInformation;
import com.stripe.android.model.ShippingMethod;
import com.stripe.android.view.ShippingInfoWidget;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class StripeConfig {

    private PaymentSessionConfig createPaymentSessionConfig() {

        return new PaymentSessionConfig.Builder()
                .setHiddenShippingInfoFields(ShippingInfoWidget.CustomizableShippingField.Phone)
                .setOptionalShippingInfoFields(ShippingInfoWidget.CustomizableShippingField.Line2)
                .setPrepopulatedShippingInfo(new ShippingInformation(
                        new Address.Builder()
                                .setLine1("123 Market St")
                                .setCity("San Francisco")
                                .setState("CA")
                                .setPostalCode("94107")
                                .setCountry("US")
                                .build(),
                        "Jenny Rosen",
                        "4158675309"
                )).setShippingInfoRequired(true)
                .setShippingMethodsRequired(true)
                .setPaymentMethodTypes(Arrays.asList(PaymentMethod.Type.Card))
                .setAllowedShippingCountryCodes(new HashSet<>(Arrays.asList("IE")))
                .setShippingInformationValidator(new AppShippingInformationValidator())
                .setShippingMethodsFactory(new AppShippingMethodsFactory())
                .build();
    }

    private static class AppShippingInformationValidator
            implements PaymentSessionConfig.ShippingInformationValidator {

        @Override
        public boolean isValid(
                @NonNull ShippingInformation shippingInformation
        ) {
            final Address address = shippingInformation.getAddress();
            return address != null && Locale.US.getCountry() == address.getCountry();
        }

        @NonNull
        public String getErrorMessage(
                @NonNull ShippingInformation shippingInformation
        ) {
            return "A US address is required";
        }
    }

    private static class AppShippingMethodsFactory
            implements PaymentSessionConfig.ShippingMethodsFactory {

        @Override
        public List<ShippingMethod> create(
                @NonNull ShippingInformation shippingInformation
        ) {
            return Arrays.asList(
                    new ShippingMethod(
                            "UPS Ground",
                            "ups-ground",
                            0,
                            "USD",
                            "Arrives in 3-5 days"
                    ),
                    new ShippingMethod(
                            "FedEx",
                            "fedex",
                            599,
                            "USD",
                            "Arrives tomorrow"
                    )
            );
        }
    }
}
