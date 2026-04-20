package shiroya.orderEvent;

public enum OrderStatus {

    CREATED,
    PAYMENT_PENDING,
    PAYMENT_SUCCESS,
    PAYMENT_FAILED,
    CONFIRMED,
    CANCELLED;

    public boolean isFinalState() {
        return this == CONFIRMED || this == CANCELLED;
    }
}
