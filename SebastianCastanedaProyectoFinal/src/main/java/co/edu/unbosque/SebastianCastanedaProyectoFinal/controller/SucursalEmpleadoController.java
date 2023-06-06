package co.edu.unbosque.SebastianCastanedaProyectoFinal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.Empleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.model.SucursalEmpleado;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.EmpleadoRepository;
import co.edu.unbosque.SebastianCastanedaProyectoFinal.repository.SucursalEmpleadoRepository;

@Controller
@RequestMapping
public class SucursalEmpleadoController {
	@Autowired
	private SucursalEmpleadoRepository daoSucursalEmpleado;

	@Autowired
	private EmpleadoRepository daoEmpleadoRepository;

	@GetMapping("/ListarEmpleadosCedula")
	public String getEmpleados(@RequestParam(required = false) Integer cedula, Model model) {
	    if (cedula != null) {
	        Empleado empleado = daoEmpleadoRepository.findAllByCedula(cedula);
	        if (empleado != null) {
	            List<SucursalEmpleado> all = daoSucursalEmpleado.findAllByIdEmpleado(empleado);
	            model.addAttribute("empleados", all);
	        } else {
	            model.addAttribute("empleados", new ArrayList<>());
	        }
	    } else {
	        List<SucursalEmpleado> all = daoSucursalEmpleado.findAll();
	        model.addAttribute("empleados", all);
	    }
	    return "Personal";
	}

	@GetMapping("/ListarEmpleados")
	public String getEmpleados(Model model) {
		List<SucursalEmpleado> all = daoSucursalEmpleado.findAll();
		model.addAttribute("empleados", all);
		return "Personal";
	}

}
