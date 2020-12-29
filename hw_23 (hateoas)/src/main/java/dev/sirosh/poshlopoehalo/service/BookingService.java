package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.Booking;

public interface BookingService {
    boolean addBooking(Booking booking);
    boolean deleteBooking(Booking booking);
    boolean isExists(Booking booking);
}
