import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    LocalTime openingTime;
    LocalTime closingTime;
    Restaurant restaurant1;
    int initialMenuSize;

    @BeforeEach
    public void init(){
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        initialMenuSize = restaurant.getMenu().size();
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        restaurant1 = Mockito.spy(restaurant);
        LocalTime fourhoursAfter = openingTime.plusHours(4);
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(fourhoursAfter);

        assertTrue(restaurant1.isRestaurantOpen());//test for equal
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        restaurant1 = Mockito.spy(restaurant);
        LocalTime fourhoursBefore = openingTime.minusHours(4);
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(fourhoursBefore);

        assertFalse(restaurant1.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<Calculating Cost>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void calculating_Total_cost_on_Selecting_items_from_menu(){
        List<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        int totalCost = restaurant.selectedItemsCost(selectedItems);
        assertEquals(388,totalCost);
    }
}