import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConferenceComponent } from './conference/conference.component';
import { ConferenceService } from './conference.service';



@NgModule({
  declarations: [
    ConferenceComponent
  ],
  imports: [
    CommonModule
  ],
  providers: [
    ConferenceService
  ],
  exports: [
    ConferenceComponent
  ]
})
export class WebsocketModule { }
