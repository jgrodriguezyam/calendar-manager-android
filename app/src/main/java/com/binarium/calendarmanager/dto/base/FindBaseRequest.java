package com.binarium.calendarmanager.dto.base;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jrodriguez on 08/04/2017.
 */

public class FindBaseRequest {
    @SerializedName("Sort")
    private String sort;
    @SerializedName("SortBy")
    private String sortBy;
    @SerializedName("ItemsToShow")
    private int itemsToShow;
    @SerializedName("Page")
    private int page;

    public FindBaseRequest() {
    }

    public FindBaseRequest(String sort, String sortBy, int itemsToShow, int page) {
        this.sort = sort;
        this.sortBy = sortBy;
        this.itemsToShow = itemsToShow;
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public int getItemsToShow() {
        return itemsToShow;
    }

    public void setItemsToShow(int itemsToShow) {
        this.itemsToShow = itemsToShow;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
