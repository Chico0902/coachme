package com.ssafy.api.dto.request;

import lombok.Data;

@Data
public class MemberRegistRequestDto {
    private String stringId;
    private String pw;
    private String name;
    private String nick;
    private String email;
}
