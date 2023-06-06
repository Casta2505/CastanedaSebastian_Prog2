package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Inventario;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Producto;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;

public interface InventarioVentaRepository extends CrudRepository<Inventario, Integer>{
	
	public List<Inventario> findAll();
	
	public List<Inventario> findByIdProductoAndIdSucursal(Producto producto, Sucursal sucursal);
}
