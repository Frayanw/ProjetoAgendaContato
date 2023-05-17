
package modelos;

import java.util.ArrayList;

public interface IContatoCRUD {
    public void incluir(Contato objContato) throws Exception;
    public Contato buscar(String CPF) throws Exception;
    public void alterar(Contato objContato) throws Exception;
    public void excluir(String cpf) throws Exception;
   public ArrayList<Contato> obterListagem() throws Exception ;
}
