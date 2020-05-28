package ru.itis.repositories;

import ru.itis.models.Subscription;

import java.util.Optional;

public abstract class SubscriptionRepository extends CrudRepository<Subscription, Long> {

    protected SubscriptionRepository() {
        super(Subscription.class);
    }

    abstract Optional<Subscription> findByEntity(Subscription subscription);
}
