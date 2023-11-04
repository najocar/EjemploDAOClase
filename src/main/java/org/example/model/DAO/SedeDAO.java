package org.example.model.DAO;

import org.example.model.Connection.MariaDBConnection;
import org.example.model.Entity.Complejo;
import org.example.model.Entity.Sede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SedeDAO extends Sede {
    public final static String INSERT = "INSERT INTO Sede (nombre,presupuesto) VALUES (?,?)";
    private final static String UPDATE = "UPDATE Sede SET nombre=?,presupuesto=? where id =?";
    private final static String DELETE = "DELETE FROM Sede WHERE id = ?";
    private final static String SELECTBYID = "SELECT id, nombre, presupuesto FROM Sede WHERE id =?";
    private final static String SELECTALL = "SELECT id, nombre, presupuesto FROM Sede";

    public SedeDAO(int id, String nombre, int presupesto){
        super(id,nombre,presupesto);
    }
    public SedeDAO(int id){
        getById(id);
    }

    public SedeDAO(Sede sede){
        super(sede.getId(), sede.getNombre(), sede.getPresupuesto());
        this.complejos = sede.getComplejos();
    }

    public boolean save(){
        if (getId() != -1){
            return update();
        }else{
            Connection conn = MariaDBConnection.getConnection();
            if(conn == null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1,getNombre());
                ps.setInt(2,getPresupuesto());

                if (ps.executeUpdate()==1){
                    try(ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            if (complejos!=null){
                                for(Complejo c:complejos){
                                    ComplejoDAO c2 = new ComplejoDAO(c);
                                    c2.sede=this;
                                    c2.save();
                                }
                            }
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;

            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
        }
    }

    public boolean update(){
        if(getId()==-1) return false;

        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            ps.setString(1,getNombre());
            ps.setInt(2,getPresupuesto());
            ps.setInt(3,getId());
            if (ps.executeUpdate()==1){
                if (complejos!=null){
                    for(Complejo c:complejos){
                        ComplejoDAO c2 = new ComplejoDAO(c);
                        c2.sede=this;
                        c2.save();
                    }
                }
                return true;
            }
            setId(-1);
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(){
        if(getId()==-1) return false;

        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setInt(1,getId());
            if (ps.executeUpdate()==1){
                return true;
            }
            return false;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean getById(int id){
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)) {
            ps.setInt(1,id);
            if (ps.execute()){
                try(ResultSet rs = ps.getResultSet()) {
                    if (rs.next()){
                        setId(rs.getInt("id"));
                        setNombre(rs.getString("nombre"));
                        setPresupuesto(rs.getInt("presupuesto"));
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static List<Sede> getAll(){
        Connection conn = MariaDBConnection.getConnection();
        if(conn == null) return null;
        List<Sede> result = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)) {
            if (ps.execute()){
                try(ResultSet rs = ps.getResultSet()) {
                    if (rs.next()){
                        Sede s = new Sede(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("presupuesto"));
                        result.add(s);
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return result;
    }

    @Override
    public List<Complejo> getComplejos() {
        if (complejos==null){
            setComplejos(ComplejoDAO.getBySede(getId()));
        }
        return super.getComplejos();
    }
}
