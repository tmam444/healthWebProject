package com.healthchang.demo.config.auth.dto;

import com.healthchang.demo.domain.MemberAuthority;
import com.healthchang.demo.domain.MemberTable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.sql.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
        String id = (String)response.get("id");
        String name = (String)response.get("name");
        String email = (String)response.get("email");
        String profile_image = (String)response.get("profile_image");
        return OAuthAttributes.builder()
                .name(name)
                .email(email + "_" + id)
                .password(name + "_" + id)
                .picture(profile_image)
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
//        System.out.println(attributes); // {sub=117723292258952329379, name=이충렬, given_name=충렬, family_name=이, picture=https://lh3.googleusercontent.com/-q4r_svECB28/AAAAAAAAAAI/AAAAAAAAAAA/AMZuucnV4pHdfd7T0ayyPtSlQ80q4llt7Q/photo.jpg, email=tmam4411@gmail.com, email_verified=true, locale=ko}
        String id = (String)attributes.get("sub");
        String name = (String)attributes.get("name");
        String email = (String)attributes.get("email");
        String profile_image = (String)attributes.get("picture");
        return OAuthAttributes.builder()
                .name(name)
                .email(email + "_" + id)
                .password(name + "_" + id)
                .picture(profile_image)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakao_account = (Map<String, Object>) attributes.get("properties");
//        System.out.println(attributes); //{id=1481314394, connected_at=2020-09-18T13:45:44Z, properties={nickname=충렬, profile_image=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_640x640.jpg, thumbnail_image=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_110x110.jpg}, kakao_account={profile_needs_agreement=false, profile={nickname=충렬, thumbnail_image_url=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_110x110.jpg, profile_image_url=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_640x640.jpg}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=cndfuf0159@naver.com, has_birthday=true, birthday_needs_agreement=false, birthday=1204, birthday_type=SOLAR}}
//        System.out.println(response); //{profile_needs_agreement=false, profile={nickname=충렬, thumbnail_image_url=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_110x110.jpg, profile_image_url=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_640x640.jpg}, has_email=true, email_needs_agreement=false, is_email_valid=true, is_email_verified=true, email=cndfuf0159@naver.com, has_birthday=true, birthday_needs_agreement=false, birthday=1204, birthday_type=SOLAR}
//        System.out.println(kakao_account); //{nickname=충렬, profile_image=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_640x640.jpg, thumbnail_image=http://k.kakaocdn.net/dn/Kr4M5/btqGAeDqpC8/wAnzOGxFxSw9pN1I2fh86k/img_110x110.jpg}
        String id = Integer.toString((Integer) attributes.get("id"));
        String name = (String)kakao_account.get("nickname");
        String email = (String)response.get("email");
        String profile_image = (String)kakao_account.get("profile_image_url");
        return OAuthAttributes.builder()
                .name(name)
                .email(email + "_" + id)
                .password(name + "_" + id)
                .picture(profile_image)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public MemberTable toEntity() {
        Set<MemberAuthority> authoritySet = new HashSet<>();
        authoritySet.add(MemberAuthority.USER);

        return MemberTable.builder()
                .name(name)
                .email(email)
                .password(password)
                .picture(picture)
                .date(new Date(new java.util.Date().getTime()))
//                .authoritySet(Collections.singleton(MemberAuthority.USER))
                .authoritySet(authoritySet)
                .build();
    }


}
