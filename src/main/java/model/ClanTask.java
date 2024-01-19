package model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClanTask {
    private long id;
    private String title;
    private String description;
    private int reward;
}
