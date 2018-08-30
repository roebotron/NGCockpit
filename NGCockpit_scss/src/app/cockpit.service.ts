import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Cockpit } from './cockpit';

@Injectable()
export class CockpitService {

  private API = '//esprimo.fritz.box:8080/cockpit/';
  constructor(private http: HttpClient) { }

  getAll(): Observable<Cockpit[]> {
      return this.http.get<Cockpit[]>(this.API + 'all');
  }

  get(guid: string): Observable<Cockpit> {
      return this.http.get<Cockpit>(this.API + guid);
  }

  save( cockpit: Cockpit ): Observable<any> {
      let result: Observable<Object>;
      if ( cockpit.guid ) {
          result = this.http.put( this.API + `${cockpit.guid}`, cockpit );
          console.log('put');
      } else {
          result = this.http.post( this.API, cockpit );
          console.log('post');
      }
      return result;
  }

  remove( guid: string ): Observable<any> {
      return this.http.delete( this.API + `${guid}` );
  }
}
