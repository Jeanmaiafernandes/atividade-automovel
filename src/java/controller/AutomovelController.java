package controller;

import dao.AutomovelDAO;
import model.Automovel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AutomovelController", urlPatterns = {"/automoveis"})
public class AutomovelController extends HttpServlet {

    private AutomovelDAO automovelDAO;

    @Override
    public void init() throws ServletException {
        this.automovelDAO = new AutomovelDAO();
        System.out.println("AutomovelController iniciado!");
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            action = "listar";
        }

        System.out.println(" Ação solicitada: " + action);

        try {
            switch (action) {
                case "cadastrar":
                    cadastrarAutomovel(request, response);
                    break;
                case "editar":
                    mostrarFormularioEdicao(request, response);
                    break;
                case "atualizar":
                    atualizarAutomovel(request, response);
                    break;
                case "excluir":
                    excluirAutomovel(request, response);
                    break;
                case "buscarId":
                    buscarPorId(request, response);
                    break;
                case "buscarMarca":
                    buscarPorMarca(request, response);
                    break;
                case "listar":
                default:
                    listarAutomoveis(request, response);
                    break;
            }
        } catch (Exception e) {
            System.err.println("❌ Erro no Controller: " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro interno: " + e.getMessage());
        }
    }

    // LISTAR TODOS OS AUTOMÓVEIS
    private void listarAutomoveis(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("📋 Listando automóveis...");
        List<Automovel> automoveis = automovelDAO.listarTodos();
        
        request.setAttribute("listaAutomoveis", automoveis);
        request.getRequestDispatcher("/html/funcionario.jsp").forward(request, response);
    }

    // ➕ CADASTRAR NOVO AUTOMÓVEL
    private void cadastrarAutomovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("➕ Cadastrando novo automóvel...");
        
        try {
            Automovel automovel = new Automovel();
            preencherAutomovelFromRequest(automovel, request);
            
            automovelDAO.salvar(automovel);
            System.out.println("✅ Automóvel cadastrado com sucesso!");
            
            response.sendRedirect("automoveis?action=listar&sucesso=Automóvel cadastrado com sucesso!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao cadastrar: " + e.getMessage());
            request.setAttribute("erro", "Erro ao cadastrar automóvel: " + e.getMessage());
            listarAutomoveis(request, response);
        }
    }

    // ✏️ MOSTRAR FORMULÁRIO DE EDIÇÃO
    private void mostrarFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Automovel automovel = automovelDAO.buscarPorId(id);
                
                if (automovel != null) {
                    request.setAttribute("automovel", automovel);
                    request.setAttribute("editando", true);
                    System.out.println("✏️ Carregando formulário de edição - ID: " + id);
                } else {
                    request.setAttribute("erro", "Automóvel não encontrado para edição");
                }
            } catch (NumberFormatException e) {
                request.setAttribute("erro", "ID inválido: " + idParam);
            }
        }
        
        listarAutomoveis(request, response);
    }

    // 💾 ATUALIZAR AUTOMÓVEL
    private void atualizarAutomovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("💾 Atualizando automóvel...");
        
        try {
            String idParam = request.getParameter("id");
            if (idParam == null) {
                throw new IllegalArgumentException("ID não informado para atualização");
            }
            
            int id = Integer.parseInt(idParam);
            Automovel automovel = automovelDAO.buscarPorId(id);
            
            if (automovel != null) {
                preencherAutomovelFromRequest(automovel, request);
                boolean atualizado = automovelDAO.atualizar(automovel);
                
                if (atualizado) {
                    response.sendRedirect("automoveis?action=listar&sucesso=Automóvel atualizado com sucesso!");
                } else {
                    response.sendRedirect("automoveis?action=listar&erro=Erro ao atualizar automóvel");
                }
            } else {
                response.sendRedirect("automoveis?action=listar&erro=Automóvel não encontrado");
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao atualizar: " + e.getMessage());
            response.sendRedirect("automoveis?action=listar&erro=Erro ao atualizar: " + e.getMessage());
        }
    }

    // 🗑️ EXCLUIR AUTOMÓVEL
    private void excluirAutomovel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("🗑️ Excluindo automóvel...");
        
        try {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                boolean excluido = automovelDAO.excluir(id);
                
                if (excluido) {
                    response.sendRedirect("automoveis?action=listar&sucesso=Automóvel excluído com sucesso!");
                } else {
                    response.sendRedirect("automoveis?action=listar&erro=Erro ao excluir automóvel");
                }
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erro ao excluir: " + e.getMessage());
            response.sendRedirect("automoveis?action=listar&erro=Erro ao excluir: " + e.getMessage());
        }
    }

    // 🔍 BUSCAR POR ID
    private void buscarPorId(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        System.out.println("🔍 Buscando automóvel por ID: " + idParam);
        
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(idParam);
                Automovel automovel = automovelDAO.buscarPorId(id);
                
                List<Automovel> lista = new ArrayList<>();
                if (automovel != null) {
                    lista.add(automovel);
                    request.setAttribute("automovelEncontrado", automovel);
                    request.setAttribute("mensagem", "Automóvel encontrado!");
                } else {
                    request.setAttribute("erro", "Nenhum automóvel encontrado com ID: " + id);
                }
                request.setAttribute("listaAutomoveis", lista);
                
            } catch (NumberFormatException e) {
                request.setAttribute("erro", "ID inválido: " + idParam);
            }
        } else {
            request.setAttribute("erro", "Digite um ID para pesquisar");
        }
        
        request.getRequestDispatcher("/html/funcionario.jsp").forward(request, response);
    }

    // 🔍 BUSCAR POR MARCA
    private void buscarPorMarca(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String marca = request.getParameter("marca");
        System.out.println("🔍 Buscando automóveis da marca: " + marca);
        
        if (marca != null && !marca.trim().isEmpty()) {
            List<Automovel> automoveis = automovelDAO.buscarPorMarca(marca);
            request.setAttribute("listaAutomoveis", automoveis);
            request.setAttribute("marcaBuscada", marca);
            
            if (automoveis.isEmpty()) {
                request.setAttribute("erro", "Nenhum automóvel encontrado da marca: " + marca);
            }
        } else {
            request.setAttribute("erro", "Digite uma marca para pesquisar");
        }
        
        request.getRequestDispatcher("/html/funcionario.jsp").forward(request, response);
    }

    // 🛠️ MÉTODO AUXILIAR - Preencher objeto Automovel a partir da request
    private void preencherAutomovelFromRequest(Automovel automovel, HttpServletRequest request) {
        automovel.setMarca(request.getParameter("marca"));
        automovel.setCor(request.getParameter("cor_externa"));
        automovel.setAno(Integer.parseInt(request.getParameter("ano")));
        automovel.setMotor(request.getParameter("motor"));
        automovel.setPreco(Double.parseDouble(request.getParameter("preco")));
        automovel.setCambio(request.getParameter("cambio"));
        automovel.setTracao(request.getParameter("tracao"));
        automovel.setCavalos(Integer.parseInt(request.getParameter("cavalos")));
        automovel.setCategoria(request.getParameter("categoria"));
        automovel.setCorInterna(request.getParameter("cor_interna"));
        automovel.setCombustivel(request.getParameter("combustivel"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Controller para gerenciar automóveis";
    }
}