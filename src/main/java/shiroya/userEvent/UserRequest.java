package shiroya.userEvent;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserRequest {

    private String userId;
    private String password;
    private String userName;
    private String firstName;
    private String lasttName;
    private String adharnumber;
    private String userDob;
    private String gender;
    private String userAddress;
    private String userMobileNumber;
    private String userEmail;
    private String country;
    private String state;
    private String pinCode;
    private String createdBy;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private Set<String> roles;
}

