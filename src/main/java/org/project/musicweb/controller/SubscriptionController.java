package org.project.musicweb.controller;

import org.project.musicweb.repository.SubscriptionRepository;
import org.springframework.stereotype.Controller;

@Controller
public class SubscriptionController {
    private final SubscriptionRepository subscriptionRepository;
    public SubscriptionController(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }
}
