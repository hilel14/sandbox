/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.camel.sandbox.filedemo.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 *
 * @author hilel-deb
 */
@CsvRecord(separator = "\t", crlf = "UNIX")
public class Product implements java.io.Serializable {
// Apple	08.15	22/02/2012 17:53

    SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @DataField(pos = 1)
    private String description;

    @DataField(pos = 2, precision = 2)
    private double price;

    @DataField(pos = 3, pattern = "dd/MM/yyyy HH:mm")
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
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseDateFormatted() {
        return dbDateFormat.format(purchaseDate);
    }

}
