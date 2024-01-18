package br.com.infuse.util.datafaker.bot.response;

import br.com.infuse.adapter.inbound.route.response.FieldErrorResponse;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class FieldErrorResponseBot extends AbstractBot<FieldErrorResponse> {

    private Supplier<String> name = () -> faker.lorem().word();

    private Supplier<String> message = () -> faker.lorem().word();

    public static FieldErrorResponseBot builder(){
        return new FieldErrorResponseBot();
    }

    @Override
    public FieldErrorResponse build() {
        return FieldErrorResponse.builder()
                .name(name.get())
                .message(message.get())
                .build();
    }
}
