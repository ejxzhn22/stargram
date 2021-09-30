package com.sujin.stargram.Service;

import com.sujin.stargram.domain.User;
import com.sujin.stargram.dto.UserProfileDto;
import com.sujin.stargram.handler.ex.CustomException;
import com.sujin.stargram.handler.ex.CustomValidationApiException;
import com.sujin.stargram.repository.SubscribeRepository;
import com.sujin.stargram.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional(readOnly = true)
    public UserProfileDto userProfile(long pageUserId, long principalId) {
        UserProfileDto dto = new UserProfileDto();

        User userEntity = userRepository.findById(pageUserId).orElseThrow(() -> {
            throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
        });

        dto.setUser(userEntity);
        dto.setPageOwner(pageUserId == principalId ); // 1은 페이지 주인 , -1 주인 아님
        dto.setImageCount(userEntity.getImages().size());

        int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
        int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);

        dto.setSubscribeState(subscribeState ==1);
        dto.setSubscribeCount(subscribeCount);

        return dto;
    }

    //회원가입
    @Transactional
   public User join(User user) {
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole("ROLE_USER");
        User userEntity = userRepository.save(user);
        return userEntity;
   }

   //회원수정
    @Transactional
    public User update(long id, User user) {

        //1.영속화
        User userEntity = userRepository.findById(id).orElseThrow(new Supplier<CustomValidationApiException>() {
            @Override
            public CustomValidationApiException get() {
                return new CustomValidationApiException("찾을 수 없는 id 입니다.");
            }
        }); //1. 무조건 찾음 - get()  2. 못찾으면 익셉션 발동 orElseThrow()
                                                             //3. orElse

        //1.영속화된 오브젝트를 수정 - 더티체킹(업데이트 완료)
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        userEntity.setPassword(encPassword);
        userEntity.setName(user.getName());
        userEntity.setBio(user.getBio());
        userEntity.setWebsite(user.getWebsite());
        userEntity.setPhone(user.getPhone());
        userEntity.setGender(user.getGender());

        return userEntity;
    }// 더티체킹 일어나서 업데이트가 완료됨.
}
