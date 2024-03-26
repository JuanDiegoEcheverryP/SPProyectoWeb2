package com.example.spaceinvaders.model.DTO;

public class CompraVentaDTO {

    //COMPRA Y VENTA
    Long idproducto;

    Integer cantidadProducto;

    Long idPlaneta;

    Long idBodega;

    Float total;


    CompraVentaDTO(Long idproducto, Integer cantidadProducto, Long idPlaneta,Long idBodega,Float total)
    {
        this.idproducto=idproducto; 
        this.idPlaneta=idPlaneta; 
        this.idBodega=idBodega; 
        this.cantidadProducto=cantidadProducto;
        this.total=total;
    }
}
