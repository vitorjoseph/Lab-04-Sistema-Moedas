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
import com.main.sistema_moedas.model.usuario.Empresa;
import com.main.sistema_moedas.model.usuario.Role;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.RoleRepository;
import com.main.sistema_moedas.repository.UsuarioRepository;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private RoleRepository rRepository;
    @Autowired
    private UsuarioRepository uRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping("/new")
    public String novo(Empresa emp, Endereco e) {

        Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        Role empresa = rRepository.findByNameRole("ROLE_EMPRESA").orElse(new Role("ROLE_EMPRESA"));
        List<Role> listaderoles = new ArrayList<>();
        listaderoles.add(admin);
        listaderoles.add(empresa);
        emp.setSenha(encoder.encode(emp.getSenha()));
        emp.setRoles(listaderoles);
        emp.setEndereco(e);
        uRepository.save(emp);
        return ("redirect:/login?cadastrado");
    }

    @GetMapping("")
    public ModelAndView homeEmpresa() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Empresa empresa = (Empresa) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/usuario");
        mv.addObject("user", empresa);
        return mv;
    }

    @GetMapping("/editar")
    public ModelAndView editaProfessor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/editar");
        mv.addObject("user", ((Empresa) user));
        mv.addObject("end", user.getEndereco());
        mv.addObject("tipo", "Empresa");
        return mv;
    }

    @PostMapping("/editar")
    public String updateProfessor(Empresa empresa, Endereco end, String senhaAtual) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Empresa user = ((Empresa) auth.getPrincipal());
        if (!encoder.matches(senhaAtual, user.getSenha()) && !(user.getId() == empresa.getId()))
            return "redirect:/empresa";

        if (empresa.getSenha().equals("")) {
            empresa.setSenha(user.getSenha());
        }else {
            empresa.setSenha(encoder.encode(empresa.getSenha()));
        }

        empresa.setEndereco(end);
        empresa.setRoles(user.getRoles());

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(empresa, user.getAuthorities(),updatedAuthorities);


        SecurityContextHolder.getContext().setAuthentication(newAuth);

        uRepository.save(empresa);

        return "redirect:/empresa";
    }
}
