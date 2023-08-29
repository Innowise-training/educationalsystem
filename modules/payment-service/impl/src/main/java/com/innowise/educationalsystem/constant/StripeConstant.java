package com.innowise.educationalsystem.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StripeConstant {
    public static final String STRIPE_SECRET_KEY_PROPERTY = "${stripe.secret-key}";
    public static final String STRIPE_WEBHOOK_KEY_PROPERTY = "${stripe.webhook-key}";
}
