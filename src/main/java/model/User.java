package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private long id;
    private String login;
    private long clanId;
    private int gold;
}
