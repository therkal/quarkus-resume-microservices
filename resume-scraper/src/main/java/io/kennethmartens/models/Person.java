package io.kennethmartens.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String firstname;
    private String lastname;
    private String vanityName;

    private List<Position> experience;
}
