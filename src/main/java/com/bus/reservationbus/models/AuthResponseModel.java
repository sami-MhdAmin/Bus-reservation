package com.bus.reservationbus.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseModel {
    private int statusCode;
    private String msg;
    private String accessToken;
    private Long expirationDuration;
    private Long loginTime;
}
