package ph.codeaxis.android.handycat.Entity;

/**
 * Created by Timothy on 10/9/2017.
 */

public class Transaction {
    public String date;
    public String expirydate;
    public long pcs;
    public long price;
    public long total;
    public String sellername;
    public String buyerid;
    public String sellerid;
    public String postid;
    public String id;
    public String status;

    public Transaction() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSellername() {
        return sellername;
    }

    public void setSellername(String sellername) {
        this.sellername = sellername;
    }

    public long getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExpirydate() { return expirydate; }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
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
