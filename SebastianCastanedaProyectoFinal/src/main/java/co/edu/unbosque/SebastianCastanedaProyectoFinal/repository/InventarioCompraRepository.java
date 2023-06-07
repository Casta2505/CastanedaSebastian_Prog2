package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioCompra;

public interface InventarioCompraRepository extends CrudRepository<InventarioCompra, Integer>{

    public List<InventarioCompra> findAll();

}
