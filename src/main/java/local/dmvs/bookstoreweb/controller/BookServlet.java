package local.dmvs.bookstoreweb.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import local.dmvs.bookstoreweb.model.bean.Book;
import local.dmvs.bookstoreweb.model.dao.BookDAO;

/**
 *
 * @author devsys-a
 */
public class BookServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // define a resposta como sendo HTML.
        response.setContentType("text/html;charset=UTF-8");
        
        //captura o caminho(path) que foi solicitado.
        String action = request.getPathInfo();
        // Gera um Log para acompanhamento do desenvolvedor.
        Logger.getLogger(BookServlet.class.getName()).log(Level.INFO, "Path solicitado: {0}", action);
        
        try {
           switch (action){
               //se for chamado “/new” carrega o método showNewBookForm.
               case "/new":
                   showNewBookForm(request, response);
                   break;
                   
               case "/edit":
                   showEditBookForm(request, response);
                   break;
                   
               case "/update":
                   updateBookAction(request, response);
                   break;
                   
               case "/delete":
                   deleteBookAction(request, response);
                   break;
                
               //se for chamado /insert” carrega o método insertBookAction    
               case "/insert":
                   insertBookAction(request, response);
                   break;
                   
               case "/list":
                   default:
                       listBook(request, response);
                       break;
           }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    /**
     * Exibe a lista completa de livros
     * @param request
     * @param response
     * @throws SQLException
     * @throws IOException
     * @throws ServletException 
     */
    
    private void listBook(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        //Instancia o bjeto bookDAO e solicita a lista de registros.
        BookDAO bookDAO = new BookDAO(); 
        List<Book> listaBook = bookDAO.getResults();
        
        //Gera log de INFO retornando a quantidade de registros encontrados.
        Logger.getLogger(BookDAO.class.getName()).log(Level.INFO,
                "Total de registros: {0}", listaBook.size());
        
        //Define na “request” um atributo chamado “listaBook” com o arraylist dos
        //resultados da linha 120. A request são os dados complementares que foram enviados na requisição.
        request.setAttribute("listaBook", listaBook);
        
        //cria um dispatcher (expedidor) apontando para a BookList.jsp.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/BookList.jsp");
        
        //Encaminha a requisição para o BookList.jsp enviando as requests e responses. As
        //responses são os dados que o servidor irá encaminhar aos clientes.
        dispatcher.forward(request, response);
        
    }
    
    private void showNewBookForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        //Define o expedidor (dispatcher) para a página BookForm.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/BookForm.jsp");
        //Aciona o dispatcher.
        dispatcher.forward(request, response);
        
    }
    
    private void insertBookAction(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        
        BookDAO bookDAO =new BookDAO();
        Book novoBook = new Book();
        //perceba a captura do conteúdo do campo do formulário
        novoBook.setTitulo( request.getParameter("formTitulo") ); 
        novoBook.setAutor( request.getParameter("formAutor") );
        //O form sempre manda os dados em String. Fique atento!!!
        novoBook.setPreco( Double.parseDouble(request.getParameter("formPreco")) ); 
        
        bookDAO.create(novoBook);
        response.sendRedirect("list");
        
    }
    
    private void showEditBookForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        // captura da URL – método GET – a variável “id”.
        int id = Integer.parseInt(request.getParameter("id"));
        BookDAO bookDAO = new BookDAO();
        //Carrega do banco de dados o livro a ser alterado a partir do ID.
        Book atualizaBook = bookDAO.getResultById(id);
        //Define o expedidor (dispatcher) para a página BookForm.jsp.
        RequestDispatcher dispatcher = request.getRequestDispatcher("/BookForm.jsp");
        //“empacota” um objeto para ser enviado à página do dispatcher. O dispatcher
        //consegue o objeto atualizaBook chamando o identificador “book”.
        request.setAttribute("book", atualizaBook);
        //Aciona o dispatcher.
        dispatcher.forward(request, response);
        
    }
    
    private void updateBookAction(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        BookDAO bookDAO = new BookDAO();
        Book bookAtualizado = new Book();
        
        //captura do BookForm.jsp os dados do formulário e monta o objeto
        //bookAtualizado para enviar ao bookDAO.
        
        bookAtualizado.setId( Integer.parseInt(request.getParameter("formId")) );
        bookAtualizado.setTitulo( request.getParameter("formTitulo") );
        bookAtualizado.setAutor( request.getParameter("formAutor") );
        bookAtualizado.setPreco(Double.parseDouble(request.getParameter("formPreco")) );
        
        bookDAO.update(bookAtualizado);
        response.sendRedirect("list");
        
    }
    
    private void deleteBookAction(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        
        //carrega da URL a variável ID (linha 237) e aciona o método
        //delete
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        BookDAO bookDAO = new BookDAO();
        bookDAO.delete(id);
        response.sendRedirect("list");
        
    }

 }
