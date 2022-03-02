package org.kennethmartens.models;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@RegisterForReflection
public class Position {
    private String role;
    private String description;
    private String company;
    private String location;
    private YearMonth startDate;
    private YearMonth endDate;
    // Parsed duration.
    private String duration;

    private String personId;
}
