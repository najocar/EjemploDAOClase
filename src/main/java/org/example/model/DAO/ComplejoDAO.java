package org.example.model.DAO;

import org.example.model.Connection.MariaDBConnection;
import org.example.model.Entity.Complejo;
import org.example.model.Entity.Sede;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComplejoDAO extends Complejo {
    private final static String INSERT ="INSERT INTO Complejo (nombre,localizacion,jefe,area,id_sede) VALUES(?,?,?,?,?)";
    private final static String UPDATE ="UPDATE Complejo SET nombre=?,localizacion=?,jefe=?,area=?,id_sede=? WHERE id=?";
    private final static String DELETE="DELETE FROM Complejo WHERE id=?";
    private final static String SELECTBYID="SELECT id,nombre,localizacion,jefe,area,id_sede FROM Complejo WHERE id=?";
    private final static String SELECTALL="SELECT id,nombre,localizacion,jefe,area,id_sede FROM Complejo";
    private final static String SELECTBYSEDE="SELECT id,nombre,localizacion,jefe,area,id_sede FROM Complejo WHERE id_sede=?";

    public ComplejoDAO(int id, String nombre, String localizacion,String jefe, int area){

        super(id,nombre,localizacion,area,jefe);
    }
    public ComplejoDAO(int id){
        getById(id);
    }
    public ComplejoDAO(Complejo c){
        super(c.getId(), c.getNombre(), c.getLocalizacion(),
                c.getArea(),c.getJefe());
    }

    public boolean save(){
        if(getId()!=-1){
            return update();
        }else{
            if(sede==null) return false;
            Connection conn = MariaDBConnection.getConnection();
            if(conn==null) return false;

            try(PreparedStatement ps = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)){
                ps.setString(1,getNombre());
                ps.setString(2,getLocalizacion());
                ps.setString(3,getJefe());
                ps.setInt(4,getArea());
                ps.setInt(5,sede.getId());

                if(ps.executeUpdate()==1) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            setId(rs.getInt(1));
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
                setId(-1);
                return false;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }
    public boolean update(){
        if(getId()==-1) return false;
        if(sede==null) return false;
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(UPDATE)){
            ps.setString(1,getNombre());
            ps.setString(2,getLocalizacion());
            ps.setString(3,getJefe());
            ps.setInt(4,getArea());
            ps.setInt(5,sede.getId());
            ps.setInt(6,getId());
            if(ps.executeUpdate()==1)
                return true;
            setId(-1);
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean remove(){

        if(getId()==-1) return false;

        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return false;

        try(PreparedStatement ps = conn.prepareStatement(DELETE)){
            ps.setInt(1,getId());
            if(ps.executeUpdate()==1)
                return true;

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean getById(int id){
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return false;
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYID)){
            ps.setInt(1,id);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    if(rs.next()){
                        setId(rs.getInt("id"));
                        setNombre(rs.getString("nombre"));
                        setArea(rs.getInt("area"));
                        setJefe(rs.getString("jefe"));
                        setLocalizacion(rs.getString("localizacion"));
                        sede = new SedeDAO(rs.getInt("id_sede"));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static List<Complejo> getAll(){
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return null;
        List<Complejo> result=new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTALL)){
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Complejo c = new Complejo();
                        c.setId(rs.getInt("id"));
                        c.setNombre(rs.getString("nombre"));
                        c.setArea(rs.getInt("area"));
                        c.setJefe(rs.getString("jefe"));
                        c.setLocalizacion(rs.getString("localizacion"));
                        c.sede = new SedeDAO(rs.getInt("id_sede"));

                        result.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static List<Complejo> getBySede(int id_sede){
        Connection conn = MariaDBConnection.getConnection();
        if(conn==null) return null;
        List<Complejo> result=new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(SELECTBYSEDE)){
            ps.setInt(1,id_sede);
            if(ps.execute()){
                try(ResultSet rs = ps.getResultSet()){
                    while(rs.next()){
                        Complejo c = new Complejo();
                        c.setId(rs.getInt("id"));
                        c.setNombre(rs.getString("nombre"));
                        c.setArea(rs.getInt("area"));
                        c.setJefe(rs.getString("jefe"));
                        c.setLocalizacion(rs.getString("localizacion"));
                        c.sede = new SedeDAO(rs.getInt("id_sede"));
                        result.add(c);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

}