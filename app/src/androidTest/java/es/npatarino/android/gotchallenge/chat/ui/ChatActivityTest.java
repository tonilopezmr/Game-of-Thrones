package es.npatarino.android.gotchallenge.chat.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.v7.widget.RecyclerView;
import com.pedrogomez.renderers.RVRendererAdapter;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.chat.conversation.ConversationDomain;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.Conversation;
import es.npatarino.android.gotchallenge.chat.conversation.domain.model.User;
import es.npatarino.android.gotchallenge.chat.message.domain.model.Message;
import es.npatarino.android.gotchallenge.chat.message.view.viewmodel.TextPayLoad;
import es.npatarino.android.gotchallenge.common.view.activities.DetailActivity;
import es.npatarino.android.gotchallenge.testingtools.EspressoDaggerMockRule;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview.RecyclerSortedViewAssertion;
import es.npatarino.android.gotchallenge.testingtools.viewassertions.recyclerview.RecyclerViewInteraction;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar
        .ToolbarLogoViewAssertion.hasLogo;
import static es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar
        .ToolbarSubtitleViewAssertion.withSubtitle;
import static es.npatarino.android.gotchallenge.testingtools.viewassertions.toolbar.ToolbarTitleViewAssertion.withTitle;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

public class ChatActivityTest {

    private static final String CONVERSATION_NAME = "Conversation";
    private static final String CONVERSATION_IMAGE_URL
            = "https://pbs.twimg.com/profile_images/724559849544019971/SI6djf1z.jpg";
    private static final String MESSAGE_TEXT = "Hola bon dia";

    @Rule
    public IntentsTestRule<ChatActivity> activityTestRule =
            new IntentsTestRule<>(ChatActivity.class, true, false);

    @Rule
    public ChatActivityDaggerMockRule daggerMockRule = new ChatActivityDaggerMockRule();

    @Mock
    ConversationDomain.Repository conversationRepository;

    @After
    public void tearDown() throws Exception {
        GotChallengeApplication app = EspressoDaggerMockRule.getApp();
        app.releaseConversationComponent();
        app.releaseMessageComponent();
    }

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
        startIntent.putExtra(ChatActivity.CONVER_ID_KEY, getConversation().getId());
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
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());

        initActivity();

        onView(withId(R.id.toolbar))
                .check(hasLogo());
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
        MockMessageRepository messageRepository = daggerMockRule.getMockMessageRepository();
        messageRepository.disableMessages();

        initActivity();

        onView(withId(R.id.message_edit_text))
                .perform(typeText(MESSAGE_TEXT), closeSoftKeyboard());

        onView(withId(R.id.attach))
                .perform(click());

        RecyclerViewInteraction.<Message>onRecyclerView(withId(R.id.recycler_view))
                .withItems(Arrays.asList(new Message("1", null, 2, true, new TextPayLoad(MESSAGE_TEXT))))
                .check((item, view, e) -> {
                    String message = ((TextPayLoad) item.getPayload()).getTextMessage().toString();
                    matches(hasDescendant(withText(message))).check(view, e);
                });
    }

    @Test
    public void
    show_messages_in_order_from_others_when_there_are_messages() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());
        MockMessageRepository messageRepository = daggerMockRule.getMockMessageRepository();
        messageRepository.enableMessages();

        initActivity();

        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(this::getAdapterMessages));
    }

    @Test
    public void
    show_messages_in_order_from_others_and_me_when_send_messages_and_receive_messages() throws Exception {
        given(conversationRepository.get(any(Conversation.class))).willReturn(getConversationObservable());
        MockMessageRepository messageRepository = daggerMockRule.getMockMessageRepository();
        messageRepository.enableMessages();
        Conversation conversation = getConversation();

        initActivity();

        onView(withId(R.id.message_edit_text))
                .perform(typeText(MESSAGE_TEXT));

        onView(withId(R.id.attach))
                .perform(click());

        messageRepository.sendMessage(conversation);

        onView(withId(R.id.message_edit_text))
                .perform(typeText(MESSAGE_TEXT + " Other message"));

        onView(withId(R.id.attach))
                .perform(click());

        messageRepository.sendMessage(conversation);

        onView(withId(R.id.recycler_view))
                .check(RecyclerSortedViewAssertion.isSorted(this::getAdapterMessages));
    }

    private List<Message> getAdapterMessages(RecyclerView recyclerView) {
        RVRendererAdapter<Message> adapter = (RVRendererAdapter<Message>) recyclerView.getAdapter();
        return getMessages(adapter);
    }

    @NonNull
    private LinkedList<Message> getMessages(RVRendererAdapter<Message> adapter) {
        LinkedList<Message> messages = new LinkedList<Message>();
        for (int i = 0, size = adapter.getItemCount(); i < size; i++) {
            messages.add(adapter.getItem(i));
        }
        return messages;
    }

    private Observable<Conversation> getConversationObservable() {
        return Observable.fromCallable(this::getConversation);
    }

    private Conversation getConversation() {
        return new Conversation("1", CONVERSATION_NAME, getUserList(), null, CONVERSATION_IMAGE_URL);
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

}