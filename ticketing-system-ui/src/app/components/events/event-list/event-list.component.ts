import { Component, OnInit } from "@angular/core";
import { EventService } from "src/app/services/event.service";

@Component({
  selector: "app-event-list",
  templateUrl: "./event-list.component.html",
  styleUrls: ["./event-list.component.css"],
})
export class EventListComponent implements OnInit {
  events: any[] = [];
  filters = {
    keyword: "",
    category: "",
    city: "",
  };

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  setCategory(category: string) {
    this.filters.category = category;
    this.loadEvents();
  }

  loadEvents() {
    this.eventService.getEvents(this.filters).subscribe({
      next: (res: any) => {
        this.events = res.data?.content || [];
      },
      error: (err) => {
        console.error("Error loading events", err);
        // Fallback to mock data if API fails (for demo purposes)
        let mockEvents = [
          {
            id: 1,
            title: "Coldplay Live",
            description: "Music concert",
            category: "CONCERT",
            city: "Mumbai",
            venue: "DY Patil Stadium",
            startTime: "2026-05-20T18:30:00Z",
            ticketPrice: 2999.0,
            availableTickets: 5000,
          },
          {
            id: 2,
            title: "Tech Summit 2026",
            description: "Global tech conference",
            category: "OTHER",
            city: "Bangalore",
            venue: "BIEC",
            startTime: "2026-06-15T09:00:00Z",
            ticketPrice: 1500.0,
            availableTickets: 2000,
          },
          {
            id: 3,
            title: "Premier League Final",
            description: "Football match",
            category: "SPORTS",
            city: "London",
            venue: "Wembley Stadium",
            startTime: "2026-05-25T19:00:00Z",
            ticketPrice: 4500.0,
            availableTickets: 8000,
          },
        ];

        if (this.filters.category) {
          mockEvents = mockEvents.filter(
            (e) => e.category === this.filters.category,
          );
        }
        if (this.filters.keyword) {
          const kw = this.filters.keyword.toLowerCase();
          mockEvents = mockEvents.filter(
            (e) =>
              e.title.toLowerCase().includes(kw) ||
              e.city.toLowerCase().includes(kw) ||
              e.category.toLowerCase().includes(kw),
          );
        }

        this.events = mockEvents;
      },
    });
  }
}
