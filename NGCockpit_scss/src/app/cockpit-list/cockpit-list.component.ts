import { Component, OnInit } from '@angular/core';
import { CockpitService } from '../cockpit.service';
import { Cockpit } from '../cockpit';

@Component({
  selector: 'app-cockpit-list',
  templateUrl: './cockpit-list.component.html',
  styleUrls: ['./cockpit-list.component.scss']
})
export class CockpitListComponent implements OnInit {

  cockpitList = Array<Cockpit>();
  selectedCockpit: Cockpit;

  constructor(private service: CockpitService) { }

  ngOnInit() {
      this.service.getAll().subscribe(data => this.cockpitList = data);
  }

  onSelect(cockpit: Cockpit): void {
    this.selectedCockpit = cockpit;
  }
}
