export class UsuarioDTO {
    constructor(
        public id: number,
        public nombre: string,
        public rol: string,
        public avatar: string,
        public idNave: number,
        public token: string,
    ) { }
}
