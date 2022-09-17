import { Component, OnInit } from '@angular/core';
import { Stomp } from '@stomp/stompjs';
import { ConferenceService } from '../conference.service';

const SUBSCRIBER_NAME = '/topic/meetings/1234';
const PUBLISHER_NAME = '/app/v1/meetings/1234/themes/sport'

@Component({
  selector: 'app-conference',
  templateUrl: './conference.component.html',
  styleUrls: ['./conference.component.scss']
})
export class ConferenceComponent implements OnInit {

   ws: any;

  constructor(private conferenceService: ConferenceService) { }

  ngOnInit(): void {
    this.conferenceService.getClient().subscribe(client => {
      this.ws = client;

      if(client?.connected)
        this.listenToMessage();

    })
  }

  listenToMessage() {
    this.ws.subscribe(SUBSCRIBER_NAME, (data: any) => {
      console.log("GELDIM ", JSON.parse(data?.body));
    })
  }

  sendMessage() {
    this.ws.send(
      PUBLISHER_NAME,{}, JSON.stringify({ "name": "cemil"})
    );
  }
}
