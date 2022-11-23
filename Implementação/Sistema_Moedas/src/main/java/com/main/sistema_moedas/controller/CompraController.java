package com.main.sistema_moedas.controller;

import static java.lang.Long.toHexString;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.main.sistema_moedas.model.Compra;
import com.main.sistema_moedas.model.Conta;
import com.main.sistema_moedas.model.Vantagem;
import com.main.sistema_moedas.model.usuario.Aluno;
import com.main.sistema_moedas.model.usuario.Usuario;
import com.main.sistema_moedas.repository.CompraRepository;
import com.main.sistema_moedas.repository.ContaRepository;
import com.main.sistema_moedas.repository.VantagemRepository;

@Controller
@RequestMapping("/compra")
public class CompraController {

    private static final String PATH = "src/main/resources/static/cupons";

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private VantagemRepository vantagemRepository;

    @GetMapping("/new/{id}")
    public String nova(@PathVariable long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Aluno aluno = ((Aluno) auth.getPrincipal());
        Vantagem v = vantagemRepository.findById(id).orElse(null);
        Conta conta = aluno.getConta();
        if ((v == null) || (conta.getSaldo() < v.getValor()))
            return "redirect:/vantagem/listar";
        Compra compra = new Compra(v.getValor(), aluno, v, LocalDateTime.now());
        conta.retirar(v.getValor());
        compra = compraRepository.save(compra);
        contaRepository.save(conta);

        String codigo = toHexString(compra.getId()) + toHexString(v.getId()) + toHexString(aluno.getId())
                + toHexString(v.getEmpresa().getId());
        String nomeAluno = "Cliente: " + aluno.getNome();
        String produto = "Produto: " + v.getProduto();
        String descricao = "Descricao: " + v.getDescricao();
        String data = "Data: " + compra.getData().format(DateTimeFormatter.ofPattern("dd LLLL u, HH:mm:ss"));

        String pathAluno = PATH + "/" + aluno.getId() + "/";
        String pathEmpresa = PATH + "/" + v.getEmpresa().getId() + "/";
        String nomeArquivo = codigo + ".pdf";

        Document doc = new Document();
        try {
            if (!Files.exists(Paths.get(pathAluno))) {
                Files.createDirectories(Paths.get(pathAluno));
            }
            if (!Files.exists(Paths.get(pathEmpresa))) {
                Files.createDirectories(Paths.get(pathEmpresa));
            }

            PdfWriter.getInstance(doc, new FileOutputStream(pathAluno + nomeArquivo));
            PdfWriter.getInstance(doc, new FileOutputStream(pathEmpresa + nomeArquivo));
            doc.open();
            doc.addTitle("Cupom");

            Font font = FontFactory.getFont(FontFactory.TIMES, 16, BaseColor.BLACK);
            Paragraph p1 = new Paragraph("Código: " + codigo, font);
            Paragraph p2 = new Paragraph(nomeAluno, font);
            Paragraph p3 = new Paragraph(produto, font);
            Paragraph p4 = new Paragraph(descricao, font);
            Paragraph p5 = new Paragraph(data, font);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p4.setAlignment(Paragraph.ALIGN_CENTER);
            p5.setAlignment(Paragraph.ALIGN_CENTER);

            doc.add(p1);
            doc.add(p2);
            doc.add(p3);
            doc.add(p4);
            doc.add(p5);
            doc.close();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/compra/cupons";
    }

    @GetMapping("/cupons")
    public ModelAndView cupons() {
        ModelAndView mv = new ModelAndView("compra/cupom");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Usuario user = (Usuario) auth.getPrincipal();
        String path = PATH + "/" + user.getId() + "/";
        mv.addObject("id",user.getId());

        try {
            List<String> docs = Files.list(Paths.get(path)).filter(f -> !Files.isDirectory(f)).map(Path::getFileName)
                    .map(Path::toString).collect(Collectors.toList());

            mv.addObject("docs", docs);
        }catch (NoSuchFileException e) {
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return mv;
    }

}
