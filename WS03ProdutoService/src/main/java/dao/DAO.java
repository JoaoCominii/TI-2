package dao;

import java.sql.*;

import com.ti2cc.Carro;

public class DAO {
    protected Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "carro";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
        String username = "ti2cc";
        String password = "ti@cc";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;
        try {
            conexao.close();
            status = true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return status;
    }

    public boolean inserirCarro(Carro carro) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO carro (marca, modelo, ano) "
                           + "VALUES ('" + carro.getMarca() + "', '" + carro.getModelo() + "', " + carro.getAno() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean atualizarCarro(Carro carro) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE carro SET marca = '" + carro.getMarca() + "', modelo = '" + carro.getModelo() + "', ano = " + carro.getAno()
                       + " WHERE id = " + carro.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }

    public boolean excluirCarro(int id) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM carro WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }

    public Carro[] getCarros() {
        Carro[] carros = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM carro");        
            if(rs.next()){
                rs.last();
                carros = new Carro[rs.getRow()];
                rs.beforeFirst();

                for(int i = 0; rs.next(); i++) {
                    carros[i] = new Carro(rs.getInt("id"), rs.getString("marca"), 
                                          rs.getString("modelo"), rs.getInt("ano"));
                }
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return carros;
    }
}
