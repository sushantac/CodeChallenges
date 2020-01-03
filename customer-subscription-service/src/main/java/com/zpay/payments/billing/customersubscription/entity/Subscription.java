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
		
		Integer duration_max_months_allowed = 3;
		
		//Calculate and return invoice Dates
		LocalDate s_date = start_date;
		LocalDate e_date = end_date;
		
		LocalDate qualifying_date = start_date;
		if(SubscriptionType.MONTHLY.equals(subscription_type)) {
			
			int month_counter = 0;
			
			while(qualifying_date.isBefore(end_date)) {
				
				boolean is_valid_date = true;
				try {
					qualifying_date = LocalDate.of(start_date.getYear(), start_date.getMonth().ordinal() + month_counter, date_of_month);
				}catch (DateTimeException e) {
					//not a valid date, ignore it and continue to check next month
					
					is_valid_date = false;
				}
				
				if(is_valid_date) {
					invoice_dates.add(qualifying_date);
				}
				
							
				
			}
			
			
		}else if(SubscriptionType.WEEKLY.equals(subscription_type)) {
			
		}
				
		return invoice_dates;
	}


}
