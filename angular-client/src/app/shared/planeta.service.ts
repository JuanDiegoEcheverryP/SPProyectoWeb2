import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Observable } from 'rxjs';
import { Nave } from '../model/nave';
import { Planeta } from '../model/planeta';

@Injectable({
    providedIn: 'root'
})
export class PlanetaService {

    constructor(
    private http: HttpClient
    ) {}

    private headers = new HttpHeaders(
        { "Content-Type": "application/json" }
    )

    listarPlanetasPorId(id: number): Observable<Planeta[]> {
        return this.http.get<Planeta[]>(`${environment.serverUrl}/api/estrella/planetas/${id}`)
    }
}
