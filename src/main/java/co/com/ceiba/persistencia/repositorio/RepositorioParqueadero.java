package co.com.ceiba.persistencia.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import co.com.ceiba.persistencia.entidad.ParqueaderoEntity;

@Repository
public interface RepositorioParqueadero extends CrudRepository<ParqueaderoEntity, Long> {
    
	ParqueaderoEntity findByVigilantes(long vigilanteId);
	
	List<ParqueaderoEntity> findByNombre(String nombre);
	
}