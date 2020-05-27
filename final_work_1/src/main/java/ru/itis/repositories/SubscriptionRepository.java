package ru.itis.repositories;

import ru.itis.models.PostLike;
import ru.itis.models.Subscription;

import java.util.Optional;

public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {
    Optional<Subscription> findByEntity(Subscription subscription);
}
