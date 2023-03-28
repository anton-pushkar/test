package mate.academy.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mate.academy.model.OrderStatus;

@Getter
@Setter
public class OrderRequestDto {
    private Long carId;
    private String description;
    private List<Long> favorIds;
    private List<Long> goodsIds;
    private OrderStatus status;
}
