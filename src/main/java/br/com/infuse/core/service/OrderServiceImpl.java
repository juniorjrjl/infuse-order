package br.com.infuse.core.service;

import br.com.infuse.core.domain.insert.OrderInsertDomain;
import br.com.infuse.core.domain.insert.OrderInsertedDomain;
import br.com.infuse.core.exception.ControlIdInUseException;
import br.com.infuse.core.exception.ValidatorException;
import br.com.infuse.core.exception.ValidatorExceptionField;
import br.com.infuse.core.port.ClientPort;
import br.com.infuse.core.port.OrderPort;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService{

    private final OrderPort port;
    private final ClientPort clientPort;

    public OrderServiceImpl(final OrderPort port, ClientPort clientPort) {
        this.port = port;
        this.clientPort = clientPort;
    }

    @Override
    public List<OrderInsertedDomain> save(final List<OrderInsertDomain> domain) {
        checkHowManyOrders(domain);
        for(OrderInsertDomain d: domain){
            if (port.hasControlId(d.getControlId())){
                throw new ControlIdInUseException(String.format("O número de controle %s já está sendo usado", d.getControlId()));
            }
            if (!clientPort.hasClientId(d.getClientId())){
                throwValidatorException("clientId", String.format("Não há cliente cadastrado com o Id %s", d.getClientId()));
            }
        }

        return port.save(domain);
    }

    private void checkHowManyOrders(final List<OrderInsertDomain> domain){
        if(domain != null && !domain.isEmpty() && domain.size() < 11) return;

        throwValidatorException("body", "Informe no mínimo 1 pedido e no máximo 10");
    }

    private static void throwValidatorException(final String field, final String message) {
        List<ValidatorExceptionField> fields = new ArrayList<>();
        ValidatorExceptionField validatorExceptionField = ValidatorExceptionField.builder()
                .field(field)
                .message(message)
                .build();
        fields.add(validatorExceptionField);
        throw new ValidatorException("Sua requisição contém erros", fields);
    }

}
