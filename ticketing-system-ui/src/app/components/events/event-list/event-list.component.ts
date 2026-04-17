import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit {
  events: any[] = [];

  constructor() { }

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents() {
    // Mock data based on ui-md.md
    this.events = [
      {
        id: 1,
        title: 'Coldplay Live',
        description: 'Music concert',
        category: 'CONCERT',
        city: 'Mumbai',
        venue: 'DY Patil Stadium',
        startTime: '2026-05-20T18:30:00Z'
      },
      {
        id: 2,
        title: 'Tech Summit 2026',
        description: 'Global tech conference',
        category: 'CONFERENCE',
        city: 'Bangalore',
        venue: 'BIEC',
        startTime: '2026-06-15T09:00:00Z'
      },
      {
        id: 3,
        title: 'Premier League Final',
        description: 'Football match',
        category: 'SPORTS',
        city: 'London',
        venue: 'Wembley Stadium',
        startTime: '2026-05-25T19:00:00Z'
      }
    ];
  }
}
