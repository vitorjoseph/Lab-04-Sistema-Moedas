package com.main.sistema_moedas.controller;

import com.main.sistema_moedas.model.usuario.Aluno;
import com.main.sistema_moedas.model.usuario.Professor;
import com.main.sistema_moedas.model.usuario.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getPrincipal() instanceof String) return mv;
        Usuario user = (Usuario) auth.getPrincipal();
        if (user instanceof Professor){
            mv.addObject("conta", ((Professor) user).getConta());
        }
        else if(user instanceof Aluno){
            mv.addObject("conta", ((Aluno) user).getConta());
        }
        return mv;
    }

}
