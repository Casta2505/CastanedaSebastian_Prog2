package co.edu.unbosque.SebastianCastanedaProyectoFinal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Sucursal;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.SucursalEmpleado;

public interface SucursalEmpleadoRepository extends CrudRepository<SucursalEmpleado, Integer>{

    public List<SucursalEmpleado> findAll();

    public List<SucursalEmpleado> findAllByIdSucursal(Sucursal lugar);

    public List<SucursalEmpleado> findAllByIdEmpleado(Empleado empleado);

}
