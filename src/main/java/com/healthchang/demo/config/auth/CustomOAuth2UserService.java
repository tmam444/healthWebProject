package com.healthchang.demo.config.auth;

import com.healthchang.demo.config.auth.dto.OAuthAttributes;
import com.healthchang.demo.config.auth.dto.SessionUser;
import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.domain.MemberTable;
import com.healthchang.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().
                                        getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        MemberTable user = memberRepository.findByEmail(attributes.getEmail()).orElse(null);
        if(user == null){
            user = saveOrUpdate(attributes);
        }

        httpSession.setAttribute("user", new SessionUser(user));

        Set<MemberAuthority> authoritySet = new HashSet<>();
        authoritySet.add(MemberAuthority.USER);

        return new DefaultOAuth2User(
                authoritySet,
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    public MemberTable saveOrUpdate(OAuthAttributes attributes){
        MemberTable user = memberRepository.findByEmail(attributes.getEmail())
                                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                                .orElse(attributes.toEntity());
        user.setPassword(encoder.encode(user.getPassword()));
        return memberRepository.save(user);
    }
}
