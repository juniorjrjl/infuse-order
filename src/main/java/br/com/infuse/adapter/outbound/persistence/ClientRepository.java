package br.com.infuse.adapter.outbound.persistence;

import br.com.infuse.adapter.outbound.persistence.entity.ClientEntity;
import br.com.infuse.core.port.ClientPort;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;

@AllArgsConstructor(onConstructor = @__({@Inject}))
public class ClientRepository implements ClientPort {

    private final EntityManager entityManager;

    @Override
    public boolean hasClientId(final Long clientId) {
        return entityManager.find(ClientEntity.class, clientId) != null;
    }

}
