package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Inventario;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Producto;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;

public interface InventarioRepository extends CrudRepository<Inventario, Integer>{
	
	public List<Inventario> findAll();
	
	public List<Inventario> findByIdProductoAndIdSucursal(Producto producto, Sucursal sucursal);
	
	public Optional<Inventario> findById(Integer id);
	
	public List<Inventario> findAllByIdSucursal(Sucursal sucursal);
	
	public Inventario findAllByIdSucursalAndIdProductoAndCantidad(Sucursal sucursal,Producto producto, Integer cantidad);
	
}
