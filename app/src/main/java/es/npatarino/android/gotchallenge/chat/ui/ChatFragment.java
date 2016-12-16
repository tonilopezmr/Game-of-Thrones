package es.npatarino.android.gotchallenge.chat.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.pedrogomez.renderers.RVRendererAdapter;
import es.npatarino.android.gotchallenge.GotChallengeApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;
import es.npatarino.android.gotchallenge.base.ui.messages.ErrorManager;
import es.npatarino.android.gotchallenge.chat.conversation.model.Conversation;
import es.npatarino.android.gotchallenge.chat.di.ChatFragmentModule;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import es.npatarino.android.gotchallenge.chat.message.model.Payload;
import es.npatarino.android.gotchallenge.chat.message.presenter.MessagePresenter;
import es.npatarino.android.gotchallenge.chat.message.ui.MessageRenderBuilder;
import es.npatarino.android.gotchallenge.chat.message.ui.SortedMessageCollection;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.StickerPayLoad;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.TextPayLoad;
import net.mobindustry.emojilib.EmojiPanel;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class ChatFragment extends Fragment implements MessagePresenter.View, OnBackListener {

  @Inject
  ErrorManager errorManager;
  @Inject
  MessagePresenter messagePresenter;
  @Inject
  ImageLoader imageLoader;
  private RecyclerView messageRecyclerView;
  private RVRendererAdapter<Message> adapter;
  private View rootView;
  //emoji section
  private EmojiPanel emojiPanel;
  private FrameLayout rootEmojiKeyBoard;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    initDagger();
    View rootView = inflater.inflate(R.layout.chat_fragment, container, false);
    initFragment(rootView);

    return rootView;
  }

  private void initDagger() {
    GotChallengeApplication.get(getContext())
        .getMessageComponent()
        .plus(new ChatFragmentModule())
        .inject(this);
  }

  private void initFragment(View rootView) {
    this.rootView = rootView;
    messageRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
    initEmojiPanel(rootView);

    String id = getArguments().getString(ChatActivity.CONVER_ID_KEY);
    messagePresenter.setView(this);
    messagePresenter.init(new Conversation(id, "", Collections.emptyList(), null, null));
  }

  private void initEmojiPanel(View rootView) {
    rootEmojiKeyBoard = (FrameLayout) rootView.findViewById(R.id.root_frame_layout);
    emojiPanel = new EmojiPanel(getActivity(), rootEmojiKeyBoard, new EmojiPanel.EmojiClickCallback() {

      @Override
      public void sendClicked(Spannable span) {
        if (span.length() != 0) {
          sendMessage(new TextPayLoad(span));
        }
      }

      @Override
      public void stickerClicked(String path) {
        sendMessage(new StickerPayLoad(path));
      }
    });

    emojiPanel.iconsInit();
    emojiPanel.init();

    initOnBackPressed(rootView);
  }

  private void sendMessage(Payload payload) {
    long timestamp = System.currentTimeMillis();
    messagePresenter.send(new Message("" + timestamp, null, timestamp, true, payload));
    scrollToBottom();
  }

  private void initOnBackPressed(View rootView) {
    rootView.setOnKeyListener((v, keyCode, event) -> {
      if (keyCode == KeyEvent.KEYCODE_BACK) {
        if (emojiPanel.isEmojiAttached()) {
          emojiPanel.dissmissEmojiPopup();
          return true;
        } else {
          return false;
        }
      }
      return false;
    });
  }

  private void initRecyclerView(RVRendererAdapter chatAdapter) {
    messageRecyclerView.setAdapter(chatAdapter);

    LinearLayoutManager recyclerViewManager = new LinearLayoutManager(getActivity());
    recyclerViewManager.setStackFromEnd(true);

    messageRecyclerView.setLayoutManager(recyclerViewManager);
    messageRecyclerView.setHasFixedSize(true);
  }

  private void scrollToBottom() {
    messageRecyclerView.scrollToPosition(adapter.getItemCount() - 1);
  }

  @Override
  public void onPause() {
    super.onPause();
    emojiPanel.dissmissEmojiPopup();
  }


  @Override
  public void showMessage(Message message) {
    adapter.add(message);
    scrollToBottom();
  }

  @Override
  public void showMessages(List<Message> messages) {
    adapter.addAll(messages);
    scrollToBottom();
  }

  @Override
  public void errorOnShowMessage() {
    errorManager.showError(rootView, "Error on show a message");
  }

  @Override
  public void errorOnShowMessages() {
    errorManager.showError(rootView, "Error on show a message list");
  }

  @Override
  public void initUi() {
    adapter = new RVRendererAdapter<Message>(new MessageRenderBuilder(imageLoader));
    SortedMessageCollection sortedMessageCollection = new SortedMessageCollection(adapter);
    adapter.setCollection(sortedMessageCollection);

    initRecyclerView(adapter);
    hideKeyboard();
  }

  private void hideKeyboard() {
    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
  }

  @Override
  public void error() {
    Toast.makeText(getContext(), "Some error", Toast.LENGTH_LONG).show();
  }

  @Override
  public boolean onBackListener() {
    if (emojiPanel.isEmojiAttached()) {
      emojiPanel.dissmissEmojiPopup();
      return true;
    }

    return false;
  }
}
