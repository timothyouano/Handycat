package com.example.newpc.qrcode;

/**
 * Created by Timothy on 10/9/2017.
 */

public class Transaction {
    private String date;
    private String time;
    private long pcs;
    private long price;
    private long total;
    private String expirydate;
    private String buyerid;
    private String sellerid;
    private String postid;
    private String sellername;
    private String status;

    public String id;

    public long getPrice() {
        return price;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transaction() {
    }

    public String getId() { return id; }

    public void setId(String transid) { this.id = transid; }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getPcs() {
        return pcs;
    }

    public void setPcs(long pcs) {
        this.pcs = pcs;
    }

    public long getTotal() {
        return pcs;
    }

    public void setTotal(long total) {
        this.total = total;
    }

}
