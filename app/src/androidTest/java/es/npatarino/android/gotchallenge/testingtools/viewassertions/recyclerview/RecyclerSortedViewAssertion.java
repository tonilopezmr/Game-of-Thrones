package es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.core.deps.guava.collect.Ordering;
import android.support.test.espresso.util.HumanReadables;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.StringDescription;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RecyclerSortedViewAssertion<T extends Comparable> implements ViewAssertion {

    private List<T> sortedList = new ArrayList<>();
    private WithAdapter<T> withAdapter;

    public RecyclerSortedViewAssertion(WithAdapter<T> withAdapter) {
        if (withAdapter == null) {
            throw new IllegalArgumentException("WithAdapter is needed, can't be null");
        }

        this.withAdapter = withAdapter;
    }

    @Override
    public void check(View view, NoMatchingViewException noViewFoundException) {
        StringDescription description = new StringDescription();
        RecyclerView recyclerView = (RecyclerView) view;
        sortedList = withAdapter.itemsToSort(recyclerView);

        checkIsNotEmpty(view, description);

        description.appendText("The list " + sortedList + " is not sorted");
        assertTrue(description.toString(), Ordering.natural().<T>isOrdered(sortedList));
    }

    private void checkIsNotEmpty(View view, StringDescription description) {
        if (sortedList.isEmpty()) {
            description.appendText("The list must be not empty");
            throw (new PerformException.Builder())
                    .withActionDescription(description.toString())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(new IllegalStateException("The list is empty"))
                    .build();
        }
    }

    public static <T extends Comparable> RecyclerSortedViewAssertion isSorted(WithAdapter<T> adapter) {
        return new RecyclerSortedViewAssertion<T>(adapter);
    }

    public interface WithAdapter<T> {
        List<T> itemsToSort(RecyclerView recyclerView);
    }
}
