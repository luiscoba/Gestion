package ec.pure.naportec.eir.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
import ec.pure.naportec.eir.data.local.entity.ReferenciaEntity;

@Dao
public interface ReferenciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodoReferencacia(List<ReferenciaEntity> referenciaciaEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertarReferencia(ReferenciaEntity referencia);

    @Query("SELECT * FROM `ReferenciaEntity`")
    List<ReferenciaEntity> obtenerTablaReferencia();

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genmodCodigo= :genmodCodigo AND gentrfCodigo= :gentrfCodigo ORDER BY genrciDatonum1")
    List<ReferenciaEntity> obtenerListaReferenciasOrderByDatoNum1(String genmodCodigo, String gentrfCodigo);

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genmodCodigo= :genmodCodigo AND gentrfCodigo= :gentrfCodigo ORDER BY genrciDescripcion")
    List<ReferenciaEntity> obtenerListaReferenciasOrderByDescription(String genmodCodigo, String gentrfCodigo);

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genrciUuid= :genrciUuid")
    ReferenciaEntity obtenerReferencia(String genrciUuid);

    @Query("DELETE FROM `ReferenciaEntity`")
    void borrarTablaReferencia();

    // Consultas LiveData

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genmodCodigo= :genmodCodigo AND gentrfCodigo= :gentrfCodigo ORDER BY genrciDescripcion")
    LiveData<List<ReferenciaEntity>> obtenerReferenciasOrderByDescription(String genmodCodigo, String gentrfCodigo);

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genmodCodigo= :genmodCodigo AND gentrfCodigo= :gentrfCodigo ORDER BY genrciDatonum1")
    LiveData<List<ReferenciaEntity>> obtenerReferenciasOrderByDatoNum1(String genmodCodigo, String gentrfCodigo);

    // Consulta Sincronica

    @Query("SELECT * FROM `ReferenciaEntity` WHERE genmodCodigo= :genmodCodigo AND gentrfCodigo= :gentrfCodigo ORDER BY genrciDescripcion")
    List<ReferenciaEntity> obtenerReferenciasOrderByDescriptionSincronica(String genmodCodigo, String gentrfCodigo);
}
