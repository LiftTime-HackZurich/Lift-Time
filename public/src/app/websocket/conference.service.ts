import { Injectable } from '@angular/core';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'any'
})
export class ConferenceService {
 
  socket = new SockJS('http://localhost:8080/lift-time-websocket');

  stompClient: Stomp.Client = Stomp.over(this.socket);

  clientEmitter = new BehaviorSubject(this.stompClient);

  constructor() {
    this.connect();
  }

  connect() {
    this.stompClient.connect({}, (frame: any) => {
      if(!!this.stompClient.connected)
        this.clientEmitter.next(this.stompClient);
    });
  }

  getClient() {
    return this.clientEmitter;
  }

  disconnect() {
    if (this.stompClient != null) {
      this.stompClient.disconnect(() => {});
    }
 
    console.log('Disconnected!');
  }

  send(destination: string = 'meetings/1234/themes/sport', data : any = {}) {
    this.stompClient.send(
      `/app/v1/${destination}`,
      {},
      JSON.stringify(data)
    );
  }

}
