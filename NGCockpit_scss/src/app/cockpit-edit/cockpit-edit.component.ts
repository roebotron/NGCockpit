import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { ActivatedRoute, Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { CockpitService } from '../cockpit.service';
import { Cockpit } from '../cockpit';

@Component({
  selector: 'app-cockpit-edit',
  templateUrl: './cockpit-edit.component.html',
  styleUrls: ['./cockpit-edit.component.scss']
})
export class CockpitEditComponent implements OnInit, OnDestroy {
  cockpit: Cockpit = new Cockpit;
  sub: Subscription;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private cockpitService: CockpitService) {
  }

  ngOnInit() {
    this.sub = this.route.params.subscribe(params => {
      const id = params['id'];
      if (id) {
        this.cockpitService.get(id).subscribe((cockpit: Cockpit) => {
          if (cockpit) {
            this.cockpit = cockpit;
          } else {
            console.log(`Cockpit with id '${id}' not found, returning to list`);
            this.gotoList();
          }
        });
      }
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }

  gotoList() {
    this.router.navigate(['/cockpit-list']);
  }

  save() {
    this.cockpitService.save(this.cockpit).subscribe(result => {
      this.gotoList();
    }, error => console.error(error));
  }
/*
  save(form: NgForm) {
    this.cockpitService.save(form as Cockpit).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }

  remove(href) {
    this.cockpitService.remove(href).subscribe(result => {
      this.gotoList();
    }, error => console.error(error))
  }
*/
}
