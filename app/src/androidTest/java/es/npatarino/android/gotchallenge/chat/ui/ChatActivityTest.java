package es.npatarino.android.gotchallenge.chat.ui;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.MessageDomain;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;
import es.npatarino.android.gotchallenge.common.view.activities.DetailActivity;
import es.npatarino.android.gotchallenge.testingtools.EspressoDaggerMockRule;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview.RecyclerSortedViewAssertion;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview.RecyclerViewInteraction;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar.ToolbarSubtitleViewAssertion.withSubtitle;
import static es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar.ToolbarTitleViewAssertion.withTitle;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

public class ChatActivityTest {

    private static final String CONVERSATION_NAME = "Conversation";
    private static final String CONVERSATION_IMAGE_URL = "https://pbs.twimg.com/profile_images/724559849544019971/SI6djf1z.jpg";
    private static final String MESSAGE_TEXT = "Hola bon dia";

    @Rule
    public IntentsTestRule<ChatActivity> activityTestRule =
            new IntentsTestRule<>(ChatActivity.class, true, false);

    @Rule
    public EspressoDaggerMockRule espressoDaggerMockRule = new EspressoDaggerMockRule();

    @Mock
    ConversationDomain.Repository conversationRepository;

    @Mock
    MessageDomain.Repository messageRepository;

    @Test
    public void
    show_conversation_name_in_toolbar_title_when_init_activity() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());

        initActivity();

        onView(withId(R.id.toolbar))
                .check(withTitle(CONVERSATION_NAME));
    }

    private void initActivity() {
        Intent startIntent = new Intent();
        startIntent.putExtra(ChatActivity.CONVER_ID_KEY, "any");
        activityTestRule.launchActivity(startIntent);
    }

    @Test
    public void
    show_conversation_users_in_toolbar_subtitle_when_init_activity() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());

        initActivity();

        onView(withId(R.id.toolbar))
                .check(withSubtitle(getUserListSubtitle()));
    }

    @Test
    public void
    show_conversation_image_in_toolbar_subtitle_when_init_activity() throws Exception {
        //TODO finish this
    }

    @Test
    public void
    move_to_detail_conversation_activity_when_click_in_toolbar() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());

        initActivity();

        onView(withId(R.id.toolbar))
                .perform(click());
        intended(hasComponent(DetailActivity.class.getCanonicalName()));
    }

    @Test
    public void
    show_my_message_when_send_a_message() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());

        initActivity();

        onView(withId(R.id.message_edit_text))
                .perform(typeText(MESSAGE_TEXT));

        onView(withId(R.id.attach))
                .perform(click());

        RecyclerViewInteraction.<Message>onRecyclerView(withId(R.id.recycler_view))
                .withItems(Arrays.asList(new Message("1", null, 2, true, new TextPayLoad(MESSAGE_TEXT))))
                .check((item, view, e) -> {
                    String message = ((TextPayLoad) item.getPayload()).getMessage().toString();
                    matches(hasDescendant(withText(message))).check(view, e);
                });
    }

    @Test
    public void
    show_messages_in_order_from_others_when_there_are_messages() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());
        given(messageRepository.getMessages(any(Conversation.class))).willReturn(getMessagesObservable());
        given(messageRepository.subscribeToMessages(any(Conversation.class))).willReturn(Observable.empty());

        initActivity();

        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(recyclerView -> {
                    ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
                    return adapter.getItems();
                }));
    }

    @Test
    public void
    show_messages_in_order_from_others_and_me_when_send_messages_and_receive_messages() throws Exception {
        //TODO how to mock fine this
//        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());
//
//        initActivity();
//
//        onView(withId(R.id.message_edit_text))
//                .perform(typeText(MESSAGE_TEXT));
//
//        onView(withId(R.id.attach))
//                .perform(click());
//
//        onView(withId(R.id.message_edit_text))
//                .perform(typeText(MESSAGE_TEXT + " Other message"));
//
//        onView(withId(R.id.attach))
//                .perform(click());
//
//        onView(withId(R.id.recycler_view))
//                .check(RecyclerSortedViewAssertion.isSorted(recyclerView -> {
//                    ChatAdapter adapter = (ChatAdapter) recyclerView.getAdapter();
//                    return adapter.getItems();
//                }));
    }

    private Observable<Conversation> getConversationObservable() {
        return Observable.fromCallable(this::getConversation);
    }

    private Conversation getConversation() {
        return new Conversation("1", CONVERSATION_NAME, getUserList(), null, CONVERSATION_IMAGE_URL);
    }

    private List<Message> getMessages() {
        return new ArrayList<>(Arrays.asList(
                new Message("1", new User("1", "Arya Stark", "",
                        "http://www.bolsamania.com/seriesadictos/wp-content/uploads/2015/12/landscape-1436892099-arya-stark.jpg"),
                        1, false, new TextPayLoad("Joffrey\nCersei\nWalder Frey\nMeryn Trant\nTywin Lannister\n"
                        + "The red woman\nBeric Dondarrion\nThoros of myr\nIlyn payne\nThe mountain\nThe hound")),
                new Message("2", new User("2", "Daenerys Targaryen", "",
                        "http://winteriscoming.net/wp-content/uploads/2016/03/Daenerys-Targaryen-crop-630x371.jpg"),
                        3, false, new TextPayLoad("where my dwarf is?")),
                new Message("3", new User("3", "Tyrion Lannister", "",
                        "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
                        6, false, new TextPayLoad("Tell me blonde who never burns"))));
    }

    private List<User> getUserList() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User("", "Pepe", "", CONVERSATION_IMAGE_URL));
        list.add(new User("", "Pepe", "", CONVERSATION_IMAGE_URL));
        return list;
    }

    private String getUserListSubtitle() {
        return getUserList().toString().replace("[", "").replace("]", "");
    }

    private Observable<List<Message>> getMessagesObservable() {
        return Observable.fromCallable(this::getMessages);
    }

    private Observable<Message> getMessageObservable(long timestamp) {
        return Observable.fromCallable(() -> new Message("3", new User("3", "Tyrion Lannister", "",
                "https://pbs.twimg.com/profile_images/668279339838935040/8sUE9d4C.jpg"),
                timestamp, false, new TextPayLoad("Tell me blonde who never burns")));
    }
}