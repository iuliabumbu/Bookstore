package ro.sd.a2.mappers;

import ro.sd.a2.dto.UserDto;
import ro.sd.a2.entity.User;

public class Mapper {

    public static UserDto userMapping(User user){

        if(user == null){
            return null;
        }

        return  UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password((user.getPassword()))
                .build();
    }

}
