package com.sujin.stargram.config.oauth;

import com.sujin.stargram.config.auth.PrincipalDetails;
import com.sujin.stargram.config.oauth.provider.GoogleUserInfo;
import com.sujin.stargram.config.oauth.provider.KakaoUserInfo;
import com.sujin.stargram.config.oauth.provider.NaverUserInfo;
import com.sujin.stargram.config.oauth.provider.OAuth2UserInfo;
import com.sujin.stargram.domain.User;
import com.sujin.stargram.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest : "+ userRequest.getClientRegistration()); //restrationId로 어떤 OAuth로 로그인 햇는지 알수 잇음
        System.out.println("userRequest : "+ userRequest.getAccessToken());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        //구글 로그인 버트 클릭 -> 구글로그인창 -> 로그인 완료 => code를 리턴(OAuth-client라이브러리) -> AccessToken 요청
        //userRequest정보 -> 회원프로필받아야함(loadUser함수 ) -> 구글로부터 받아줌 회원프로필
        System.out.println("getAttributes : "+ oAuth2User.getAttributes());

        //회원가입을 강제로 징행
        OAuth2UserInfo oAuth2UserInfo = null;
        OAuth2UserInfo oAuth2UserInfo2 = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("kakao_account"));
            oAuth2UserInfo2 = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("properties"));
//            oAuth2UserInfo3 = new KakaoUserInfo((Map)oAuth2User.getAttributes().get("id"));

        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId;
        if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            providerId = Integer.toString((Integer) oAuth2User.getAttributes().get("id"));
            System.out.println("?????????????????providerId = " + providerId);
        }else{
            providerId = oAuth2UserInfo.getProviderId();
        }
        String username = provider+"_"+providerId; //google_(sub)
        String password = bCryptPasswordEncoder.encode("스타그램");

        String email = oAuth2UserInfo.getEmail();
        String role = "ROLE_USER";
        String name = "";
        if(!userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            name = oAuth2UserInfo.getName();
        }else{
            name = oAuth2UserInfo2.getName();
        }

        User userEntity = userRepository.findByUsername(username);

        if(userEntity == null){
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .name(name)
                    .build();
            userRepository.save(userEntity);
        }else{
            System.out.println("당신은 자동 회원가입이 되어 있습니다.");
        }

        //Authentication 안에 들어감
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes());
    }
}
