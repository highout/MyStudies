package com.example.project_two;

import java.time.LocalDate;

public record Reservation (
        Long id,
        Long userId,
        Long roomId,
        LocalDate startDate,
        LocalDate endDate,
        ReservationStatus status
){
}
