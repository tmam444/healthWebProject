package com.healthchang.demo.config.auth.dto;

import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.domain.MemberTable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.util.Collections;
import java.util.Map;

@Getter
@ToString
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    private String password;
    private String name;
    private String picture;
    private Date date;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture, String password, Date date) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.password = password;
        this.date = date;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver("id", attributes);
        } else if ("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        } else if ("google".equals(registrationId)) {
            return ofGoogle(userNameAttributeName, attributes);
        }
        return null;
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
//        System.out.println(response); //{id=120235908, email=fealtyhot@naver.com, name=이충렬, birthday=12-04}
        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email(response.get("email") + "_" + response.get("id"))
                .password(response.get("name") + "_" + response.get("id"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        System.out.println(attributes); // {sub=117723292258952329379, name=이충렬, given_name=충렬, family_name=이, picture=https://lh3.googleusercontent.com/-q4r_svECB28/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnV4pHdfd7T0ayyPtSlQ80q4llt7Q/photo.jpg, email=tmam4411@gmail.com, email_verified=true, locale=ko}
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email") + "_" + attributes.get("sub"))
                .password((String) attributes.get("name") + "_" + attributes.get("sub"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("properties");
        System.out.println(response);
        System.out.println(kakao_account);
        return OAuthAttributes.builder()
                .name((String) kakao_account.get("nickname"))
                .email((String) response.get("email") + "_" + attributes.get("id"))
                .password((String) kakao_account.get("nickname") + "_" + attributes.get("id"))
                .picture((String) kakao_account.get("profile_image_url"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public MemberTable toEntity() {
        return MemberTable.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .date(new Date(new java.util.Date().getTime()))
                .authoritySet(Collections.singleton(MemberAuthority.GUEST))
                .build();
    }


}
