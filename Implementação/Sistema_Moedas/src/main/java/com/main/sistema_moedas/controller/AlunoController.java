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

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Endereco;
import com.main.sistema_moedas.model.Instituicao;
import com.main.sistema_moedas.model.usuario.Aluno;
import com.main.sistema_moedas.model.usuario.Role;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.InstituicaoRepository;
import com.main.sistema_moedas.repository.RoleRepository;
import com.main.sistema_moedas.repository.UsuarioRepository;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private RoleRepository rRepository;
    @Autowired
    private UsuarioRepository uRepository;
    @Autowired
    private InstituicaoRepository iRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    @PostMapping("/new")
    public String novo(Aluno a, Endereco e, Long idInstituicao) {
        Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        Role aluno = rRepository.findByNameRole("ROLE_ALUNO").orElse(new Role("ROLE_ALUNO"));
        List<Role> listaderoles = new ArrayList<>();
        Instituicao inst = iRepository.findById(idInstituicao).get();
        listaderoles.add(admin);
        listaderoles.add(aluno);
        a.setRoles(listaderoles);
        a.setSenha(encoder.encode(a.getSenha()));
        a.setEndereco(e);
        a.setInstituicao(inst);
        a.setConta(new Conta());
        uRepository.save(a);
        return ("redirect:/login?cadastrado");
    }

    @GetMapping("")
    public ModelAndView homeAluno() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Aluno aluno = (Aluno) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/usuario");
        mv.addObject("user", aluno);
        mv.addObject("conta", aluno.getConta());
        return mv;
    }

    @GetMapping("/editar")
    public ModelAndView editaAluno() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        ModelAndView mv = new ModelAndView("usuarios/editar");
        mv.addObject("user", ((Aluno) user));
        mv.addObject("end", user.getEndereco());
        mv.addObject("tipo", "Aluno");
        return mv;
    }

    @PostMapping("/editar")
    public String updateAluno(Aluno aluno, Endereco end, String senhaAtual) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Aluno user = ((Aluno) auth.getPrincipal());
        if (!encoder.matches(senhaAtual, user.getSenha()) && !(user.getId() == aluno.getId()))
            return "redirect:/aluno";

        if (aluno.getSenha().equals("")) {
            aluno.setSenha(user.getSenha());
        }else {
            aluno.setSenha(encoder.encode(aluno.getSenha()));
        }

        aluno.setEndereco(end);
        aluno.setRoles(user.getRoles());
        aluno.setInstituicao(user.getInstituicao());
        aluno.setConta(user.getConta());

        List<GrantedAuthority> updatedAuthorities = new ArrayList<>(user.getAuthorities());
        Authentication newAuth = new UsernamePasswordAuthenticationToken(aluno, user.getAuthorities(),updatedAuthorities);


        SecurityContextHolder.getContext().setAuthentication(newAuth);

        uRepository.save(aluno);

        return "redirect:/aluno";
    }
}
