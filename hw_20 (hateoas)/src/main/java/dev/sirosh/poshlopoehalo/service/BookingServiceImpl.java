package dev.sirosh.poshlopoehalo.service;

import dev.sirosh.poshlopoehalo.model.Booking;
import dev.sirosh.poshlopoehalo.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    BookingRepository bookingRepository;

    @Override
    public boolean addBooking(Booking booking) {
        bookingRepository.save(booking);
        return true;
    }

    @Override
    public boolean deleteBooking(Booking booking) {
        bookingRepository.delete(booking);
        return true;
    }

    @Override
    public boolean isExists(Booking booking) {
        return bookingRepository.findByUserAndMovement(booking.getUser(), booking.getMovement()).isPresent();
    }
}
