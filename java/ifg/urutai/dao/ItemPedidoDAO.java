package ifg.urutai.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ifg.urutai.connection.ConnectionFactory;
import ifg.urutai.model.ItemPedido;
import ifg.urutai.model.Pedido;
import ifg.urutai.model.Produto;

public class ItemPedidoDAO {
	
	Connection connection;
    public ItemPedidoDAO(){
        this.connection = new ConnectionFactory().getConnection();
    }
    
    public void add(ItemPedido itemPedido){
        String sql = "INSERT INTO Item_Pedido (quantidade, id_pedido, id_produto) VALUES (?, ?, ?)";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
        statement.setInt(1,itemPedido.getQuantidade());
        statement.setInt(2, itemPedido.getPedido().getId());
        statement.setInt(3, itemPedido.getProduto().getId());
        statement.execute();
        }catch (SQLException e){
            Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public ItemPedido findbyId(int id){
        String sql = "SELECT * FROM Item_Pedido WHERE id_item_pedido = ?";
        ItemPedido itemPedido = new ItemPedido();
        
        PedidoDAO pedidoDAO = new PedidoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,id);

            ResultSet rs = statement.executeQuery();
            rs.next();
            itemPedido = new ItemPedido();
            itemPedido.setIdItemPedido(rs.getInt("id_item_pedido"));
            itemPedido.setQuantidade(rs.getInt("quantidade"));
            int id_pedido = rs.getInt("id_pedido");
            int id_produto = rs.getInt("id_produto");
            
            Pedido pedido = pedidoDAO.findbyIdPedido(id_pedido);   
            itemPedido.setPedido(pedido);
            
            Produto produto = produtoDAO.findbyIdProduto(id_produto);
            itemPedido.setProduto(produto);
        }catch (SQLException e){
            Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
        }

        return itemPedido;
    }
    
    public void updateQuantidadeById(ItemPedido itemPedido){
        String sql = "UPDATE Item_Pedido SET quantidade = ? WHERE id_item_pedido = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, itemPedido.getQuantidade());
            statement.setInt(2, itemPedido.getIdItemPedido());
            statement.execute();
        }catch (SQLException e){
            Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public void deleteById(ItemPedido itemPedido){
        String sql = "DELETE FROM Item_Pedido WHERE id_item_pedido = ?";

        try(PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1,itemPedido.getIdItemPedido());
            statement.execute();
        }catch (SQLException e){
            Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    
    public List<ItemPedido> findAllByIdPedido(int id_pedido){
        String sql = "SELECT * FROM Item_Pedido WHERE id_pedido = ?";

        List<ItemPedido> list = new ArrayList<>();
        
        PedidoDAO pedidoDAO = new PedidoDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        try(PreparedStatement statement = connection.prepareStatement(sql)){
        	statement.setInt(1, id_pedido);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            ItemPedido itemPedido = null;

            while (rs.next()){
                itemPedido = new ItemPedido();
                itemPedido.setIdItemPedido(id_pedido);
                itemPedido.setQuantidade(rs.getInt("quantidade"));
                int id_produto = rs.getInt("id_produto");
                
                Pedido pedido = pedidoDAO.findbyIdPedido(id_pedido);
                Produto produto = produtoDAO.findbyIdProduto(id_produto);
                
                itemPedido.setPedido(pedido);
                itemPedido.setProduto(produto);
                list.add(itemPedido);
            }
            
        }catch (SQLException e){
            Logger.getLogger(ItemPedidoDAO.class.getName()).log(Level.SEVERE,null,e);
        }
        
        return list;
    }

}