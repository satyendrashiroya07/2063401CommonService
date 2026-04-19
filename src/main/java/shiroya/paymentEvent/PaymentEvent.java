package shiroya.paymentEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentEvent {
    private String orderId;
    private String status;
    private String userId;
    private String productId;
    private int quantity;
    private String email;
}
