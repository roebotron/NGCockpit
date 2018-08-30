import { Component, OnInit, Input } from '@angular/core';
import { Cockpit } from '../cockpit';

@Component({
  selector: 'app-cockpit-detail',
  templateUrl: './cockpit-detail.component.html',
  styleUrls: ['./cockpit-detail.component.scss']
})
export class CockpitDetailComponent implements OnInit {

  @Input() cockpit: Cockpit;
  constructor() { }

  ngOnInit() {
  }

}
