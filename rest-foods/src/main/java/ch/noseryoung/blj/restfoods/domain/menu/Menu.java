package ch.noseryoung.blj.restfoods.domain.menu;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;



@Entity(name = "menu")
@Getter
@Setter
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer menuID;

    private String name;

    @Max(value = 2147483647, message = "Price can't be that high")
    @PositiveOrZero(message = "Price must be positive or zero")
    private Integer price;

    private boolean vegetarian;

    private String type;

    private String imgUrl;

}