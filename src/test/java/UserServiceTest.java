import com.example.pricemanager.dto.UserDto;
import com.example.pricemanager.entity.User;
import com.example.pricemanager.service.UserService;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class UserServiceTest {
    static UserService userService;

    @BeforeClass
    public static void createUserService() {
        userService = new UserService();
    }

    @Test
    public void userTableShouldContainAtLeastOneActiveAdmin() {
        List<UserDto> userDtos = userService.getAllUserDtos();
        int numOfActiveAdmins = 0;
        for (int i = 0; i < userDtos.size(); i += 1) {
            if (userDtos.get(i).getUserRole().equals(User.UserRole.ADMIN_ROLE)
                    && userDtos.get(i).getUserStatus().equals(User.UserStatus.ACTIVE)) {
                numOfActiveAdmins += 1;
            }
        }
        Assert.assertTrue(numOfActiveAdmins > 0);
    }

    @Test
    public void getHashOfPasswordShouldWorkCorrect() {
        String hash = userService.getHashOfPassword("Admin1337");
        String actualHash = "cb128d1b4569100e3228944359973b676728b5621d28b2a84723e9deb977dd25";
        Assert.assertEquals(actualHash, hash);
    }
}
