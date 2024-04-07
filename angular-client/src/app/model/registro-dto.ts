import { Avatar } from "./avatar";

export class RegistroDTO {
    constructor(
        public id: number,
        public nombre: string,
        public contrasena: string,
        public contrasena2: string,
        public rol: string | null,
        public avatar: Avatar | null
    ) { }
}
