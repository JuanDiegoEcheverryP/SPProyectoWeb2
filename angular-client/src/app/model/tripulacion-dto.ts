import { Nave } from "./nave";

export class TripulacionDTO {
    constructor(
        public capitan: string,
        public cantidadTripulantes: number,
        public nave: Nave,
    ) { }
}
