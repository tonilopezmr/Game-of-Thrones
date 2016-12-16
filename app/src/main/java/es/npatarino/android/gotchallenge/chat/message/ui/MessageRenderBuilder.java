package es.npatarino.android.gotchallenge.chat.message.ui;

import com.pedrogomez.renderers.Renderer;
import com.pedrogomez.renderers.RendererBuilder;
import es.npatarino.android.gotchallenge.base.ui.imageloader.ImageLoader;
import es.npatarino.android.gotchallenge.chat.message.model.Message;
import es.npatarino.android.gotchallenge.chat.message.viewmodel.StickerPayLoad;

import java.util.LinkedList;
import java.util.List;

public class MessageRenderBuilder extends RendererBuilder<Message> {

  private ImageLoader imageLoader;

  public MessageRenderBuilder(ImageLoader imageLoader) {
    this.imageLoader = imageLoader;
    List<Renderer<Message>> prototypes = getMessageRendererPrototypes();
    setPrototypes(prototypes);
  }

  public List<Renderer<Message>> getMessageRendererPrototypes() {
    LinkedList<Renderer<Message>> prototypes = new LinkedList<>();

    prototypes.add(new MessageRenderer(imageLoader));
    prototypes.add(new StickerRenderer(imageLoader));

    return prototypes;
  }

  @Override
  protected Class getPrototypeClass(Message content) {
    Class prototypeClass;

    if (content.getPayload() instanceof StickerPayLoad) {
      prototypeClass = StickerRenderer.class;
    } else {
      prototypeClass = MessageRenderer.class;
    }

    return prototypeClass;
  }
}
