package mvs.repository;

import mvs.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Quang Minh on 5/18/2016.
 */
@RepositoryRestResource
public interface TokenRepository extends JpaRepository<Token,Long>{
    Token findByToken (String token);
}
