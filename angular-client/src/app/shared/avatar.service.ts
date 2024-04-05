import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Avatar } from '../model/avatar';

@Injectable({
  providedIn: 'root'
})
export class AvatarService {

  constructor(
    private http: HttpClient
  ) {
  }

  private headers = new HttpHeaders(
    { "Content-Type": "application/json" }
  )

  listarAvatares(): Observable<Avatar[]> {
    return this.http.get<Avatar[]>(`${environment.serverUrl}/api/avatar/list`)
  }

  avatarId(id:number): Observable<Avatar> {
    return this.http.get<Avatar>(`${environment.serverUrl}/api/avatar/${id}`)
  }
}
