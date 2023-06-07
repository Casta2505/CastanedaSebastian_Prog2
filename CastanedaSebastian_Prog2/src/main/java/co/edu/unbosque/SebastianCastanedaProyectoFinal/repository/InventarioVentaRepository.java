package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.InventarioVenta;

public interface InventarioVentaRepository extends CrudRepository<InventarioVenta, Integer>{

    public List<InventarioVenta> findAll();

}
