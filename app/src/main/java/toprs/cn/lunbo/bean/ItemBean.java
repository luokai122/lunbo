package toprs.cn.lunbo.bean;


public class ItemBean {
    public String itemImage;
    public String itemTitle;
    public String itemUrl;

    public ItemBean(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public ItemBean(String itemImage , String itemTitle, String itemUrl) {
        this.itemTitle = itemTitle;
        this.itemUrl = itemUrl;
        this.itemImage = itemImage;
    }
}
