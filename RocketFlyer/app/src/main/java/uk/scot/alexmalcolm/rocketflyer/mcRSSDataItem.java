package uk.scot.alexmalcolm.rocketflyer;

import java.io.Serializable;

public class mcRSSDataItem
{
    private String itemTitle;
    private String itemDesc;
    private String itemLink;

    public String getItemTitle()
    {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle)
    {
        this.itemTitle = itemTitle;
    }

    public String getItemDesc()
    {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc)
    {
        this.itemDesc = itemDesc;
    }

    public String getItemLink()
    {
        return itemLink;
    }

    public void setItemLink(String itemLink)
    {
        this.itemLink = itemLink;
    }

    public mcRSSDataItem()
    {
        this.itemTitle = "";
        this.itemDesc = "";
        this.itemLink = "";
    }
}

