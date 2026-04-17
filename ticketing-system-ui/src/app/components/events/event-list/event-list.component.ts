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

  private fallbackImages = [
    "assets/banner-1.png",
    "assets/banner-2.png",
    "assets/banner-3.png",
    "assets/banner-4.png",
  ];

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
        let fetchedEvents = res.data?.content || [];
        this.events = fetchedEvents.map((event: any, index: number) => {
          if (!event.image) {
            event.image =
              this.fallbackImages[index % this.fallbackImages.length];
          }
          return event;
        });
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
            image: "assets/banner-1.png",
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
            image: "assets/banner-2.png",
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
            image: "assets/banner-3.png",
          },
          {
            id: 4,
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
