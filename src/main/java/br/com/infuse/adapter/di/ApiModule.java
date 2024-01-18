package br.com.infuse.adapter.di;

import br.com.infuse.adapter.decorator.OrderQueryServiceDecorator;
import br.com.infuse.adapter.decorator.OrderServiceDecorator;
import br.com.infuse.adapter.deserializer.DeserializerImpl;
import br.com.infuse.adapter.deserializer.Deserializer;
import br.com.infuse.adapter.inbound.route.request.OrderInsertRequest;
import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.adapter.inbound.route.response.FindAllOrderResponse;
import br.com.infuse.adapter.inbound.route.response.OrderInsertedResponse;
import br.com.infuse.adapter.mapper.repository.OrderMapper;
import br.com.infuse.adapter.mapper.repository.OrderMapperImpl;
import br.com.infuse.adapter.mapper.route.GetAllOrderMapper;
import br.com.infuse.adapter.mapper.route.GetAllOrderMapperImpl;
import br.com.infuse.adapter.mapper.route.NewOrderMapper;
import br.com.infuse.adapter.mapper.route.NewOrderMapperImpl;
import br.com.infuse.adapter.outbound.persistence.ClientRepository;
import br.com.infuse.adapter.outbound.persistence.OrderRepository;
import br.com.infuse.adapter.serializer.ISerializer;
import br.com.infuse.adapter.serializer.SerializerImpl;
import br.com.infuse.adapter.validator.ValidatorRequest;
import br.com.infuse.adapter.validator.ValidatorRequestImpl;
import br.com.infuse.core.port.ClientPort;
import br.com.infuse.core.port.OrderPort;
import br.com.infuse.core.service.OrderQueryService;
import br.com.infuse.core.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class ApiModule extends AbstractModule {

    private final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("infuse-PU");
    private EntityManager entityManager;

    @Provides
    public int port(){
        return 8080;
    }

    @Provides
    public EntityManager entityManager(){
        if (entityManager == null)
            entityManager = entityManagerFactory.createEntityManager();

        return entityManager;
    }

    @Provides
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper= new ObjectMapper();
        objectMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    @Provides
    public XmlMapper xmlMapper(){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.disable(WRITE_DATES_AS_TIMESTAMPS);
        xmlMapper.findAndRegisterModules();
        return xmlMapper;
    }

    @Provides
    public Validator validator(){
        Validator validator;
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            validator = factory.getValidator();
        }
        return validator;
    }

    @Override
    protected void configure() {
        bind(new Key<Deserializer<OrderInsertRequest>>() {}).to(new Key<DeserializerImpl<OrderInsertRequest>>() {});
        registerSerializers();
        bind(new Key<ValidatorRequest>() {}).to(new Key<ValidatorRequestImpl>() {});
        registerMappers();
        registerServices();
        registerRepositories();
    }

    private void registerServices() {
        bind(OrderQueryService.class).to(OrderQueryServiceDecorator.class);
        bind(OrderService.class).to(OrderServiceDecorator.class);
    }

    private void registerSerializers() {
        bind(new Key<ISerializer<ErrorResponse>>() {}).to(new Key<SerializerImpl<ErrorResponse>>() {});
        bind(new Key<ISerializer<OrderInsertedResponse>>() {}).to(new Key<SerializerImpl<OrderInsertedResponse>>() {});
        bind(new Key<ISerializer<FindAllOrderResponse>>() {}).to(new Key<SerializerImpl<FindAllOrderResponse>>() {});
    }

    private void registerMappers() {
        bind(GetAllOrderMapper.class).to(GetAllOrderMapperImpl.class);
        bind(NewOrderMapper.class).to(NewOrderMapperImpl.class);
        bind(OrderMapper.class).to(OrderMapperImpl.class);
    }

    private void registerRepositories(){
        bind(OrderPort.class).to(OrderRepository.class);
        bind(ClientPort.class).to(ClientRepository.class);
    }

}
