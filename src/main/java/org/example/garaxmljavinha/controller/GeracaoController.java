package org.example.garaxmljavinha.controller;

import org.example.garaxmljavinha.controller.domain.Usuario;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class GeracaoController {

    @GetMapping("/info")
    public String ping() {
        return "Sistema de pé";
    }

    @GetMapping("/gerar")
    public String gerar(){
        long start = System.currentTimeMillis();
        List<Usuario> usuarios = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 500000; i++) {
            Usuario usuario = new Usuario();
            usuario.setNome("Usuário " + i);
            usuario.setIdade(random.nextInt(120));
            usuario.setEmail("usuario" + i + "@gmail.com");
            usuario.setEndereco("Rua do usuario " + i + ", " + random.nextInt(5000));
            usuario.setTelefone(String.valueOf(random.nextInt(999999999)));
            usuario.setDataNascimento(String.valueOf(randomBirthday()));
            usuario.setSexo(i%2==0?"m":"f");
            usuarios.add(usuario);
        }

        // Salvando os usuários em arquivos XML
        for (int i = 0; i < usuarios.size(); i++) {
            Usuario usuario = usuarios.get(i);
            salvarArquivoXML(usuario, "arquivo " + i + ".xml");
        }

        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Tempo de execucao em segundos: " + elapsed/1000 + "s");

        return "Tempo de execucao em segundos " + elapsed/1000 + "s";
    }

    public static LocalDate randomBirthday() {
        return LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70))));
    }

    public static void salvarArquivoXML(Usuario usuario, String nomeArquivo) {
        try {
            // Criação do arquivo XML
            FileWriter writer = new FileWriter(nomeArquivo);
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            writer.write("<usuario>\n");
            writer.write("    <nome>" + usuario.getNome() + "</nome>\n");
            writer.write("    <idade>" + usuario.getIdade() + "</idade>\n");
            writer.write("    <email>" + usuario.getEmail() + "</email>\n");
            writer.write("    <endereco>" + usuario.getEndereco() + "</endereco>\n");
            writer.write("    <telefone>" + usuario.getTelefone() + "</telefone>\n");
            writer.write("    <dataNascimento>" + usuario.getDataNascimento() + "</dataNascimento>\n");
            writer.write("    <sexo>" + usuario.getSexo() + "</sexo>\n");
            // Outros atributos do usuário, se houver
            writer.write("</usuario>\n");
            writer.close();
            System.out.println("Arquivo XML " + nomeArquivo + " salvo com sucesso." + " no path:" + System.getProperty("user.dir"));
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo XML " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
