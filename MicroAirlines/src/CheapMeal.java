import java.util.ArrayList;

public class CheapMeal extends Meal{
	
	static ArrayList <CheapMeal> nice = new ArrayList<CheapMeal>();

	public CheapMeal(String mealName, int price) {
		super(mealName, price);
	}
	
	public String getMealName() {
		return mealName;
	
	}

	public void setMealName(String mealName) {
		mealName = mealName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
}
