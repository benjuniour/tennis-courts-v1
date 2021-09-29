package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    /**
     * Finds a guest based on the guest id
     * @param guestId the id of the guest to be found
     * @return a Guest DTO representing the Guest Information, otherwise throws an exception
     */
    public GuestDTO findGuest(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
                    throw new EntityNotFoundException("Guest not found.");
                }
        );
    }
}
