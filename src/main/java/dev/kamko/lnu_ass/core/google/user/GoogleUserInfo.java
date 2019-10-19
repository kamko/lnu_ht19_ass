package dev.kamko.lnu_ass.core.google.user;

import lombok.Value;

@Value
public class GoogleUserInfo {
    String sub;
    String name;
    String email;
}
