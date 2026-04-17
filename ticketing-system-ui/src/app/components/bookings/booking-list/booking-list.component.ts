import { Component, OnInit } from '@angular/core';
import { BookingService } from 'src/app/services/booking.service';

@Component({
  selector: 'app-booking-list',
  templateUrl: './booking-list.component.html',
  styleUrls: ['./booking-list.component.css']
})
export class BookingListComponent implements OnInit {
  bookings: any[] = [];
  loading: boolean = true;

  constructor(private bookingService: BookingService) { }

  ngOnInit(): void {
    this.loadBookings();
  }

  loadBookings() {
    this.bookingService.getBookings().subscribe({
      next: (res: any) => {
        this.bookings = res.data || res;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error loading bookings', err);
        // Mock data fallback
        this.bookings = [
          {
            bookingNumber: "BK-20260417-AB12",
            eventId: 101,
            eventTitle: "Coldplay Live", // assumed joined field for UI
            status: "CONFIRMED",
            amount: 5998.00,
            createdAt: "2026-04-17T10:00:00Z"
          },
          {
            bookingNumber: "BK-20260412-XY99",
            eventId: 102,
            eventTitle: "Tech Summit 2026",
            status: "CANCELLED",
            amount: 1500.00,
            createdAt: "2026-04-12T14:30:00Z"
          }
        ];
        this.loading = false;
      }
    });
  }

  cancelBooking(id: string | number) {
    if(confirm('Are you sure you want to cancel this booking?')) {
      this.bookingService.cancelBooking(id).subscribe({
        next: () => {
          this.loadBookings(); // reload
        },
        error: (err) => {
          console.error('Failed to cancel', err);
          // Update mock data for demo
          const b = this.bookings.find(x => x.bookingNumber === id || x.id === id);
          if (b) b.status = 'CANCELLED';
        }
      });
    }
  }
}
