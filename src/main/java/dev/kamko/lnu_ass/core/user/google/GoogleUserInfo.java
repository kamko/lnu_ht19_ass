package dev.kamko.lnu_ass.core.user.google;

import lombok.Value;

@Value
public class GoogleUserInfo {
    String sub;
    String name;
    String email;
}
