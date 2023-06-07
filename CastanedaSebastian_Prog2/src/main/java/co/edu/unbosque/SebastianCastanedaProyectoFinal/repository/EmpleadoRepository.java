package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;

public interface EmpleadoRepository extends CrudRepository<Empleado, Integer>{

    public List<Empleado> findAll();

    public Empleado findAllByCedula(Integer cedula);

}
