/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistência;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import modelos.*;

public class ContatoDAO implements IContatoCRUD {

    //Atributos
    private String nomeDoArquivoNoDisco = null;
    Telefone telefone = new Telefone();

    public ContatoDAO() {
        nomeDoArquivoNoDisco = "./src/bancodedados/contatosBD.txt";
    }

    @Override
    public void incluir(Contato objContato) throws Exception {
        try {
            //cria o arquivo
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco, true);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            //Escreve no arquivo
            bw.write(objContato.toString());
            //fecha o arquivo
            bw.close();
        } catch (Exception erro) {
            String msg = "Metodo Incluir Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public Contato buscar(String CPF) throws Exception {
        try {
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");

                String cpfAux = vetorStr[0];
                String nomeCompleto = vetorStr[1];
                String email = vetorStr[2];
                String DDI = vetorStr[3];
                String DDD = vetorStr[4];
                String numero = vetorStr[5];
                if (CPF.equalsIgnoreCase(cpfAux)) {
                    telefone.setDDI(DDI);
                    telefone.setDDD(DDD);
                    telefone.setNumero(numero);
                    Contato objetoContato = new Contato(cpfAux, nomeCompleto, email, telefone);
                    br.close();
                    return objetoContato;
                }
            }
            br.close();
            return null;
        } catch (Exception erro) {
            throw erro;
        }
    }

    @Override
    public ArrayList<Contato> obterListagem() throws Exception {
        try {
            ArrayList<Contato> listaDeContatos = new ArrayList<Contato>();
            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                String cpf = vetorStr[0];
                String nomeCompleto = vetorStr[1];
                String email = vetorStr[2];
                String DDI = vetorStr[3];
                String DDD = vetorStr[4];
                String numero = vetorStr[5];
                telefone = new Telefone();
                telefone.setDDI(DDI);
                telefone.setDDD(DDD);
                telefone.setNumero(numero);
                Contato objetoContato = new Contato(cpf, nomeCompleto, email, telefone);
                listaDeContatos.add(objetoContato);
            }
            br.close();
            return listaDeContatos;
        } catch (Exception erro) {
            throw erro;
        }

    }

    @Override
    public void alterar(Contato objContato) throws Exception {
        try {
            excluir(objContato.getCpf());
            incluir(objContato);
        } catch (Exception erro) {
            String msg = "Metodo Alterar Contato - " + erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public void excluir(String cpf) throws Exception {
        ArrayList<Contato> agenda = new ArrayList();
        Contato contato = new Contato();
        try {

            FileReader fr = new FileReader(nomeDoArquivoNoDisco);
            BufferedReader br = new BufferedReader(fr);
            String linha ;
            String cpfAux ;
            String nomeCompleto;
            String email ;
            String DDI;
            String DDD;
            String numero;

            while ((linha = br.readLine()) != null) {
                String vetorStr[] = linha.split(";");
                cpfAux = vetorStr[0];
                nomeCompleto = vetorStr[1];
                email = vetorStr[2];
                DDI = vetorStr[3];
                DDD = vetorStr[4];
                numero = vetorStr[5];
                telefone = new Telefone();
                telefone.setDDI(DDI);
                telefone.setDDD(DDD);
                telefone.setNumero(numero);
                contato = new Contato(cpfAux, nomeCompleto, email, telefone);
                agenda.add(contato);
            }
            //Exclui o que tem no arquivo e reescreve
            FileWriter fw = new FileWriter(nomeDoArquivoNoDisco);
            //Criar o buffer do arquivo
            BufferedWriter bw = new BufferedWriter(fw);
            
            for (int i = 0; i < 3; i++) {
                if (!cpf.equals(agenda.get(i).getCpf())) {
                    contato = new Contato();
                    telefone = new Telefone();
                    contato.setCpf(agenda.get(i).getCpf());
                    contato.setNomeCompleto(agenda.get(i).getNomeCompleto());
                    contato.setEmail(agenda.get(i).getEmail());
                    telefone.setDDI(agenda.get(i).getTelefone().getDDI());
                    telefone.setDDD(agenda.get(i).getTelefone().getDDD());
                    telefone.setNumero(agenda.get(i).getTelefone().getNumero());                    
                    contato.setTelefone(telefone);
                    
                    bw.write(contato.toString());
                }
            }
            
            bw.close();
        } catch (Exception erro) {

            String msg = "Metodo Alterar Contato - " + erro.getMessage();
            //throw new Exception(msg);

        }
    }
}
