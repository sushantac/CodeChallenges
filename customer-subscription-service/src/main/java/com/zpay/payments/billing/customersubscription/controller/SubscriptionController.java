package com.zpay.payments.billing.customersubscription.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zpay.payments.billing.customersubscription.entity.Subscription;
import com.zpay.payments.billing.customersubscription.entity.SubscriptionRepository;


@RestController
public class SubscriptionController {

	@Autowired
	SubscriptionRepository subscriptionRepository;

	@RequestMapping(path = "/subscriptions", method = RequestMethod.GET)
	public List<Subscription> getSubscriptions() {
		return subscriptionRepository.findAll();
	}
	
	@RequestMapping(path = "/subscriptions/{id}", method = RequestMethod.GET)
	public Subscription getSubscriptionById(@PathVariable UUID id) {
		
		
		Optional<Subscription> optional = subscriptionRepository.findById(id);
		
		if(optional == null) {
			throw new RuntimeException("Subscription not found");
		}
		
		return optional.get();
	}

	@RequestMapping(path = "/subscriptions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void createSubscription(@RequestBody Subscription subscription) {

		subscriptionRepository.save(subscription);
		
	}

	@RequestMapping(path = "/subscriptions", method = RequestMethod.PUT)
	public String updateSubscription(@RequestBody Subscription subscription) {
		return "subscribed!";
	}

	@RequestMapping(path = "/subscriptions", method = RequestMethod.DELETE)
	public String deleteSubscription(@RequestBody Subscription subscription) {
		return "subscribed!";
	}

}

/*
 * Your team is building a recurring payment system and you need to build the
 * subscription module. A subscription is an object which tells the system when
 * to bill the customer and includes all the details needed to create an invoice
 * to bill the customer: a. The amount to charge per invoice b. How often to
 * charge the customer – daily, weekly or monthly a. If its weekly, the day of
 * the week - i.e. TUESDAY b. If it’s monthly, the date of the month - i.e. 20
 * c. The start and end date of the entire subscription, with a maximum duration
 * of 3 months
 * 
 * Create a simple Java web app which has a single endpoint to allow users to
 * create a subscription. The endpoint accepts as its input the information
 * described above, saves it in an in-memory data structure and returns a
 * subscription object that holds: 1. the amount to charge per billing 2. the
 * invoice type 3. all the dates that an invoice will be created
 * 
 * Example: if we created a $20 weekly subscription that will charge a customer
 * every Tuesday from the 1st of Feb 2018 till the 1st of March 2018, the
 * response would be as follows: { "id": "44335d51-265a-4e01-ad4d-306be659a48f",
 * "amount": { "value": 20, "currency": "AUD" }, "subscription_type": "WEEKLY",
 * "invoice_dates": ["06/02/2018", "13/02/2018", "20/02/2018", "27/02/2018"] }
 * 
 */