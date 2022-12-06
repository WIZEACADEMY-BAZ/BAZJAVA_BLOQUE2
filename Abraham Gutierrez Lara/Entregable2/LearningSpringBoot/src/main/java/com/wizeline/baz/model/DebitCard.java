package com.wizeline.baz.model;

import java.time.LocalDate;

import com.wizeline.baz.enums.DebitCardType;

public class DebitCard {
	
	private long cardNumber;
	private LocalDate expireDate;
	private int cvc;
	private DebitCardType type;
	
	public DebitCard() {
	}

	public DebitCard(long cardNumber, LocalDate expireDate, int cvc, DebitCardType type) {
		this.cardNumber = cardNumber;
		this.expireDate = expireDate;
		this.cvc = cvc;
		this.type = type;
	}
	
	public long getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(long cardNumber) {
		this.cardNumber = cardNumber;
	}
	public LocalDate getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}
	public int getCvc() {
		return cvc;
	}
	public void setCvc(int cvc) {
		this.cvc = cvc;
	}
	public DebitCardType getType() {
		return type;
	}
	public void setType(DebitCardType type) {
		this.type = type;
	}	
}
