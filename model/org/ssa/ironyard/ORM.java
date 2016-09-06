package org.ssa.ironyard;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ORM<T extends DomainObject>
{
    String projection();
    String table();
    T map(ResultSet results) throws SQLException;
    String prepareInsert();
    String prepareUpdate();
    String prepareRead();
    String prepareDelete();
    String prepareDeleteAll();
    String readUser();
    String readType();
    String prepareReadLessThan();

   

    String eagerRead();
    T deepMap(ResultSet query) throws SQLException;
    String eagerReadUser();
    String eagerReadType();
    String eagerPrepareReadLessThan();


    

 


}