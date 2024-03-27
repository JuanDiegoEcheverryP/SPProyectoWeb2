package com.example.spaceinvaders.model.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CompraVentaDTO {

    //COMPRA Y VENTA
    Long idproducto;

    Integer cantidadProducto;

    Long idPlaneta;

    Long idNave;

    Float total;


    CompraVentaDTO(Long idproducto, Integer cantidadProducto, Long idNave,Long idBodega,Float total)
    {
        this.idproducto=idproducto; 
        this.idPlaneta=idPlaneta; 
        this.idNave=idBodega; 
        this.cantidadProducto=cantidadProducto;
        this.total=total;
    }
}
