package controle;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.HashSet;
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
    public boolean validarNumero(String numero)throws Exception {
        // Validar Número
        String regex = "^[0-9]+$";
        if (numero.length() != 9) {
            throw new Exception("Número deve ter 9 dígitos");
        }
        return numero.matches(regex);
    }
    //Validar DDD do Brasil
 private static final HashSet<String> dddsValidos = new HashSet<String>() {
        {
            add("11");
            add("12");
            add("13");
            add("14");
            add("15");
            add("16");
            add("17");
            add("18");
            add("19");
            add("21");
            add("22");
            add("24");
            add("27");
            add("28");
            add("31");
            add("32");
            add("33");
            add("34");
            add("35");
            add("37");
            add("38");
            add("41");
            add("42");
            add("43");
            add("44");
            add("45");
            add("46");
            add("47");
            add("48");
            add("49");
            add("51");
            add("53");
            add("54");
            add("55");
            add("61");
            add("62");
            add("63");
            add("64");
            add("65");
            add("66");
            add("67");
            add("68");
            add("69");
            add("71");
            add("73");
            add("74");
            add("75");
            add("77");
            add("79");
            add("81");
            add("82");
            add("83");
            add("84");
            add("85");
            add("86");
            add("87");
            add("88");
            add("89");
            add("91");
            add("92");
            add("93");
            add("94");
            add("95");
            add("96");
            add("97");
            add("98");
            add("99");
        }
    };

    public static boolean validarDDD(String ddd) {
        return dddsValidos.contains(ddd);
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

    
    //Método validar CPF
    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // Remove caracteres não numéricos

        if (cpf.length() != 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        boolean todosDigitosIguais = true;
        for (int i = 1; i < 11; i++) {
            if (cpf.charAt(i) != cpf.charAt(0)) {
                todosDigitosIguais = false;
                break;
            }
        }
        if (todosDigitosIguais) {
            return false;
        }

        // Verifica o primeiro dígito verificador
        int soma = 0;
        for (int i = 0; i < 9; i++) {
            int digito = cpf.charAt(i) - '0';
            soma += digito * (10 - i);
        }
        int resto = soma % 11;
        int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;
        if (digitoVerificador1 != cpf.charAt(9) - '0') {
            return false;
        }

        // Verifica o segundo dígito verificador
        soma = 0;
        for (int i = 0; i < 10; i++) {
            int digito = cpf.charAt(i) - '0';
            soma += digito * (11 - i);
        }
        resto = soma % 11;
        int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;
        if (digitoVerificador2 != cpf.charAt(10) - '0') {
            return false;
        }

        return true;
    }
    
    
    @Override
    public void incluir(Contato objContato) throws Exception {
        //Regras de Negócios de incluir
        //Não pode haver CPF duplicado na agenda
        //Não pode permitir a inclusão de ninguém sem preencher todos os campos(cpf,nome,email)
        //Validar CPF para não colocar letras
        //Validar email para ter @ e .com

        //Validando CPF
        boolean validar = validarCPF(objContato.getCpf());
        if (validar) {
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
        //Validar DDD
        boolean valido = validarDDD(objContato.getTelefone().getDDD());
        if (valido){
            
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
