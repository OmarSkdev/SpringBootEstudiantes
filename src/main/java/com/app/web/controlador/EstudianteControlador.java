package com.app.web.controlador;


import com.app.web.entidad.Estudiante;
import com.app.web.repositorio.EstudianteRepositorio;
import com.app.web.servicio.EstudianteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EstudianteControlador {
    @Autowired
    private EstudianteServicio servicio;

    @GetMapping({"/estudiantes","/"})
    public String Listarestudiantes(Model modelo) {
        modelo.addAttribute("estudiantes", servicio.listarTodosLosEstudiantes());
        return "estudiantes";
    }
    @GetMapping("/estudiantes/nuevo")
    public String mostrarFormularioDeRegistrarEstudiante(Model modelo){
        Estudiante estudiante = new Estudiante();
        modelo.addAttribute("estudiante", estudiante);
        return "crear_estudiante";

    }

    @PostMapping("/estudiantes")
    public String guardarEstudiante(@RequestParam String email, @ModelAttribute("estudiante") Estudiante estudiante, RedirectAttributes redirectAttributes) {
        if ( servicio.emailExiste(email)) {
            redirectAttributes.addFlashAttribute("mensaje", "Estudiante ya existe");
            return "redirect:/estudiantes/nuevo";
        }
        servicio.guardarEstudiante(estudiante);
        return "redirect:/estudiantes";
    }
    @GetMapping("/estudiantes/editar/{id}")
    public String mostrarFormularioEditarEstudiante(@PathVariable("id") Long id, Model modelo) {
        modelo.addAttribute("estudiante", servicio.buscarEstudiantePorId(id));
        return "editar_estudiante";
    }

    @PostMapping("/estudiantes/{id}")
    public String actualizarEstudiante(@PathVariable("id") Long id, @ModelAttribute("estudiante") Estudiante estudiante, Model modelo) {
        Estudiante estudianteExistente = servicio.buscarEstudiantePorId(id);
        estudianteExistente.setId(id);
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setApellido(estudiante.getApellido());
        estudianteExistente.setEmail(estudiante.getEmail());

        servicio.actualizarEstudiante(estudianteExistente);
        return "redirect:/estudiantes";
    }
    @GetMapping("/estudiantes/{id}")
    public String eliminarEstudiante(@PathVariable("id") Long id) {
        servicio.eliminarEstudiante(id);
        return "redirect:/estudiantes";
    }
}
