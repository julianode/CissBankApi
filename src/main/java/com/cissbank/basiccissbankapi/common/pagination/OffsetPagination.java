package com.cissbank.basiccissbankapi.common.pagination;

import java.util.Objects;

public class OffsetPagination extends Pagination{

    private int itemsLimitPerPage;

    private int itemsOffsetToWantedPage;

    public OffsetPagination() {}

    public OffsetPagination(int itemsLimitPerPage, int itemsOffsetToWantedPage) {
        this.itemsLimitPerPage = ensureLimitIsNeverLessThenOne(itemsLimitPerPage);
        this.itemsOffsetToWantedPage = ensureOffsetIsLimitMultiple(itemsOffsetToWantedPage);
    }

    public int getItemsLimitPerPage() {
        return itemsLimitPerPage;
    }

    public void setItemsLimitPerPage(int itemsLimitPerPage) {
        this.itemsLimitPerPage = itemsLimitPerPage;
    }

    public int getItemsOffsetToWantedPage() {
        return itemsOffsetToWantedPage;
    }

    public void setItemsOffsetToWantedPage(int itemsOffsetToWantedPage) {
        this.itemsOffsetToWantedPage = itemsOffsetToWantedPage;
    }

    @Override
    public int getCurrentPage() {
        return itemsOffsetToWantedPage / itemsLimitPerPage;
    }

    @Override
    public int getNextPage() {
        return getCurrentPage() + 1;
    }

    /**
     * This method does not let ArithmeticException to happen.
     */
    private int ensureOffsetIsLimitMultiple(int itemsOffsetToWantedPage) {

        int remainder = itemsOffsetToWantedPage % itemsLimitPerPage;

        if (remainder != 0) {
            itemsOffsetToWantedPage = itemsOffsetToWantedPage - remainder;
        }
        return itemsOffsetToWantedPage;
    }

    /**
     * This method does not let ArithmeticException to happen.
     */
    private int ensureLimitIsNeverLessThenOne(int itemsLimitPerPage) {

        if(itemsLimitPerPage < 1) {
            return 1;

        } else {
            return itemsLimitPerPage;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OffsetPagination)) return false;
        OffsetPagination that = (OffsetPagination) o;
        return itemsLimitPerPage == that.itemsLimitPerPage && itemsOffsetToWantedPage == that.itemsOffsetToWantedPage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemsLimitPerPage, itemsOffsetToWantedPage);
    }

    @Override
    public String toString() {
        return "OffsetPagination{" +
                "itemsLimitPerPage=" + itemsLimitPerPage +
                ", itemsOffsetToWantedPage=" + itemsOffsetToWantedPage +
                '}';
    }
}
