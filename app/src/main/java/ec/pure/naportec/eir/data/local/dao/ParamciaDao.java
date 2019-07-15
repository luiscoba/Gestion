package ec.pure.naportec.eir.data.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

import ec.pure.naportec.eir.data.local.entity.ParamciaEntity;

@Dao
public interface ParamciaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertarTodoParamcia(List<ParamciaEntity> paramciaEntities);

    @Query("SELECT * FROM `ParamciaEntity`")
    List<ParamciaEntity> obtenerTodoParamcia();


}
