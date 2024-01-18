package br.com.infuse.util.datafaker.bot.response;

import br.com.infuse.adapter.inbound.route.response.ErrorResponse;
import br.com.infuse.adapter.inbound.route.response.FieldErrorResponse;
import br.com.infuse.util.datafaker.bot.AbstractBot;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.List;
import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@With
@AllArgsConstructor(access = PRIVATE)
@NoArgsConstructor(access = PRIVATE)
public class ErrorResponseBot extends AbstractBot<ErrorResponse> {

    private Supplier<Integer> status = () -> faker.number().numberBetween(100,599);

    private Supplier<String> title = () -> faker.lorem().word();

    private Supplier<String> details = () -> faker.lorem().word();

    private Supplier<List<FieldErrorResponse>> fields = () -> faker.response()
            .getFieldErrorResponseBot()
            .build(faker.number()
                    .randomDigit());

    public static ErrorResponseBot builder(){
        return new ErrorResponseBot();
    }

    @Override
    public ErrorResponse build() {
        return ErrorResponse.builder()
                .status(status.get())
                .title(title.get())
                .details(details.get())
                .fields(fields.get())
                .build();
    }
}
