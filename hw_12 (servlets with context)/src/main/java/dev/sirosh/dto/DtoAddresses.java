package dev.sirosh.dto;

import java.util.List;

public class DtoAddresses implements Dto {
    private List<DtoAddress> addresses;
    private long totalCount;

    public List<DtoAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<DtoAddress> addresses) {
        this.addresses = addresses;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    protected DtoAddresses() {
    }

    public DtoAddresses(List<DtoAddress> addresses, long totalCount) {
        this.addresses = addresses;
        this.totalCount = totalCount;
    }
}
