package com.main.sistema_moedas.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.main.sistema_moedas.model.Endereco;
import com.main.sistema_moedas.model.usuario.Professor;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.UsuarioRepository;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    @Autowired
    private UsuarioRepository uRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("")
    public ModelAndView homeProfessor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor professor = (Professor) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/usuario");
        mv.addObject("user", professor);
        mv.addObject("conta", professor.getConta());
        return mv;
    }

    @GetMapping("/editar")
    public ModelAndView editaProfessor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/editar");
        mv.addObject("user", ((Professor) user));
        mv.addObject("end", user.getEndereco());
        mv.addObject("tipo", "Profesor");
        return mv;
    }

    @PostMapping("/editar")
    public String updateProfessor(Professor professor, Endereco end, String senhaAtual) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Professor user = ((Professor) auth.getPrincipal());
        if (!encoder.matches(senhaAtual, user.getSenha()) && !(user.getId() == professor.getId()))
            return "redirect:/professor";

        if (professor.getSenha().equals("")) {
            professor.setSenha(user.getSenha());
        }else {
            professor.setSenha(encoder.encode(professor.getSenha()));
        }

        professor.setEndereco(end);
        professor.setRoles(user.getRoles());
        professor.setInstituicao(user.getInstituicao());
        professor.setConta(user.getConta());

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(professor, user.getAuthorities(),updatedAuthorities);

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        uRepository.save(professor);

        return "redirect:/professor";
    }
}
