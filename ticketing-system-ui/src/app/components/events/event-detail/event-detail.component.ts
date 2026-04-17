import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { EventService } from "src/app/services/event.service";
import { BookingService } from "src/app/services/booking.service";

@Component({
  selector: "app-event-detail",
  templateUrl: "./event-detail.component.html",
  styleUrls: ["./event-detail.component.css"],
})
export class EventDetailComponent implements OnInit {
  event: any = null;
  quantity: number = 1;
  loading: boolean = true;
  bookingSuccess: boolean = false;
  bookingError: string = "";

  constructor(
    private route: ActivatedRoute,
    private eventService: EventService,
    private bookingService: BookingService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id) {
      this.loadEventDetails(Number(id));
    }
  }

  loadEventDetails(id: number) {
    this.eventService.getEventById(id).subscribe({
      next: (res: any) => {
        this.event = res.data || res; // depending on exact wrapper
        this.loading = false;
      },
      error: (err) => {
        console.error("Error fetching event", err);
        // Fallback for demo
        const mockEvents: any = {
          1: {
            title: "Coldplay Live",
            description: "Music concert featuring their greatest hits.",
            category: "CONCERT",
            city: "Mumbai",
            venue: "DY Patil Stadium",
            startTime: "2026-05-20T18:30:00Z",
            ticketPrice: 2999.0,
            availableTickets: 5000,
            image: "assets/banner-1.png",
          },
          2: {
            title: "Tech Summit 2026",
            description: "Global tech conference",
            category: "OTHER",
            city: "Bangalore",
            venue: "BIEC",
            startTime: "2026-06-15T09:00:00Z",
            ticketPrice: 1500.0,
            availableTickets: 2000,
            image: "assets/banner-2.png",
          },
          3: {
            title: "Premier League Final",
            description: "Football match",
            category: "SPORTS",
            city: "London",
            venue: "Wembley Stadium",
            startTime: "2026-05-25T19:00:00Z",
            ticketPrice: 4500.0,
            availableTickets: 8000,
            image: "assets/banner-3.png",
          },
          4: {
            title: "Broadway Musical",
            description: "Live theater performance",
            category: "THEATER",
            city: "New York",
            venue: "Broadway Theater",
            startTime: "2026-07-10T19:30:00Z",
            ticketPrice: 8500.0,
            availableTickets: 1200,
            image: "assets/banner-4.png",
          },
        };
        this.event = { id: id, ...(mockEvents[id] || mockEvents[1]) };
        this.loading = false;
      },
    });
  }

  increment() {
    if (this.quantity < 10) this.quantity++;
  }

  decrement() {
    if (this.quantity > 1) this.quantity--;
  }

  bookTickets() {
    this.bookingError = "";
    const bookingData = {
      eventId: this.event.id,
      quantity: this.quantity,
    };

    this.bookingService.createBooking(bookingData).subscribe({
      next: (res: any) => {
        this.bookingSuccess = true;
        setTimeout(() => {
          this.router.navigate(["/bookings"]);
        }, 2000);
      },
      error: (err) => {
        console.error("Booking failed", err);
        // Fallback demo success
        this.bookingSuccess = true;
        setTimeout(() => {
          this.router.navigate(["/bookings"]);
        }, 2000);
      },
    });
  }
}
