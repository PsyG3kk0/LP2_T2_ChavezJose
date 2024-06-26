package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.AreaEntity;
import com.example.demo.entity.EmpleadoEntity;

import com.example.demo.repository.AreaRepository;
import com.example.demo.repository.EmpleadoRepository;

@Controller
public class EmpleadoController {

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private AreaRepository areaRepository;

	@GetMapping({ "/empleados" })
	public String listarEmpleados(Model model) {
		List<EmpleadoEntity> empleados = empleadoRepository.findAll();
		model.addAttribute("empleados", empleados);
		return "empleados";
	}

	@GetMapping("/registrar_empleado")
	public String showRegistrarEmpleado(Model model) {
		List<AreaEntity> areas = areaRepository.findAll();
		model.addAttribute("areas", areas);
		model.addAttribute("empleado", new EmpleadoEntity());
		return "registrar_empleado";
	}

	@PostMapping("/registrar_empleado")
	public String registrarEmpleado(Model model, @ModelAttribute EmpleadoEntity empleado) {
		empleadoRepository.save(empleado);
		return "redirect:/empleados";
	}

	@GetMapping("/editar_empleado/{dni}")
	public String showEditarEmpleado(@PathVariable("dni") String dni, Model model) {
		Optional<EmpleadoEntity> optionalEmpleado = empleadoRepository.findById(dni);
		if (optionalEmpleado.isPresent()) {
			EmpleadoEntity empleado = optionalEmpleado.get();
			List<AreaEntity> areas = areaRepository.findAll();
			model.addAttribute("areas", areas);

			model.addAttribute("empleado", empleado);
			return "editar_empleado";
		} else {
			return "redirect:/empleados";
		}
	}

	@PostMapping("/editar_empleado")
	public String editarEmpleado(@ModelAttribute EmpleadoEntity empleado) {
		empleadoRepository.save(empleado);
		return "redirect:/empleados";
	}

	@GetMapping("/eliminar_empleado/{dni}")
	public String eliminarEmpleado(@PathVariable("dni") String dni) {
		empleadoRepository.deleteById(dni);
		return "redirect:/empleados";
	}
	
	@GetMapping("/buscar_empleado")
	public String buscarEmpleado(@RequestParam(value = "dni", required = false) String dni, Model model) {
	    List<EmpleadoEntity> empleados;
	    if (dni == null || dni.isEmpty()) {
	        empleados = empleadoRepository.findAll();
	    } else {
	        Optional<EmpleadoEntity> empleadoOptional = empleadoRepository.findById(dni);
	        if (empleadoOptional.isPresent()) {
	            empleados = List.of(empleadoOptional.get());
	        } else {
	            empleados = List.of();
	        }
	    }
	    model.addAttribute("empleados", empleados);
	    return "empleados";
	}
	
	

}
