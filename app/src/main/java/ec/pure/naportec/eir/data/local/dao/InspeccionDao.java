package ec.pure.naportec.eir.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import ec.pure.naportec.eir.data.local.entity.InspeccionEntity;
import ec.pure.naportec.eir.data.local.entity.PedirEventoPlacaEntity;
import ec.pure.naportec.eir.data.local.entity.RespuestaEntity;

@Dao
public interface InspeccionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarInspeccionEntity(InspeccionEntity inspeccionEntity);

    @Query("DELETE FROM InspeccionEntity")
    void borrarInspeccionEntity();

    @Query("SELECT * FROM `InspeccionEntity`")
    InspeccionEntity obtenerInspeccionEntity();

    @Update
    void actualizarInspeccion(InspeccionEntity entity);

    @Insert
    void insertarRespuestaDelServidor(RespuestaEntity respuestaEntity);

    @Query("SELECT * FROM `RespuestaEntity`")
    RespuestaEntity obtenerRespuestaEntity();

    @Query("DELETE FROM `RespuestaEntity`")
    void borrarRespuestaEntity();

}
