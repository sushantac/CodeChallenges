package com.zpay.payments.billing.customersubscription.entity;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Sushant
 *
 *         { "id": "44335d51-265a-4e01-ad4d-306be659a48f", "amount": { "value":
 *         20, "currency": "AUD" }, "subscription_type": "WEEKLY",
 *         "invoice_dates": ["06/02/2018", "13/02/2018", "20/02/2018",
 *         "27/02/2018"] }
 */

@Entity
public class Subscription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@NotNull
	@Embedded
	private Amount amount;

	@NotNull
	@Enumerated(EnumType.STRING)
	private SubscriptionType subscription_type;

	@Min(value = 1)
	@Max(value = 31)
	private Integer date_of_month;

	@Enumerated(EnumType.STRING)
	private DayOfWeek day_of_week;

	@NotNull
	private LocalDate start_date;

	private LocalDate end_date;

	@Transient
	private List<LocalDate> invoice_dates = new ArrayList<>();

	public Subscription() {

	}

	public Subscription(Amount amount, SubscriptionType subscription_type, @Min(1) @Max(31) Integer date_of_month,
			DayOfWeek day_of_week, LocalDate start_date, LocalDate end_date) {
		super();
		this.amount = amount;
		this.subscription_type = subscription_type;
		this.date_of_month = date_of_month;
		this.day_of_week = day_of_week;
		this.start_date = start_date;
		this.end_date = end_date;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public SubscriptionType getSubscription_type() {
		return subscription_type;
	}

	public void setSubscription_type(SubscriptionType subscription_type) {
		this.subscription_type = subscription_type;
	}

	public Integer getDate_of_month() {
		return date_of_month;
	}

	public void setDate_of_month(Integer date_of_month) {
		this.date_of_month = date_of_month;
	}

	public DayOfWeek getDay_of_week() {
		return day_of_week;
	}

	public void setDay_of_week(DayOfWeek day_of_week) {
		this.day_of_week = day_of_week;
	}

	public LocalDate getStart_date() {
		return start_date;
	}

	public void setStart_date(LocalDate start_date) {
		this.start_date = start_date;
	}

	public LocalDate getEnd_date() {
		return end_date;
	}

	public void setEnd_date(LocalDate end_date) {
		this.end_date = end_date;
	}

	public List<LocalDate> getInvoice_dates() {

		int duration_months_max = 3;

		// Calculate and return invoice Dates
		LocalDate date_in_question = start_date;
		LocalDate qualifying_date = null;

		if (SubscriptionType.MONTHLY.equals(subscription_type)) {

			for (int i = 0; i < duration_months_max; i++) {
				
				//check the length of month
				if(date_in_question.lengthOfMonth() < date_of_month) {
					//get the last date of current month
					qualifying_date = LocalDate.of(date_in_question.getYear(), date_in_question.getMonth(), date_in_question.lengthOfMonth());
					
					//check if it is before the start date, if yes - get the first day of next month
					if(qualifying_date.isBefore(start_date)) {
						//now get the first date of next month, I don't want to miss a payment!
						qualifying_date = qualifying_date.plusDays(1);	
					}
					
				}else {
					qualifying_date = LocalDate.of(date_in_question.getYear(), date_in_question.getMonth(), date_of_month);
				}
				
				if ((start_date.isBefore(qualifying_date) || start_date.isEqual(qualifying_date))
						&& (end_date.isAfter(qualifying_date) || end_date.isEqual(qualifying_date))) {

					invoice_dates.add(qualifying_date);
				}
				
				
				//advance to next month
				date_in_question = qualifying_date.plusMonths(1);
			}

		} else if (SubscriptionType.WEEKLY.equals(subscription_type)) {

		}

		return invoice_dates;
	}

}
