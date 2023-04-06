/*
 * Copyright 2016. Alejandro Sánchez
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.woleapp.network.soap.request;

import com.woleapp.util.Constants;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Class Description.
 *
 * @author asanchezyu@gmail.com.
 * @version 1.0.
 * @since 15/6/16.
 */
@Root(name = Constants.PN_TRANSACTION, strict = false)
public class TransactionModel {

    @Element(name = Constants.PN_PIN, required = false)
    /*Pin of the source card that is linked to the terminal
    identification number.*/
    private String pin;

    @Element(name = Constants.PN_BANK_CODE, required = false)
    /*identifies the bank of the destination
    account number, usually of 3 digits*/
    private String bankCode;

    @Element(name = Constants.PN_AMOUNT, required = false)
    private double amount;

    @Element(name = Constants.PN_CURRENCY, required = false)
    /*identifies the currency code for the amount value
    e.g NGN, GHS, GBP or USD. The default value is NGN
    (naira) if not specified*/
    private String currency; /*Optional*/

    @Element(name = Constants.PN_DESCRIPTION, required = false)
    private String description;

    @Element(name = Constants.PN_DESTINATION, required = false)
    /*holds the destination account number*/
    private String destination;


    @Element(name = "lineType", required = false)
    /*holds the destination account number*/
    private String lineType;

    @Element(name = Constants.PN_REFERENCE, required = false)
    /*unique transaction id generated per
    transaction by client and should be between 10 and
    25 digits*/
    private String reference; /*Mandatory*/

    @Element(name = "senderName", required = false)
    /*should contain information
    about sender and recipient in syntax
    senderName:recipient_bank:recipient_name*/
    private String senderName;

    @Element(name = "endPoint", required = false)
    /*identifies the destination endpoint type,
     it is A for Accounts and M for mobile payments
     (Wallets)*/
    private String endPoint;


    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
