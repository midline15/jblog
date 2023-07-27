package com.ssamz.jblogweb.security;

import com.ssamz.jblogweb.domain.OAuthType;
import com.ssamz.jblogweb.domain.RoleType;
import com.ssamz.jblogweb.domain.User;
import com.ssamz.jblogweb.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2UserDetailsServiceImpl extends DefaultOAuth2UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${google.default.password}")
    private String googlePassword;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String providerId = oAuth2User.getAttribute("sub");
        String email = oAuth2User.getAttribute("email");
        String username = email + "_" + providerId;
        String password = passwordEncoder.encode(googlePassword);

        User findUser = userRepository.findByUsername(username).orElseGet(()->{
            return new User();
        });

        if (findUser.getUsername() == null) {
            findUser = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(RoleType.USER)
                    .oauth(OAuthType.GOOGLE)
                    .build();
            userRepository.save(findUser);
        }
        return new UserDetailsImpl(findUser, oAuth2User.getAttributes());
    }
}
