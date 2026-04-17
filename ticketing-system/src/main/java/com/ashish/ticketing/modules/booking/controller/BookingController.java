package com.ashish.ticketing.modules.booking.controller;

import com.ashish.ticketing.common.exception.UnauthorizedException;
import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.common.util.SecurityUtil;
import com.ashish.ticketing.modules.booking.dto.request.CreateBookingRequest;
import com.ashish.ticketing.modules.booking.dto.response.BookingDetailsResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingResponse;
import com.ashish.ticketing.modules.booking.dto.response.BookingSummaryResponse;
import com.ashish.ticketing.modules.booking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Booking APIs")
@SecurityRequirement(name = "bearerAuth")
public class BookingController {

	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}

	@PostMapping
	@Operation(summary = "Create booking", description = "Validates request, reserves inventory, and creates a confirmed booking")
	public ResponseEntity<ApiResponse<BookingResponse>> createBooking(@Valid @RequestBody CreateBookingRequest request) {
		BookingResponse response = bookingService.createBooking(getCurrentUserId(), request);
		return ResponseEntity.ok(ApiResponse.success(response, "Booking created successfully"));
	}

	@GetMapping
	@Operation(summary = "Get my bookings", description = "Returns the current user's booking list")
	public ResponseEntity<ApiResponse<List<BookingSummaryResponse>>> getMyBookings() {
		List<BookingSummaryResponse> response = bookingService.getUserBookings(getCurrentUserId());
		return ResponseEntity.ok(ApiResponse.success(response, "Bookings fetched successfully"));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get booking details", description = "Returns details for a specific booking owned by the current user")
	public ResponseEntity<ApiResponse<BookingDetailsResponse>> getBookingDetails(@PathVariable Long id) {
		BookingDetailsResponse response = bookingService.getBookingDetails(getCurrentUserId(), id);
		return ResponseEntity.ok(ApiResponse.success(response, "Booking details fetched successfully"));
	}

	@PostMapping("/{id}/cancel")
	@Operation(summary = "Cancel booking", description = "Cancels a confirmed booking and releases inventory")
	public ResponseEntity<ApiResponse<Void>> cancelBooking(@PathVariable Long id) {
		bookingService.cancelBooking(getCurrentUserId(), id);
		return ResponseEntity.ok(ApiResponse.success(null, "Booking cancelled successfully"));
	}

	private Long getCurrentUserId() {
		String userId = SecurityUtil.getCurrentUserId();
		if (userId == null || userId.isBlank()) {
			throw new UnauthorizedException("No authenticated user found");
		}

		try {
			return Long.parseLong(userId);
		} catch (NumberFormatException ex) {
			throw new UnauthorizedException("Invalid authenticated user");
		}
	}

}
