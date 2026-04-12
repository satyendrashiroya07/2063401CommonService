package productEvent.userEvent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEvent {

        private Long Id;
        private String userId;
        private String password;
        private String userName;
        private String userDob;
        private String userAddress;
        private String userMobileNumber;
        private String userEmail;
        private String pinCode;

}
