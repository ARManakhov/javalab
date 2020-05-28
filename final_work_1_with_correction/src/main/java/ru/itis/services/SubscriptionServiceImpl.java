package ru.itis.services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.models.Subscription;
import ru.itis.repositories.SubscriptionRepository;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    SubscriptionRepository subscriptionRepository;
    @Override
    public boolean subscribe(Subscription subscription) {
        subscriptionRepository.save(subscription);
        return true;
    }
}
