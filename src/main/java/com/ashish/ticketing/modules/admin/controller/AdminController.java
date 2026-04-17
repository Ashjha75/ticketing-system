package com.ashish.ticketing.modules.admin.controller;

import com.ashish.ticketing.common.response.ApiResponse;
import com.ashish.ticketing.modules.admin.dto.response.BookingStatsResponse;
import com.ashish.ticketing.modules.admin.dto.response.DashboardResponse;
import com.ashish.ticketing.modules.admin.dto.response.EventStatsResponse;
import com.ashish.ticketing.modules.admin.service.AdminService;
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
public class AdminController {

	private final AdminService adminService;

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@GetMapping("/dashboard")
	public ResponseEntity<ApiResponse<DashboardResponse>> getDashboardSummary() {
		DashboardResponse response = adminService.getDashboardSummary();
		return ResponseEntity.ok(ApiResponse.success(response, "Dashboard summary fetched successfully"));
	}

	@GetMapping("/events/stats")
	public ResponseEntity<ApiResponse<List<EventStatsResponse>>> getEventStats() {
		List<EventStatsResponse> response = adminService.getEventStats();
		return ResponseEntity.ok(ApiResponse.success(response, "Event stats fetched successfully"));
	}

	@GetMapping("/bookings/stats")
	public ResponseEntity<ApiResponse<BookingStatsResponse>> getBookingStats() {
		BookingStatsResponse response = adminService.getBookingStats();
		return ResponseEntity.ok(ApiResponse.success(response, "Booking stats fetched successfully"));
	}

	@GetMapping("/events/{id}/stats")
	public ResponseEntity<ApiResponse<EventStatsResponse>> getEventStatsById(@PathVariable Long id) {
		EventStatsResponse response = adminService.getEventStatsById(id);
		return ResponseEntity.ok(ApiResponse.success(response, "Event stats fetched successfully"));
	}

}
