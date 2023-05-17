package controle;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import modelos.Contato;
import modelos.Telefone;
import modelos.IContatoCRUD;
import persistência.ContatoDAO;

public class ContatoControle implements IContatoCRUD {

    //Atributo
    IContatoCRUD contatoPersistencia = null;
    Telefone telefone = new Telefone();
    public ContatoControle() {
        contatoPersistencia = new ContatoDAO();
    }

    public boolean validarDDITelefone(String telefone)throws Exception {
        // Expressão regular que verifica se a string contém apenas números e tem entre 1 e 3 caracteres
        String regex = "^[0-9]{2}$";
        // Extrai os primeiros caracteres da string correspondentes ao DDI
        String ddi = telefone.substring(0, Math.min(telefone.length(), 3));
        
        
        
        return ddi.matches(regex);
    }

    public boolean validarDDDTelefone(String telefone) {
        // Expressão regular que verifica se a string contém apenas números e tem 2 caracteres
        String regex = "^[0-9]{2}$";
    // Extrai os primeiros caracteres da string correspondentes ao DDI
        String ddd = telefone.substring(0, Math.min(telefone.length(), 3));
    return ddd.matches(regex);
    }

    public boolean validarNumero(String numero)throws Exception {
        // Validar Número
        String regex = "^[0-9]+$";
        if (numero.length() != 9) {
            throw new Exception("Número deve ter 9 dígitos");
        }
        return numero.matches(regex);
    }

    //Método Validar email
    private static final String EMAIL_REGEX
            = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,3})+$";

    public static boolean validarEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Método Validar Nome
    private static final String NOME_REGEX = "^[a-zA-Z\\s]+$";

    public static boolean validarNome(String nomeCompleto) {
        Pattern pattern = Pattern.compile(NOME_REGEX);
        Matcher matcher = pattern.matcher(nomeCompleto);
        return matcher.matches();
    }

    @Override
    public void incluir(Contato objContato) throws Exception {
        //Regras de Negócios de incluir
        //Não pode haver CPF duplicado na agenda
        //Não pode permitir a inclusão de ninguém sem preencher todos os campos(cpf,nome,email)
        //Validar CPF para não colocar letras
        //Validar email para ter @ e .com

        //Validando CPF
        if (objContato.getCpf().matches("\\d{11}")) {
        } else {
            throw new Exception("CPF Inválido");
        }
        if (contatoPersistencia.buscar(objContato.getCpf()) != null) {
            throw new Exception("Ja existe este CPF em sua agenda!!");
        }

        //Validar Nome
        if (validarNome(objContato.getNomeCompleto())) {
        } else {
            throw new Exception("Nome Inválido");
        }

        //Validar Email como deve escrever no texto String email = "usuario@email.com";
        if (validarEmail(objContato.getEmail())) {
        } else {
            throw new Exception("Email Inválido");
        }

        //Validar DDI
        if (validarDDITelefone(objContato.getTelefone().getDDI())) {
        } else {
            throw new Exception("DDI inválido");
        }

        //Validar DDD
        if (validarDDDTelefone(objContato.getTelefone().getDDD())) {
        } else {
            throw new Exception("DDD inválido");
        }

        //Validar numero
        if (validarNumero(objContato.getTelefone().getNumero())) {
        } else {
            throw new Exception("Número inválido");
        }
        contatoPersistencia.incluir(objContato);
    }

    @Override
    public Contato buscar(String cpf) throws Exception {
        try {
            //instancia com  referencia a contato e verifica passando parametro CPF

            if (cpf.equals("")) {
                throw new Exception("Digite um cpf para buscar");
            } else {

                ContatoDAO contatoDAO = new ContatoDAO();
                Contato contato = new Contato();
                contato = contatoDAO.buscar(cpf);

                //verifica se retorno do contato esta null, caso esteja null -> retorna contato inexistente;
                if (contato == null) {
                    throw new Exception("Contato inexistente!");
                }
                //se passar pela condição ele retorna o contato;
                return contato;
            }
        } catch (Exception erro) {
            String msg = erro.getMessage();
            throw new Exception(msg);
        }
    }

    @Override
    public ArrayList<Contato> obterListagem() throws Exception {
        //retorna arraylist de contatos
        return contatoPersistencia.obterListagem();
    }

    @Override
    public void alterar(Contato objContato) throws Exception {

        if (objContato.getCpf().equalsIgnoreCase("")) {
            throw new Exception("Digite algo no campo do CPF");
        } else {
            ContatoDAO contatoDAO = new ContatoDAO();
            Contato contato = new Contato();
            contato = contatoDAO.buscar(objContato.getCpf());
            if (contato == null) {
                throw new Exception("Contato Inexistente");
            } else {
                //Validar Nome
                if (validarNome(objContato.getNomeCompleto())) {
                } else {
                    throw new Exception("Nome Inválido");
                }

                //Validar Email como deve escrever no texto String email = "usuario@email.com";
                if (validarEmail(objContato.getEmail())) {
                } else {
                    throw new Exception("Email Inválido");
                    
                    //Validar DDI
                }
                if (validarDDITelefone(objContato.getTelefone().getDDI())) {
                } else {
                    throw new Exception("DDI inválido");
                }

                //Validar DDD
                if (validarDDDTelefone(objContato.getTelefone().getDDD())) {
                } else {
                    throw new Exception("DDD inválido");
                }

                //Validar Numero
                if (validarNumero(objContato.getTelefone().getNumero())) {
                } else {
                    throw new Exception("Número inválido");
                }
            }
        }
        contatoPersistencia.alterar(objContato);
    }

    @Override
    public void excluir(String cpf) throws Exception {
        if (cpf.equalsIgnoreCase("")) {
            throw new Exception("Digite algo no campo do CPF");
        } else {
            ContatoDAO contatoDAO = new ContatoDAO();
            Contato contato = new Contato();
            contato = contatoDAO.buscar(cpf);
            if (contato == null) {
                throw new Exception("Contato Inexistente");
            }

        }
        contatoPersistencia.excluir(cpf);
    }
}
