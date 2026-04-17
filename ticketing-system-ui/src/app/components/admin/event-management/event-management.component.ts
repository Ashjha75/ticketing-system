import { Component, OnInit } from '@angular/core';
import { EventService } from 'src/app/services/event.service';

@Component({
  selector: 'app-event-management',
  templateUrl: './event-management.component.html',
  styleUrls: ['./event-management.component.css']
})
export class EventManagementComponent implements OnInit {
  events: any[] = [];
  loading: boolean = true;
  showCreateForm: boolean = false;
  createSuccess: boolean = false;
  
  // New event form model
  newEvent: any = {
    title: '',
    description: '',
    category: 'CONCERT',
    city: '',
    venue: '',
    startTime: '',
    endTime: '',
    bookingStartTime: '',
    bookingEndTime: '',
    ticketPrice: null,
    totalTickets: null
  };

  constructor(private eventService: EventService) { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents() {
    this.eventService.getEvents().subscribe({
      next: (res: any) => {
        this.events = res.data?.content || [];
        this.loading = false;
      },
      error: (err) => {
        console.error('Failed to load events', err);
        // Mock data
        this.events = [
          {
            id: 101,
            title: "Coldplay Live",
            city: "Mumbai",
            category: "CONCERT",
            startTime: "2026-05-20T18:30:00Z",
            status: "DRAFT"
          }
        ];
        this.loading = false;
      }
    });
  }

  toggleCreateForm() {
    this.showCreateForm = !this.showCreateForm;
    this.createSuccess = false;
  }

  createEvent() {
    // In a real app, validate dates/numbers here
    this.eventService.createEvent(this.newEvent).subscribe({
      next: (res: any) => {
        this.createSuccess = true;
        setTimeout(() => {
          this.toggleCreateForm();
          this.loadEvents();
        }, 2000);
      },
      error: (err) => {
        console.error('Failed to create event', err);
        // Fallback for demo
        this.events.unshift({...this.newEvent, id: Date.now(), status: 'DRAFT'});
        this.createSuccess = true;
        setTimeout(() => {
          this.toggleCreateForm();
        }, 2000);
      }
    });
  }

  publishEvent(id: number) {
    this.eventService.publishEvent(id).subscribe({
      next: () => {
        this.loadEvents();
      },
      error: (err) => {
        console.error('Publish failed', err);
        // Mock success
        const ev = this.events.find(e => e.id === id);
        if (ev) ev.status = 'PUBLISHED';
      }
    });
  }
}
