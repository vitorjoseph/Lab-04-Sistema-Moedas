package com.main.sistema_moedas.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Endereco;
import com.main.sistema_moedas.model.Instituicao;
import com.main.sistema_moedas.model.usuario.Professor;
import com.main.sistema_moedas.model.usuario.Role;
import com.main.sistema_moedas.repository.InstituicaoRepository;
import com.main.sistema_moedas.repository.RoleRepository;
import com.main.sistema_moedas.repository.UsuarioRepository;

@SuppressWarnings("unused")
@Configuration
public class AddEntity {

    @Autowired
    UsuarioRepository uRepository;
    @Autowired
    InstituicaoRepository iRepository;
    @Autowired
    private RoleRepository rRepository;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//    @Bean
    public void addInstituicao() {
        List<Instituicao> i = new ArrayList<>();
        i.add(new Instituicao("PUC Minas"));
        i.add(new Instituicao("UNI-BH"));
        i.add(new Instituicao("UFMG"));
        i.add(new Instituicao("USP"));
        i.add(new Instituicao("PUC-SP"));
        iRepository.saveAll(i);
    }

//    @Bean
    public void addRoles() {
        Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        Role prof = rRepository.findByNameRole("ROLE_PROFESSOR").orElse(new Role("ROLE_PROFESSOR"));
        Role aluno = rRepository.findByNameRole("ROLE_ALUNO").orElse(new Role("ROLE_ALUNO"));
        Role empresa = rRepository.findByNameRole("ROLE_EMPRESA").orElse(new Role("ROLE_EMPRESA"));
        List<Role> roles = new ArrayList<>();
        roles.add(prof);
        roles.add(admin);
        roles.add(empresa);
        roles.add(aluno);
        rRepository.saveAll(roles);
    }

//    @Bean
    public void addProfessores() {
        Role admin = rRepository.findByNameRole("ROLE_ADMIN").orElse(new Role("ROLE_ADMIN"));
        Role prof = rRepository.findByNameRole("ROLE_PROFESSOR").orElse(new Role("ROLE_PROFESSOR"));
        List<Role> roles = new ArrayList<>();
        roles.add(admin);
        roles.add(prof);
        Instituicao i = iRepository.findById(1l).get();
        Endereco e = new Endereco();
        e.setBairro("Bairro");
        e.setCep("12345678900");
        e.setCidade("Cidade");
        e.setEstado("Estado");
        e.setNumero(11);
        e.setRua("Rua das Cores");

        Professor p1 = new Professor();
        p1.setCpf("cpfUnico1");
        p1.setDepartamento("Ciências Exatas");
        p1.setEmail("professor1@email.com");
        p1.setInstituicao(i);
        p1.setNome("Professor 1");
        p1.setSenha(encoder.encode("123456"));
        p1.setEndereco(e);
        p1.setRoles(roles);
        Conta c1 = new Conta();
        c1.setSaldo(3000);
        p1.setConta(c1);

        Professor p2 = new Professor();
        p1.setCpf("cpfUnico2");
        p1.setDepartamento("Letras");
        p1.setEmail("professor2@email.com");
        p1.setInstituicao(i);
        p1.setNome("Professor 2");
        p1.setSenha(encoder.encode("123456"));
        p1.setEndereco(e);
        p1.setRoles(roles);
        Conta c2 = new Conta();
        c2.setSaldo(5000);
        p1.setConta(c2);


        uRepository.save(p1);
    }

}
