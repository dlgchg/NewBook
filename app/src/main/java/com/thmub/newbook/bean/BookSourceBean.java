package com.thmub.newbook.bean;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Zhouas666 on 2019-03-26
 * Github: https://github.com/zas023
 * <p>
 * 书源bean
 */
@Entity
public class BookSourceBean implements Parcelable {

    //书源地址
    @Id
    private String rootLink;    //利用url的唯一性
    //书源名称
    private String sourceName;
    //编码格式
    private String encodeType;
    //搜索地址
    private String searchLink;

    //书源规则
    //搜索
    private String ruleSearchTitle;
    private String ruleSearchAuthor;
    private String ruleSearchDesc;
    private String ruleSearchCover;
    private String ruleSearchLink;
    //目录
    private String ruleCatalogTitle;
    private String ruleCatalogLink;
    //章节
    private String ruleChapterContent;

    //排序数
    @OrderBy
    private int orderNumber;
    //权重，通过网络延时计算
    @OrderBy
    @NotNull
    private int weight = 0;
    //是否选择
    private boolean isSelected;

    @Generated(hash = 1647316888)
    public BookSourceBean(String rootLink, String sourceName, String encodeType, String searchLink, String ruleSearchTitle,
            String ruleSearchAuthor, String ruleSearchDesc, String ruleSearchCover, String ruleSearchLink, String ruleCatalogTitle,
            String ruleCatalogLink, String ruleChapterContent, int orderNumber, int weight, boolean isSelected) {
        this.rootLink = rootLink;
        this.sourceName = sourceName;
        this.encodeType = encodeType;
        this.searchLink = searchLink;
        this.ruleSearchTitle = ruleSearchTitle;
        this.ruleSearchAuthor = ruleSearchAuthor;
        this.ruleSearchDesc = ruleSearchDesc;
        this.ruleSearchCover = ruleSearchCover;
        this.ruleSearchLink = ruleSearchLink;
        this.ruleCatalogTitle = ruleCatalogTitle;
        this.ruleCatalogLink = ruleCatalogLink;
        this.ruleChapterContent = ruleChapterContent;
        this.orderNumber = orderNumber;
        this.weight = weight;
        this.isSelected = isSelected;
    }

    @Generated(hash = 1512565980)
    public BookSourceBean() {
    }

    protected BookSourceBean(Parcel in) {
        rootLink = in.readString();
        sourceName = in.readString();
        encodeType = in.readString();
        searchLink = in.readString();
        ruleSearchTitle = in.readString();
        ruleSearchAuthor = in.readString();
        ruleSearchDesc = in.readString();
        ruleSearchCover = in.readString();
        ruleSearchLink = in.readString();
        ruleCatalogTitle = in.readString();
        ruleCatalogLink = in.readString();
        ruleChapterContent = in.readString();
        orderNumber = in.readInt();
        weight = in.readInt();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<BookSourceBean> CREATOR = new Creator<BookSourceBean>() {
        @Override
        public BookSourceBean createFromParcel(Parcel in) {
            return new BookSourceBean(in);
        }

        @Override
        public BookSourceBean[] newArray(int size) {
            return new BookSourceBean[size];
        }
    };

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getRootLink() {
        return rootLink;
    }

    public void setRootLink(String rootLink) {
        this.rootLink = rootLink;
    }

    public String getSearchLink() {
        return searchLink;
    }

    public void setSearchLink(String searchLink) {
        this.searchLink = searchLink;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getRuleSearchTitle() {
        return ruleSearchTitle;
    }

    public void setRuleSearchTitle(String ruleSearchTitle) {
        this.ruleSearchTitle = ruleSearchTitle;
    }

    public String getRuleSearchAuthor() {
        return ruleSearchAuthor;
    }

    public void setRuleSearchAuthor(String ruleSearchAuthor) {
        this.ruleSearchAuthor = ruleSearchAuthor;
    }

    public String getRuleSearchDesc() {
        return ruleSearchDesc;
    }

    public void setRuleSearchDesc(String ruleSearchDesc) {
        this.ruleSearchDesc = ruleSearchDesc;
    }

    public String getRuleSearchCover() {
        return ruleSearchCover;
    }

    public void setRuleSearchCover(String ruleSearchCover) {
        this.ruleSearchCover = ruleSearchCover;
    }

    public String getRuleSearchLink() {
        return ruleSearchLink;
    }

    public void setRuleSearchLink(String ruleSearchLink) {
        this.ruleSearchLink = ruleSearchLink;
    }

    public String getRuleCatalogTitle() {
        return ruleCatalogTitle;
    }

    public void setRuleCatalogTitle(String ruleCatalogTitle) {
        this.ruleCatalogTitle = ruleCatalogTitle;
    }

    public String getRuleCatalogLink() {
        return ruleCatalogLink;
    }

    public void setRuleCatalogLink(String ruleCatalogLink) {
        this.ruleCatalogLink = ruleCatalogLink;
    }

    public String getRuleChapterContent() {
        return ruleChapterContent;
    }

    public void setRuleChapterContent(String ruleChapterContent) {
        this.ruleChapterContent = ruleChapterContent;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /*************************************************************************/

    @Override
    public String toString() {
        return "BookSourceBean{" +
                "rootLink='" + rootLink + '\'' +
                ", sourceName='" + sourceName + '\'' +
                ", searchLink='" + searchLink + '\'' +
                ", encodeType='" + encodeType + '\'' +
                ", ruleSearchTitle='" + ruleSearchTitle + '\'' +
                ", ruleSearchAuthor='" + ruleSearchAuthor + '\'' +
                ", ruleSearchDesc='" + ruleSearchDesc + '\'' +
                ", ruleSearchCover='" + ruleSearchCover + '\'' +
                ", ruleSearchLink='" + ruleSearchLink + '\'' +
                ", ruleCatalogTitle='" + ruleCatalogTitle + '\'' +
                ", ruleCatalogLink='" + ruleCatalogLink + '\'' +
                ", ruleChapterContent='" + ruleChapterContent + '\'' +
                ", orderNumber=" + orderNumber +
                ", weight=" + weight +
                ", isSelected=" + isSelected +
                '}';
    }

    public boolean getIsSelected() {
        return this.isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(rootLink);
        dest.writeString(sourceName);
        dest.writeString(encodeType);
        dest.writeString(searchLink);
        dest.writeString(ruleSearchTitle);
        dest.writeString(ruleSearchAuthor);
        dest.writeString(ruleSearchDesc);
        dest.writeString(ruleSearchCover);
        dest.writeString(ruleSearchLink);
        dest.writeString(ruleCatalogTitle);
        dest.writeString(ruleCatalogLink);
        dest.writeString(ruleChapterContent);
        dest.writeInt(orderNumber);
        dest.writeInt(weight);
        dest.writeByte((byte) (isSelected ? 1 : 0));
    }
}
