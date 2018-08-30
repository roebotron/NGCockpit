import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CockpitEditComponent } from './cockpit-edit/cockpit-edit.component';
import { CockpitListComponent } from './cockpit-list/cockpit-list.component';


const appRoutes: Routes = [
  { path: '', redirectTo: '/cockpit-list', pathMatch: 'full' },
  { path: 'cockpit-list', component: CockpitListComponent },
  { path: 'cockpit-add',  component: CockpitEditComponent },
  { path: 'cockpit-edit/:id', component: CockpitEditComponent }
];

@NgModule({
  exports: [ RouterModule ],
  imports: [ RouterModule.forRoot(appRoutes) ]
})
export class AppRoutingModule {}
