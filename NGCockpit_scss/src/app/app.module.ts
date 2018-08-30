import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { CockpitService } from './cockpit.service';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule, MatCardModule, MatInputModule, MatListModule, MatToolbarModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule } from '@angular/forms';

import { CockpitListComponent } from './cockpit-list/cockpit-list.component';
import { CockpitEditComponent } from './cockpit-edit/cockpit-edit.component';
import { AppRoutingModule } from './app-routing.module';
import { CockpitDetailComponent } from './cockpit-detail/cockpit-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    CockpitListComponent,
    CockpitEditComponent,
    CockpitDetailComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    MatToolbarModule,
    MatCardModule,
    MatInputModule,
    MatListModule,
    MatButtonModule,
    BrowserAnimationsModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [CockpitService],
  bootstrap: [AppComponent]
})
export class AppModule { }
