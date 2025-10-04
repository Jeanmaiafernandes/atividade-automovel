package dao;

import connection.ConnectionFactory;
import model.Automovel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jeanm
 */
public class AutomovelDAO {
    
            // SALVAR - Insere novo automóvel no banco
public void salvar(Automovel automovel) {
    String sql = "INSERT INTO automovel (marca, cor, ano, motor, preco, cambio, tracao, cavalos, categoria, cor_interna, combustivel) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Usa PreparedStatement para segurança contra SQL Injection
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

            stmt.executeUpdate();
            System.out.println("Automóvel salvo com sucesso!");
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar automóvel: " + e.getMessage());
        }
    }

        /*                  
           * Busca automóvel por ID - diferenças do exemplo do professor:
           * - Usamos PreparedStatement para segurança contra SQL Injection
           * - Manipulação de tipos mais simplificada
        */
       public Automovel buscarPorId(int id) {
 
        String sql = "SELECT * FROM automovel WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAutomovel(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar automóvel por ID: " + e.getMessage());
        }
        return null; // não encontrado
    }

    // Buscar por Marca (READ - vários registros)
    public List<Automovel> buscarPorMarca(String marca) { 
        String sql = "SELECT * FROM automovel WHERE marca = ?";
        List<Automovel> lista = new ArrayList<>();
        
        try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, marca);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Reusa o mesmo método mapeador - SEM REDUNDÂNCIA!
                lista.add(mapResultSetToAutomovel(rs));
            }
         }
        
        } catch (SQLException e) {
            System.err.println("Erro ao buscar automóveis por marca: " + e.getMessage());
        }
        return lista;
    }

    public List<Automovel> listarTodos() {
        String sql = "SELECT * FROM automovel";
        List<Automovel> lista = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Reusa o mesmo método mapeador - SEM REDUNDÂNCIA!
                lista.add(mapResultSetToAutomovel(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar automóveis: " + e.getMessage());
        }
        return lista;
    }
    
    // MÉTODO AUXILIAR PRIVADO: Centraliza o mapeamento ResultSet → Automovel
    // Elimina redundância nos métodos de consulta
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

