import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { ContainerComponent } from './components/container/container.component';
import { WebsocketModule } from './websocket/websocket.module';

@NgModule({
  declarations: [
    ContainerComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    WebsocketModule
  ],
  providers: [],
  bootstrap: [ContainerComponent]
})
export class AppModule { }
