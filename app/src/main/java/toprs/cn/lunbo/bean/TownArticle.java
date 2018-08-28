package toprs.cn.lunbo.bean;

import java.util.Date;

public class TownArticle {

    public interface Add{}
    public interface Del{}
    private Integer articleId;
    private String articleTitle;


    private String articleAuthor;

    private String articleIntorduction;

    private String articleImg;

    private String articleUrl;

    private String articleHtml;

    private Integer fabulousNumber;

    private Integer articleTypeId;

    private String articleTypeName;

    private Integer articleReading;

    private Integer top;

    private Date creatime;

    private Date deltime;

    private Integer delFlag;

    private boolean userFabulous;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle == null ? null : articleTitle.trim();
    }

    public String getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(String articleAuthor) {
        this.articleAuthor = articleAuthor == null ? null : articleAuthor.trim();
    }

    public String getArticleIntorduction() {
        return articleIntorduction;
    }

    public void setArticleIntorduction(String articleIntorduction) {
        this.articleIntorduction = articleIntorduction == null ? null : articleIntorduction.trim();
    }

    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg == null ? null : articleImg.trim();
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl == null ? null : articleUrl.trim();
    }

    public Integer getFabulousNumber() {
        return fabulousNumber;
    }

    public void setFabulousNumber(Integer fabulousNumber) {
        this.fabulousNumber = fabulousNumber;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public Date getCreatime() {
        return creatime;
    }

    public void setCreatime(Date creatime) {
        this.creatime = creatime;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getArticleHtml() {
        return articleHtml;
    }

    public void setArticleHtml(String articleHtml) {
        this.articleHtml= articleHtml == null ? null : articleHtml.trim();
    }

    public Integer getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Integer articleTypeId) {
        this.articleTypeId = articleTypeId;
    }
    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public Integer getArticleReading() {
        return articleReading;
    }

    public void setArticleReading(Integer articleReading) {
        this.articleReading = articleReading;
    }

    public boolean isUserFabulous() {
        return userFabulous;
    }

    public void setUserFabulous(boolean userFabulous) {
        this.userFabulous = userFabulous;
    }
}