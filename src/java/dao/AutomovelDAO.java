package dao;

import dao.conexaoDAO;
import model.Automovel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jeanm
 */
public class AutomovelDAO {
    
    // SALVAR - Insere novo automóvel no banco
    public void salvar(Automovel automovel) {
        String sql = "INSERT INTO automoveis (marca, cor, ano, motor, preco, cambio, tracao, cavalos, categoria, cor_interna, combustivel) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        
        PreparedStatement ps = null;
        
        try {
            // 1. Obter conexão
            conn = conexaoDAO.conectaDB();
            
            // VERIFICAÇÃO DE SEGURANÇA
            if (conn == null) {
                System.err.println("💥 ERRO: Conexão retornou NULL!");
                return;
            }
            
            // 2. Preparar statement
            ps = conn.prepareStatement(sql);
            
            // 3. Setar parâmetros
            ps.setString(1, automovel.getMarca());
            ps.setString(2, automovel.getCor());
            ps.setInt(3, automovel.getAno());
            ps.setString(4, automovel.getMotor());
            ps.setDouble(5, automovel.getPreco());
            ps.setString(6, automovel.getCambio());
            ps.setString(7, automovel.getTracao());
            ps.setInt(8, automovel.getCavalos());
            ps.setString(9, automovel.getCategoria());
            ps.setString(10, automovel.getCorInterna());
            ps.setString(11, automovel.getCombustivel());
            
            // 4. EXECUTAR
            int linhasAfetadas = ps.executeUpdate();
            System.out.println("✅ Automóvel salvo! Linhas afetadas: " + linhasAfetadas);
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao salvar automóvel: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Fechar recursos
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        }
    
    // BUSCAR POR ID - Retorna um único automóvel
    public Automovel buscarPorId(int id) {
        String sql = "SELECT * FROM automoveis WHERE id = ?";
        
        try (Connection conn = conexaoDAO.conectaDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("✅ Automóvel encontrado - ID: " + id);
                    return mapResultSetToAutomovel(rs);
                } else {
                    System.out.println("⚠️  Automóvel não encontrado - ID: " + id);
                    return null;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar automóvel por ID: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }    
    
    // BUSCAR POR MARCA - Retorna lista de automóveis da mesma marca
    public List<Automovel> buscarPorMarca(String marca) {
        String sql = "SELECT * FROM automoveis WHERE marca LIKE ?";
        List<Automovel> lista = new ArrayList<>();
        
        try (Connection conn = conexaoDAO.conectaDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + marca + "%"); // Busca parcial
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapResultSetToAutomovel(rs));
                }
            }
            
            System.out.println("✅ Busca por marca '" + marca + "' - " + lista.size() + " resultados");
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao buscar automóveis por marca: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }

    // LISTAR TODOS - Retorna todos os automóveis
    public List<Automovel> listarTodos() {
        String sql = "SELECT * FROM automoveis ORDER BY id DESC";
        List<Automovel> lista = new ArrayList<>();

        try (Connection conn = conexaoDAO.conectaDB();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapResultSetToAutomovel(rs));
            }
            
            System.out.println("✅ Listagem completa - " + lista.size() + " automóveis");
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao listar automóveis: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lista;
    }   
    
    // ATUALIZAR AUTOMÓVEL
    public boolean atualizar(Automovel automovel) {
        String sql = "UPDATE automoveis SET marca=?, cor=?, ano=?, motor=?, preco=?, cambio=?, tracao=?, cavalos=?, categoria=?, cor_interna=?, combustivel=? WHERE id=?";
        
        try (Connection conn = conexaoDAO.conectaDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, automovel.getMarca());
            stmt.setString(2, automovel.getCor());
            stmt.setInt(3, automovel.getAno());
            stmt.setString(4, automovel.getMotor());
            stmt.setDouble(5, automovel.getPreco());
            stmt.setString(6, automovel.getCambio());
            stmt.setString(7, automovel.getTracao());
            stmt.setInt(8, automovel.getCavalos());
            stmt.setString(9, automovel.getCategoria());
            stmt.setString(10, automovel.getCorInterna());
            stmt.setString(11, automovel.getCombustivel());
            stmt.setInt(12, automovel.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Automóvel atualizado - ID: " + automovel.getId());
                return true;
            } else {
                System.out.println("⚠️ Automóvel não encontrado para atualização - ID: " + automovel.getId());
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao atualizar automóvel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //️ EXCLUIR AUTOMÓVEL
    public boolean excluir(int id) {
        String sql = "DELETE FROM automoveis WHERE id = ?";
        
        try (Connection conn = conexaoDAO.conectaDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            
            if (linhasAfetadas > 0) {
                System.out.println("✅ Automóvel excluído - ID: " + id);
                return true;
            } else {
                System.out.println("⚠️ Automóvel não encontrado para exclusão - ID: " + id);
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Erro ao excluir automóvel: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // MÉTODO AUXILIAR PRIVADO: Centraliza o mapeamento ResultSet → Automovel
    private Automovel mapResultSetToAutomovel(ResultSet rs) throws SQLException{
        Automovel automovel = new Automovel();

       // Mapeia TODOS os campos do banco para o objeto Automovel
        automovel.setId(rs.getInt("id"));
        automovel.setMarca(rs.getString("marca"));
        automovel.setCor(rs.getString("cor"));
        automovel.setAno(rs.getInt("ano"));
        automovel.setMotor(rs.getString("motor"));
        automovel.setPreco(rs.getDouble("preco"));
        automovel.setCambio(rs.getString("cambio"));
        automovel.setTracao(rs.getString("tracao"));
        automovel.setCavalos(rs.getInt("cavalos"));
        automovel.setCategoria(rs.getString("categoria"));
        automovel.setCorInterna(rs.getString("cor_interna"));
        automovel.setCombustivel(rs.getString("combustivel"));    
    
        return automovel;
    }
}

