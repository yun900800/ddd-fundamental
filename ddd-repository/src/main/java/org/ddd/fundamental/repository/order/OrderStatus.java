package org.ddd.fundamental.repository.order;

public enum OrderStatus {
    NEW("new"), CANCEL("cancel");
    private String status;

    OrderStatus(String status){
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static OrderStatus fromStatus(String status) {
        for (OrderStatus orderStatus: OrderStatus.values()) {
            if (orderStatus.getStatus().equals(status)) {
                return orderStatus;
            }
        }
        return null;
    }
}
