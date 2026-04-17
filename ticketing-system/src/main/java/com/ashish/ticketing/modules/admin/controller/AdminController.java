package com.ashish.ticketing.modules.admin.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.admin.dto.response.BookingStatsResponse;
import com.ashish.ticketing.modules.admin.dto.response.DashboardResponse;
import com.ashish.ticketing.modules.admin.dto.response.EventStatsResponse;
import com.ashish.ticketing.modules.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Analytics", description = "Admin-only analytics APIs")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/dashboard")
	@Operation(summary = "Dashboard summary", description = "Returns top-level platform metrics for admin dashboard")
	public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardSummary() {
		DashboardResponse response = adminService.getDashboardSummary();
		return ResponseEntity.ok(ApiResponse.success(response, "Dashboard summary fetched successfully"));
	}

	@GetMapping("/events/stats")
	@Operation(summary = "Event stats", description = "Returns per-event booking and revenue analytics")
	public ResponseEntity<ApiResponse<List<EventStatsResponse>>> getEventStats() {
		List<EventStatsResponse> response = adminService.getEventStats();
		return ResponseEntity.ok(ApiResponse.success(response, "Event stats fetched successfully"));
	}

	@GetMapping("/bookings/stats")
	@Operation(summary = "Booking stats", description = "Returns booking status trends and revenue summary")
	public ResponseEntity<ApiResponse<BookingStatsResponse>> getBookingStats() {
		BookingStatsResponse response = adminService.getBookingStats();
		return ResponseEntity.ok(ApiResponse.success(response, "Booking stats fetched successfully"));
	}

	@GetMapping("/events/{id}/stats")
	@Operation(summary = "Event stats by id", description = "Returns analytics for a specific event")
	public ResponseEntity<ApiResponse<EventStatsResponse>> getEventStatsById(@PathVariable Long id) {
		EventStatsResponse response = adminService.getEventStatsById(id);
		return ResponseEntity.ok(ApiResponse.success(response, "Event stats fetched successfully"));
	}

}
