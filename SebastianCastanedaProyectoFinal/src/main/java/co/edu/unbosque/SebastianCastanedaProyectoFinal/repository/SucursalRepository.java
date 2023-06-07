package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Ciudad;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;

public interface SucursalRepository extends CrudRepository<Sucursal, Integer>{

    public List<Sucursal> findAll();

    public List<Sucursal> findAllByIdCiudad(Ciudad idCiudad);

}
