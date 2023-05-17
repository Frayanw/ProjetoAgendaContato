/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Frayan
 */
public class Contato {
    
    //Atributos
    
    private String cpf = "";
    private String nomeCompleto = "";
    private String email = "";    
    private Telefone telefone = null;
    
    //Construtores
    
    public Contato(){
          cpf = "";
          nomeCompleto = "";  
          email = "";
          telefone = null;
}
    public Contato(String cpf , String nome, String email, Telefone telefone){
        this.cpf = cpf;
        nomeCompleto = nome;
        this.email = email;
        this.telefone = telefone;
    }
    
    //Métodos

    @Override
    public String toString() {
        return cpf + ";" + nomeCompleto + ";" + email + ";" + telefone.toString();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
}
