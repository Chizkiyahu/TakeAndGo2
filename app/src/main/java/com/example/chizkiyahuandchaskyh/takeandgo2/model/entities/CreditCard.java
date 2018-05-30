package com.example.chizkiyahuandchaskyh.takeandgo2.model.entities;

import java.io.Serializable;
import java.util.Date;

public class CreditCard implements Serializable {

    public enum Issuer {
        MASTER_CARD {
            @Override
            public String toString() {
                return "MasterCard";
            }
        },
        VISA {
            @Override
            public String toString() {
                return "Visa";
            }
        },
        AMERICAN_EXPRESS {
            @Override
            public String toString() {
                return "American Express";
            }
        }
    }

    public int getId() {
        return id;
    }

    private int id;
    private int customerID;
    private String digits;
    private Date expiration;
    private String cvv;
    private Issuer issuer;


    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public CreditCard(String digits, Issuer issuer, Date expiration, String cvv) {
        this.digits = digits;
        this.expiration = expiration;
        this.cvv = cvv;
        this.issuer = issuer;
    }

    public String getDigits() {
        return digits;
    }

    public void setDigits(String digits) {
        this.digits = digits;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }


    // this function validates the CreditCard number by checking for the right number of digit
    // for the correct Issuer identification number and by using the Luhn Algorithm
    public Boolean isValid() {
        return checkNumberOfDigitsValidity() && checkIINValidity() && checkForLuhnValidity() && checkDateValidity();
    }

    private Boolean checkDateValidity() {
        return expiration.after(new Date());
    }

    private Boolean checkNumberOfDigitsValidity() {
        switch (issuer) {
            case VISA:              return digits.length() == 13 || digits.length() == 16 || digits.length() == 19;
            case MASTER_CARD:       return digits.length() == 16;
            case AMERICAN_EXPRESS:  return digits.length() == 15;
            default:                return false;
        }
    }

    private Boolean checkIINValidity() {
        switch (issuer) {
            case VISA:              return checkIINValidityForVisa();
            case MASTER_CARD:       return checkIINValidityForMasterCard();
            case AMERICAN_EXPRESS:  return checkIINValidityForAmericanExpress();
            default:                return false;
        }
    }

    private Boolean checkIINValidityForAmericanExpress() {
        String firstTwoDigits = digits.substring(0, 2);
        return firstTwoDigits.equals("34") || firstTwoDigits.equals("37");
    }

    private Boolean checkIINValidityForMasterCard() {
        try {
            int firstFourDigits = Integer.parseInt(digits.substring(0, 4));
            return (firstFourDigits >= 5100 && firstFourDigits <= 5599) || (firstFourDigits >= 2221 && firstFourDigits <= 2720);
        }
        catch (Exception ex) {
            return false;
        }
    }

    private Boolean checkIINValidityForVisa() {
        String firstDigit = digits.substring(0,1);
        return firstDigit.equals("4");
    }

    private Boolean checkForLuhnValidity() {
        int sum = 0;
        boolean alternate = false;
        for (int i = digits.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(digits.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}