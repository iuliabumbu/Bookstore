package ro.sd.a2.factory;
import ro.sd.a2.entity.User;
import java.util.UUID;

public class UserFactory {

    public static User generateUser(){
        User user = new User();
        user.setDeleted("no");
        user.setId(UUID.randomUUID().toString());
        return user;
    }


}
