package com.javatraining.code;

import javax.validation.constraints.NotNull;


/**********************************************************************
 * Account object class
 *
 * @author Charlie Leopard
 *********************************************************************/
final public class Account {
    @NotNull
    private final String accountName;
    @NotNull
    private final String password;
    @NotNull
    private final String token;

    /**
     * Construct an Account object with accountName and password to authenticate users and make orders from
     *
     * @param accountName the account name
     * @param password    the password for this account
     */
    public Account(String accountName, String password, String token) {
        this.accountName = accountName;
        this.password = password;
        this.token = token;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Acc: " + accountName + ", Password: " + password;
    }

    /**
     * Returns the field accountName
     *
     * @return Account's <Code>String accountName</Code>
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * Returns the field password
     *
     * @return Account's <Code>String password</Code>
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the field token
     *
     * @return Account's <Code>String token</Code>
     */
    public String getToken() {
        return token;
    }
}