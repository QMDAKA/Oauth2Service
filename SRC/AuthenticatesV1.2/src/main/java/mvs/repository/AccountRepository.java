package mvs.repository;

import mvs.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Quang Minh on 5/10/2016.
 */
@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account,Long> {
}
