package com.zpay.payments.billing.customersubscription.entity;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, String>{

	public Optional<Subscription> findById(UUID id);
	
}
