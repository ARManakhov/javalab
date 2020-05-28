package ru.itis.services;

import org.springframework.stereotype.Service;
import ru.itis.models.Subscription;
@Service
public interface SubscriptionService {
    boolean subscribe(Subscription subscription);
}
