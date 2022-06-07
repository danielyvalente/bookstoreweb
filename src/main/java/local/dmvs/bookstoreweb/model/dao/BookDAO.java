/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package local.dmvs.bookstoreweb.model.dao;

import connection.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import local.dmvs.bookstoreweb.model.bean.Book;

/**
 *
 * @author devsys-a
 */
public class BookDAO {
    
    private static final String SQL_INSERT = "INSERT INTO book (titulo, "
            + "autor, preco) "
            + "VALUES (?, ?, ?)";
    
    private static final String SQL_SELECT_ALL = "SELECT * FROM book";
    private static final String SQL_SELECT_ID = "SELECT * FROM book "
            + "WHERE id = ?";
    
    
    private static final String SQL_UPDATE = "UPDATE book SET titulo = ?,"
            + "autor = ?, preco = ? WHERE id = ?";
    
    private static final String SQL_DELETE = "DELETE FROM book WHERE id = ?";
    
    /**
     * Insere um usuario na base de dados Produto
     * @param b
     */
    public void create(Book b) {
        // Inicia uma conexão com a base de dados utilizando nossa classe MySQLConnection.
        Connection conn = MySQLConnection.getConnection(); 
        // declara o PreparedStatement que irá receber a instrução a ser executada no banco
        PreparedStatement stmt = null;  
        
        try { 
            //Prepara a instrução dada na constante SQL_INSERT. 
            stmt = conn.prepareStatement(SQL_INSERT); 
            // substitui os “?” pelos atributos do produto recebido no método.
            stmt.setString(1, b.getTitulo());
            stmt.setString(2, b.getAutor());
            stmt.setDouble(3, b.getPreco());
            
            // Executa a query  e retorna um inteiro. O inteiro representa a quantidade de registros incluídos.
            int auxRetorno = stmt.executeUpdate();
            
            // gera um log INFO para informação.
            Logger.getLogger(BookDAO.class.getName()).log(Level.INFO, null, 
                    "Inclusao: " + auxRetorno);
          
        } catch (SQLException sQLException) {
            // gera um log SEVERE caso tenha algum erro.
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            //Encerra a conexão com o banco e o statement
            MySQLConnection.closeConnection(conn, stmt);
        }
        
    }
    
    /**
     * Retorna todos os registros da tabela produto
     * @return 
     */
    public List<Book> getResults(){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Book b = null;
        List<Book> listaBook = null;
        
        try {
            //Prepara a string de SELECT e executa a query.
            stmt = conn.prepareStatement(SQL_SELECT_ALL);
            rs = stmt.executeQuery(); //Executa a query e retorna um ResultSet.
            
            //Carrega os dados do ResultSet rs, converte em Produto e
            // adiciona na lista de retorno.
            listaBook = new ArrayList<>();
            
            // Varre o ResultSet (rs) utilizando o método rs.next().
            while (rs.next()){
                b = new Book();
                // captura os campos do registro posicionado pelo rs.next(), 
                // monta um objeto produto temporário e o inclui na lista.
                b.setId(rs.getInt("id"));
                b.setTitulo(rs.getString("titulo"));
                b.setAutor(rs.getString("autor"));
                b.setPreco(rs.getDouble("preco"));
                
                listaBook.add(b);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaBook;
    }
    
    /**
     * Retorna um produto da tabela produto.
     * @param id
     * @return 
     */
    //A diferença deste código para o anterior é que ele retorna apenas um produto. 
    public Book getResultById(int id){
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Book b = null;
        
        try{
            stmt = conn.prepareStatement(SQL_SELECT_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                b = new Book();
                b.setId(rs.getInt("id"));
                b.setTitulo(rs.getString("titulo"));
                b.setAutor(rs.getString("autor"));
                b.setPreco(rs.getDouble("preco"));
                
            }
            
        } catch (SQLException sQLException) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt, rs);
        }
        
        return b;
    }
    
    /**
     * Atualiza um registro n atabela produto.
     * @param b Produto a ser atualizado.
     */
    // Recebe como parâmetro um produto para ser alterado. Usa como referência o atributo ID.
    // Segue o mesmo padrão do método create.
    public void update(Book b) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, b.getAutor());
            stmt.setString(2, b.getTitulo());
            stmt.setDouble(3, b.getPreco());
            stmt.setInt(4, b.getId());
            
            
            //Executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(BookDAO.class.getName()).log(Level.INFO, null,
                    "Update: " + auxRetorno);
            
        } catch (SQLException sQLException) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
        } finally {
            // Encerra a conexão com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
        
    }
    
    /**
     * Exclui um produto com base do ID fornecido.
     * @param id  IDentificação do produto.
     */
    public void delete(int id) {
        Connection conn = MySQLConnection.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, id);
            
            // Executa a query
            int auxRetorno = stmt.executeUpdate();
            
            Logger.getLogger(BookDAO.class.getName()).log(INFO, null,
                    "Delete: " + auxRetorno);
            
            } catch (SQLException sQLException) {
                Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null,
                    sQLException);
            
            } finally {
            // Encerra a conexãp com o banco e o statement.
            MySQLConnection.closeConnection(conn, stmt);
        }
    }
}
