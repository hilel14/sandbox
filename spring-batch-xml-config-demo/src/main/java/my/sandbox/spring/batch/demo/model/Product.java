/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.sandbox.spring.batch.demo.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hilel-deb
 *
 * Input file line example: Apple 08.15 22/02/2012 17:53
 */
public class Product implements java.io.Serializable {

    SimpleDateFormat fileDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String description;

    private double price;

    private Date purchaseDate;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(String price) {
        this.price = Double.parseDouble(price);
    }

    /**
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     * @throws java.text.ParseException
     */
    public void setPurchaseDate(String purchaseDate) throws ParseException {
        this.purchaseDate = fileDateFormat.parse(purchaseDate);
    }

    public String getPurchaseDateFormatted() {
        return dbDateFormat.format(purchaseDate);
    }

}
