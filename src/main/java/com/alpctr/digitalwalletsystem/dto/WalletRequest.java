package com.alpctr.digitalwalletsystem.dto;

import com.alpctr.enums.Currency;

public class WalletRequest {

	public WalletRequest() {
		// TODO Auto-generated constructor stub
	}
	
    private Long customerId;
    private String walletName;
    private Currency currency;
    private boolean activeForShopping;
    private boolean activeForWithdraw;
    
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getWalletName() {
		return walletName;
	}
	public void setWalletName(String walletName) {
		this.walletName = walletName;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public boolean isActiveForShopping() {
		return activeForShopping;
	}
	public void setActiveForShopping(boolean activeForShopping) {
		this.activeForShopping = activeForShopping;
	}
	public boolean isActiveForWithdraw() {
		return activeForWithdraw;
	}
	public void setActiveForWithdraw(boolean activeForWithdraw) {
		this.activeForWithdraw = activeForWithdraw;
	}

}
