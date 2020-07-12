package logic.users;

import logic.structures.simplelist.SimpleList;
import org.junit.Test;

public class SorterTest {

    @Test
    public void highRated() {
        int a = 5;
        int b = 2;
        System.out.println((double)(a/b));


    }

    @Test
    public void difficulty() {
        Recipe testRecipe1 = new Recipe();
        testRecipe1.setName("R1");
        Recipe testRecipe2 = new Recipe();
        testRecipe2.setName("R2");
        Recipe testRecipe3 = new Recipe();
        testRecipe3.setName("R3");
        Recipe testRecipe4 = new Recipe();
        testRecipe4.setName("R4");
        Recipe testRecipe5 = new Recipe();
        testRecipe5.setName("R5");
        testRecipe1.setDifficulty(19);//#2
        testRecipe2.setDifficulty(89);//#5
        testRecipe3.setDifficulty(70);//#4
        testRecipe4.setDifficulty(65);//#3
        testRecipe5.setDifficulty(4);//#1
        SimpleList<Recipe> unorderedRecipes = new SimpleList<>();
        unorderedRecipes.append(testRecipe2);//#2
        unorderedRecipes.append(testRecipe5);//#5
        unorderedRecipes.append(testRecipe3);//#3
        unorderedRecipes.append(testRecipe1);//#1
        unorderedRecipes.append(testRecipe4);//#4
        System.out.println("unsorted: " + unorderedRecipes);
        System.out.println("Sorted: " + Sorter.difficulty(unorderedRecipes));
    }

    @Test
    public void byDuration() {
        Recipe testRecipe1 = new Recipe();
        testRecipe1.setName("R1");
        Recipe testRecipe2 = new Recipe();
        testRecipe2.setName("R2");
        Recipe testRecipe3 = new Recipe();
        testRecipe3.setName("R3");
        Recipe testRecipe4 = new Recipe();
        testRecipe4.setName("R4");
        Recipe testRecipe5 = new Recipe();
        testRecipe5.setName("R5");
        testRecipe1.setDuration(19);//#2
        testRecipe2.setDuration(89);//#5
        testRecipe3.setDuration(70);//#4
        testRecipe4.setDuration(65);//#3
        testRecipe5.setDuration(4);//#1
        SimpleList<Recipe> unorderedRecipes = new SimpleList<>();
        unorderedRecipes.append(testRecipe2);//#2
        unorderedRecipes.append(testRecipe5);//#5
        unorderedRecipes.append(testRecipe3);//#3
        unorderedRecipes.append(testRecipe1);//#1
        unorderedRecipes.append(testRecipe4);//#4
        System.out.println("unsorted: " + unorderedRecipes);
        System.out.println("Sorted: " + Sorter.byDuration(unorderedRecipes));
    }
}