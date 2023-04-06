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
@Root(name = Constants.PN_REQUEST, strict = false)
public class FundsTransferRequestModel {


    @Element(name = Constants.PN_DIRECTION, required = false)
    /*Value = "request” ,always static.*/
    private String direction;

    @Element(name = Constants.PN_ACTION, required = false)
    /*Defines the action request type. See Appendix
    A for the list of all action types. i.e. FT*/
    private String action;


    @Element(name = Constants.PN_TERMINAL_ID, required = false)
    /*Client Identification Number, connected to the
     client's account number*/
    private String terminalId;

    @Element(name = Constants.PN_TRANSACTION, required = false)
    private TransactionModel transaction;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }


    public TransactionModel getTransaction() {
        return transaction;
    }

    public void setTransaction(TransactionModel transaction) {
        this.transaction = transaction;
    }
}
