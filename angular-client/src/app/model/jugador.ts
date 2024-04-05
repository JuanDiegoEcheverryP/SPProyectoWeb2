import { Avatar } from "./avatar";

export class Jugador {

    constructor(
        public id: number,
        public nombre: string,
        public contrasena: string,
        public rol: string | null,
        public avatar: Avatar | null
    ) { }
}
