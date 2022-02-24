package org.kenneth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private String firstname;
    private String lastname;

    private Collection<Position> experience;
}
