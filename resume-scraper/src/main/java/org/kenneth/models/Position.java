package org.kenneth.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {
    private String role;
    private String description;
    private String company;
    private Instant startDate;
    private Instant endDate;
}
